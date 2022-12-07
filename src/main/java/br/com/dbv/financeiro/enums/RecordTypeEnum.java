package br.com.dbv.financeiro.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RecordTypeEnum {

    MERIT(1, "MERIT"),
    DEMERIT(2, "DEMERIT");

    public Integer type;
    public String description;

}