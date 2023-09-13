package br.com.dbv.financeiro.dto.reponse;

import br.com.dbv.financeiro.model.CashBook;
import br.com.dbv.financeiro.model.Club;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CashBookResponseDTO {

    private Long id;
    private Club club;
    private String type;
    private String description;
    private Double value;
    private LocalDate date;

    public CashBookResponseDTO(CashBook cashBook) {
        this.id = cashBook.getId();
        this.club = cashBook.getClub();
        this.type = cashBook.getType().getValue();
        this.description = cashBook.getDescription();
        this.value = cashBook.getValue();
        this.date = cashBook.getDate();
    }

}
