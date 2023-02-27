package br.com.dbv.financeiro.dto;

import br.com.dbv.financeiro.model.Club;
import br.com.dbv.financeiro.model.Unit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitDTO {

    private String name;
    private Integer qtdPoints = 0;
    private String imageLink;
    private Integer unitOrder;
    private String assignment;
    private Long clubId;

    public Unit convert(Club club) {

        Unit unit = new Unit();
        unit.setName(name);
        unit.setQtdPoints(qtdPoints);
        unit.setImageLink(imageLink);
        unit.setUnitOrder(unitOrder);
        unit.setAssignment(assignment);
        unit.setClub(club);

        return unit;
    }

}