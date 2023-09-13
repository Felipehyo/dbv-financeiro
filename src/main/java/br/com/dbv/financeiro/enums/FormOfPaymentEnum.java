package br.com.dbv.financeiro.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum FormOfPaymentEnum {

    PIX("PIX", "Pix"),
    CHURCH("CHURCH", "Igreja"),
    CASH("CASH", "Dinheiro");

    public final String value;
    public final String description;

}