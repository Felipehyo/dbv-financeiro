package br.com.dbv.financeiro.dto;

import br.com.dbv.financeiro.model.Event;
import br.com.dbv.financeiro.model.EventRegister;
import br.com.dbv.financeiro.model.Pathfinder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventRegisterDTO {

    private Long eventId;
    private UUID pathfinderId;

    public EventRegister convert(Event event, Pathfinder pathfinder) {

        EventRegister eventRegister = new EventRegister();
        eventRegister.setEvent(event);
        eventRegister.setPathfinder(pathfinder);
        eventRegister.setCreatedDate(LocalDateTime.now());

        return eventRegister;

    }

}