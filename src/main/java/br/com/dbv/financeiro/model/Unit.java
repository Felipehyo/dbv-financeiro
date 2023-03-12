package br.com.dbv.financeiro.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "UNIT")
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    private Integer qtdPoints;
    private String imageLink;
    private String assignment;
    private Integer unitOrder;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    private Integer deliveryPendingPoints = 0;

}