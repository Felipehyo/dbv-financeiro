package br.com.dbv.financeiro.dto;

import br.com.dbv.financeiro.model.Club;
import br.com.dbv.financeiro.model.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {

    private String name;
    private Double value;
    private LocalDate date;
    private Long clubId;

    public Event convert(Club club) {

        Event event = new Event();
        event.setEvent(name);
        event.setValue(value);
        event.setDate(date);
        event.setClub(club);

        return event;

    }

}