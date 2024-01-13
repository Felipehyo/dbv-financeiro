package br.com.dbv.financeiro.service;

import br.com.dbv.financeiro.dto.ErrorDTO;
import br.com.dbv.financeiro.dto.movement.UserMovementHistoryRequestDTO;
import br.com.dbv.financeiro.enums.MovementTypeEnum;
import br.com.dbv.financeiro.exception.CustomException;
import br.com.dbv.financeiro.model.Event;
import br.com.dbv.financeiro.model.Pathfinder;
import br.com.dbv.financeiro.model.UserEventBank;
import br.com.dbv.financeiro.model.UserMovementHistory;
import br.com.dbv.financeiro.repository.EventRepository;
import br.com.dbv.financeiro.repository.UserEventBankRepository;
import br.com.dbv.financeiro.repository.UserMovementHistoryRepository;
import br.com.dbv.financeiro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class UserMovementHistoryService {

    @Autowired
    private UserMovementHistoryRepository repository;

    @Autowired
    private UserEventBankRepository userEventBankRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    public UserMovementHistory movement(UserMovementHistoryRequestDTO request) throws CustomException {

        var optionalEvent = eventRepository.findById(request.getEventId());

        if (!optionalEvent.isPresent())
            throw new CustomException(new ErrorDTO("400", "Event not found", "Event not found"));

        var event = optionalEvent.get();
        var optionalUser = userRepository.findById(request.getPathfinderId());

        if (!optionalUser.isPresent())
            throw new CustomException(new ErrorDTO("400", "User not found", "User not found"));

        var user = optionalUser.get();

        /** Busca valor total */
        var userValueAccruedEvent = 0.0;
        UserEventBank userEventBank = null;

        var optionalUserEventBank = userEventBankRepository.findByEventIdAndPathfinderId(event.getId(), user.getId());
        if (optionalUserEventBank.isPresent()) {
            userEventBank = optionalUserEventBank.get();
            userValueAccruedEvent = optionalUserEventBank.get().getAmount();
        } else {
            var UserEventBankRequest = UserEventBank.builder()
                    .event(event)
                    .pathfinder(user)
                    .amount(userValueAccruedEvent)
                    .build();

            userEventBank = userEventBankRepository.save(UserEventBankRequest);
        }

        /** Lógica do negócio */
        if (request.getMovementType().equals(MovementTypeEnum.TRANSFER_TO_EVENT)) {
            if (user.getBank() < request.getAmount())
                throw new CustomException(new ErrorDTO("400", "Invalid Operation", "Insufficient cash value for the transaction"));

            if (userValueAccruedEvent >= event.getValue())
                throw new CustomException(new ErrorDTO("400", "Invalid Operation", "This event has already been paid for"));

            //Retira valor do usuário
            user.setBank(user.getBank() - request.getAmount());

            // valor restante para quitar o evento
            var eventDebitBalance = event.getValue() - userValueAccruedEvent;
            // valor para transferir para o evento
            var transferEventValue = eventDebitBalance < request.getAmount() ? eventDebitBalance : request.getAmount();
            // valor para devolver para o usuário
            var transferUserBankValue = request.getAmount() - transferEventValue;

            userEventBank.setAmount(userEventBank.getAmount() + transferEventValue);
            event.setBank(event.getBank() + transferEventValue);
            var transferToEvent = generateMovement(event, user, transferEventValue, MovementTypeEnum.TRANSFER_TO_EVENT);
            repository.save(transferToEvent);

            if (transferUserBankValue > 0) {
                user.setBank(user.getBank() + transferUserBankValue);
                var transferToUserBank = generateMovement(event, user, transferUserBankValue, MovementTypeEnum.TRANSFER_TO_USER_BANK);
                repository.save(transferToUserBank);
            }

            return transferToEvent;
        } else if (request.getMovementType().equals(MovementTypeEnum.TRANSFER_TO_USER_BANK)) {
            if (userValueAccruedEvent < request.getAmount())
                throw new CustomException(new ErrorDTO("400", "Invalid Operation", "Insufficient cash value for the transaction"));

            var transferToUserBank = generateMovement(event, user, request.getAmount(), MovementTypeEnum.TRANSFER_TO_USER_BANK);
            repository.save(transferToUserBank);

            userEventBank.setAmount(userEventBank.getAmount() - request.getAmount());
            event.setBank(event.getBank() - request.getAmount());
            user.setBank(user.getBank() + request.getAmount());

            return transferToUserBank;
        }

        throw new CustomException(new ErrorDTO("400", "Invalid Operation", "Invalid operation"));

    }

    private UserMovementHistory generateMovement(Event event, Pathfinder user, double transferUserBankValue, MovementTypeEnum movementType) {
        return UserMovementHistory.builder()
                .event(event)
                .pathfinder(user)
                .movementType(movementType)
                .amount(transferUserBankValue)
                .createdDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")))
                .build();
    }


}


