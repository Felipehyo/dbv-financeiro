package br.com.dbv.financeiro.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RecordTypeEnum {

    MERIT(1, "MERIT"),
    DEMERIT(2, "DEMERIT"),
    NOT_SCORE(2, "NOT_SCORE");

    public Integer type;
    public String description;

}