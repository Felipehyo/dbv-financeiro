package br.com.dbv.financeiro.dto;

import br.com.dbv.financeiro.model.Unit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalPointsResponseDTO {

    private Unit unit;
    private Integer total;

}