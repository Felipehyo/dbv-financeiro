package br.com.dbv.financeiro.service;

import br.com.dbv.financeiro.dto.ErrorDTO;
import br.com.dbv.financeiro.dto.event.EventDTO;
import br.com.dbv.financeiro.dto.event.EventRegisterDTO;
import br.com.dbv.financeiro.dto.event.UpdateEventDTO;
import br.com.dbv.financeiro.dto.movement.UserMovementHistoryRequestDTO;
import br.com.dbv.financeiro.enums.EventTypeRegisterEnum;
import br.com.dbv.financeiro.enums.MovementTypeEnum;
import br.com.dbv.financeiro.exception.CustomException;
import br.com.dbv.financeiro.model.EventRegister;
import br.com.dbv.financeiro.repository.EventRegisterRepository;
import br.com.dbv.financeiro.repository.EventRepository;
import br.com.dbv.financeiro.repository.UserEventBankRepository;
import br.com.dbv.financeiro.repository.UserRepository;
import br.com.dbv.financeiro.service.mapper.EventRegisterMapper;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    @Autowired
    private EventRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventRegisterRepository eventRegisterRepository;

    @Autowired
    private UserEventBankRepository userEventBankRepository;

    @Autowired
    private UserMovementHistoryService userMovementHistoryService;

    public ResponseEntity<?> updateEvent(Long eventId, UpdateEventDTO request) {

        var optionalEvent = repository.findById(eventId);

        if (!optionalEvent.isPresent()) {
            return ResponseEntity.badRequest().body(new ErrorDTO("400", "Event not found", "Event not found"));
        }

        var event = optionalEvent.get();

        if (!StringUtils.isBlank(request.getName())) event.setEvent(request.getName());
        if (request.getValue() != null && request.getValue() > 0) event.setValue(request.getValue());
        if (request.getDate() != null) event.setDate(request.getDate());

        repository.save(event);

        var response = EventDTO.builder()
                .id(event.getId())
                .clubId(event.getClub().getId())
                .name(event.getEvent())
                .value(event.getValue())
                .date(event.getDate())
                .build();

        return ResponseEntity.ok().body(response);

    }

    public Object registerUserInEvent(EventRegisterDTO request) throws CustomException {

        var optionalEvent = eventRepository.findById(request.getEventId());
        var optionalPathfinder = userRepository.findById(request.getPathfinderId());

        if (!optionalEvent.isPresent() || !optionalPathfinder.isPresent()) {
            throw new CustomException(new ErrorDTO("400", "Event or Pathfinder not found", "Event or Pathfinder not found in database"));
        }

        var event = optionalEvent.get();
        var pathfinder = optionalPathfinder.get();

        EventRegister eventRegister = null;

        var optionalEventRegister = eventRegisterRepository.findByEventIdAndPathfinderId(optionalEvent.get().getId(), optionalPathfinder.get().getId());

        MovementTypeEnum operation = null;

        if (request.getRegisterType().equals(EventTypeRegisterEnum.UNSUBSCRIBE)) {
            if (!optionalEventRegister.isPresent()) {
                throw new CustomException(new ErrorDTO("400", "User is not registered", "User is not registered"));
            }
            operation = MovementTypeEnum.TRANSFER_TO_USER_BANK;
            eventRegister = optionalEventRegister.get();
        } else if (request.getRegisterType().equals(EventTypeRegisterEnum.SUBSCRIBE)) {
            if (optionalEventRegister.isPresent()) {
                throw new CustomException(new ErrorDTO("400", "User already registered", "User already registered"));
            }
            operation = MovementTypeEnum.TRANSFER_TO_EVENT;
            eventRegister = request.convert(optionalEvent.get(), optionalPathfinder.get());
        }

        var optionalUserEventBank = userEventBankRepository.findByEventIdAndPathfinderId(event.getId(), pathfinder.getId());

        var amount = 0.0;

        if (pathfinder.getBank() > 0 && request.getRegisterType().equals(EventTypeRegisterEnum.SUBSCRIBE)) {

            amount = pathfinder.getBank() > event.getValue() ? amount = event.getValue() : pathfinder.getBank();

            userMovementHistoryService.movement(UserMovementHistoryRequestDTO.builder()
                    .eventId(event.getId())
                    .pathfinderId(pathfinder.getId())
                    .amount(amount)
                    .movementType(operation)
                    .build());

        }

        if (optionalUserEventBank.isPresent() && optionalUserEventBank.get().getAmount() > 0 && request.getRegisterType().equals(EventTypeRegisterEnum.UNSUBSCRIBE)) {
            userMovementHistoryService.movement(UserMovementHistoryRequestDTO.builder()
                    .eventId(event.getId())
                    .pathfinderId(pathfinder.getId())
                    .amount(optionalUserEventBank.get().getAmount())
                    .movementType(operation)
                    .build());
        }

        if (request.getRegisterType().equals(EventTypeRegisterEnum.SUBSCRIBE)) {
            eventRegisterRepository.save(eventRegister);
            return EventRegisterMapper.convert(eventRegister, userEventBankRepository);
        } else {
            eventRegisterRepository.delete(eventRegister);
            return null;
        }

    }

}
