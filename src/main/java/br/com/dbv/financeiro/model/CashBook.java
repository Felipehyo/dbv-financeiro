package br.com.dbv.financeiro.model;

import br.com.dbv.financeiro.enums.BookTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CASH_BOOK")
public class CashBook {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @NotNull
    private BookTypeEnum type;

    @NotNull
    private String description;
    
    private Double value;

    private LocalDate date;

}