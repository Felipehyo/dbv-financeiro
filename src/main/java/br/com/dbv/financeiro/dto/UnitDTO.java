package br.com.dbv.financeiro.dto;

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

    public Unit convert() {

        Unit unit = new Unit();
        unit.setName(name);
        unit.setQtdPoints(qtdPoints);
        unit.setImageLink(imageLink);
        unit.setUnitOrder(unitOrder);
        unit.setAssignment(assignment);

        return unit;
    }

}