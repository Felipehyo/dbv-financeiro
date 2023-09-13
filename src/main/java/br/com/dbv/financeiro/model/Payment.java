package br.com.dbv.financeiro.model;

import br.com.dbv.financeiro.enums.FormOfPaymentEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PAYMENT")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToOne
    @JoinColumn(name = "pathfinder_id")
    private Pathfinder pathfinder;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @NotNull
    private Double value;

    @NotNull
    private FormOfPaymentEnum formOfPayment;

    private LocalDate date;

}