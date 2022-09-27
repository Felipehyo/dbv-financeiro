package br.com.dbv.financeiro.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class BillingModel {

    private UUID id;
    private String message;
    private Double value;

}