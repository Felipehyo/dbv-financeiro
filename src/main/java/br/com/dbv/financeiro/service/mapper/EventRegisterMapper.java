package br.com.dbv.financeiro.service.mapper;

import br.com.dbv.financeiro.dto.event.ResponseEventRegisterDTO;
import br.com.dbv.financeiro.model.EventRegister;
import br.com.dbv.financeiro.model.Pathfinder;
import br.com.dbv.financeiro.model.UserEventBank;
import br.com.dbv.financeiro.repository.UserEventBankRepository;

import java.util.Optional;

public class EventRegisterMapper {

    public static ResponseEventRegisterDTO convert(EventRegister eventRegister, UserEventBankRepository userEventBankRepository) {

        var response = userEventBankRepository.findByEventIdAndPathfinderId(eventRegister.getEvent().getId(), eventRegister.getPathfinder().getId());

        calcPercentage(response);

        return ResponseEventRegisterDTO.builder()
                .event(eventRegister.getEvent().getEvent())
                .userId(eventRegister.getPathfinder().getId())
                .user(eventRegister.getPathfinder().getName())
                .userBank(eventRegister.getPathfinder().getBank())
                .userGender(eventRegister.getPathfinder().getGender())
                .eventAllocatedAmount(response.isPresent() ? response.get().getAmount() : 0)
                .percentagePayment(calcPercentage(response))
                .build();
    }

    private static Double calcPercentage(Optional<UserEventBank> response) {

        if (response.isPresent()) {
            return (100 * response.get().getAmount()) / response.get().getEvent().getValue();
        }

        return 0.0;

    }

    public static ResponseEventRegisterDTO convert(EventRegister eventRegister, Pathfinder user, UserEventBankRepository userEventBankRepository) {

        var response = userEventBankRepository.findByEventIdAndPathfinderId(eventRegister.getEvent().getId(), eventRegister.getPathfinder().getId());

        calcPercentage(response);

        return ResponseEventRegisterDTO.builder()
                .event(eventRegister.getEvent().getEvent())
                .userId(eventRegister.getPathfinder().getId())
                .user(eventRegister.getPathfinder().getName())
                .userBank(eventRegister.getPathfinder().getBank())
                .userGender(eventRegister.getPathfinder().getGender())
                .eventAllocatedAmount(response.isPresent() ? response.get().getAmount() : 0)
                .percentagePayment(calcPercentage(response))
                .build();
    }

}
