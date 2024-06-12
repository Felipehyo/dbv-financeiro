package br.com.dbv.financeiro.dto;

import br.com.dbv.financeiro.enums.BookTypeEnum;
import br.com.dbv.financeiro.model.CashBook;
import br.com.dbv.financeiro.model.Club;
import br.com.dbv.financeiro.model.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CashBookDTO {

    private BookTypeEnum type;
    private String description;
    private LocalDate date;
    private String value;
    private Long eventId;

    public CashBook convert(Club club, Event event) {

        CashBook cashBook = new CashBook();
        cashBook.setDescription(description);
        cashBook.setValue(Double.valueOf(value));
        cashBook.setType(type);
        cashBook.setClub(club);
        cashBook.setEvent(event);
        cashBook.setDate(date);

        return cashBook;

    }

}