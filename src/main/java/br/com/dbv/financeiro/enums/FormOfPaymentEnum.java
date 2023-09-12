package br.com.dbv.financeiro.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum FormOfPaymentEnum {

    PIX("PIX"),
    CHURCH("CHURCH"),
    CASH("CASH");

    public final String value;

}