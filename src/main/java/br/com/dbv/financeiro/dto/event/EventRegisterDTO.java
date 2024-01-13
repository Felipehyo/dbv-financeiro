package br.com.dbv.financeiro.dto.event;

import br.com.dbv.financeiro.enums.EventTypeRegisterEnum;
import br.com.dbv.financeiro.model.Event;
import br.com.dbv.financeiro.model.EventRegister;
import br.com.dbv.financeiro.model.Pathfinder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventRegisterDTO {

    private Long eventId;
    private UUID pathfinderId;
    private EventTypeRegisterEnum registerType;

    public EventRegister convert(Event event, Pathfinder pathfinder) {

        EventRegister eventRegister = new EventRegister();
        eventRegister.setEvent(event);
        eventRegister.setPathfinder(pathfinder);
        eventRegister.setCreatedDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));

        return eventRegister;

    }

}