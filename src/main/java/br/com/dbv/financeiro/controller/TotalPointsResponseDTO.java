package br.com.dbv.financeiro.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalPointsResponseDTO {

    private String unit;
    private Integer total;

}