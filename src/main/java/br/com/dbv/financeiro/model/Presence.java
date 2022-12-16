package br.com.dbv.financeiro.model;

import br.com.dbv.financeiro.enums.PresenceTypeEnum;
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
@Table(name = "PRESENCE")
public class Presence {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "pathfinder_id")
    @NotNull
    private User pathfinder;

    @OneToOne
    @JoinColumn(name = "kit_id")
    private Kit kit;

    private LocalDate date;
    private PresenceTypeEnum presenceType;

}