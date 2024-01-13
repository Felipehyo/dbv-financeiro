package br.com.dbv.financeiro.dto.event;

import br.com.dbv.financeiro.model.Club;
import br.com.dbv.financeiro.model.Event;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventDTO {

    private Long id;
    private String name;
    private Double value;
    private LocalDate date;
    private Long clubId;
    private Long subscribedUsers;

    public Event convert(Club club) {

        Event event = new Event();
        event.setEvent(name);
        event.setValue(value);
        event.setDate(date);
        event.setClub(club);
        event.setBank(0.0);

        return event;

    }

    public EventDTO(Event event, Long subscribedUsers){
        this.id = event.getId();
        this.name = event.getEvent();
        this.value = event.getValue();
        this.date = event.getDate();
        this.subscribedUsers = subscribedUsers;
    }

}