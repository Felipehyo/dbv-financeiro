package br.com.dbv.financeiro.dto;

import br.com.dbv.financeiro.model.Units;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitDTO {

    private String name;
    private Integer qtdPoints = 0;

    public Units convert() {

        Units unit = new Units();
        unit.setName(this.name);
        unit.setQtdPoints(this.qtdPoints);

        return unit;
    }

}