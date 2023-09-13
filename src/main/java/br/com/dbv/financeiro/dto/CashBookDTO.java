package br.com.dbv.financeiro.dto;

import br.com.dbv.financeiro.enums.BookTypeEnum;
import br.com.dbv.financeiro.model.CashBook;
import br.com.dbv.financeiro.model.Club;
import br.com.dbv.financeiro.model.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashBookDTO {

    private BookTypeEnum type;
    private String description;
    private LocalDate date;
    private Double value;
    private Long clubId;

    public CashBook convert(Club club) {

        CashBook cashBook = new CashBook();
        cashBook.setDescription(description);
        cashBook.setValue(value);
        cashBook.setType(type);
        cashBook.setClub(club);
        cashBook.setDate(date);

        return cashBook;

    }

}