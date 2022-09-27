package br.com.dbv.financeiro.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserTypeEnum {

    EXECUTIVE(1, "Leadership"),
    DIRECTION(2, "Direction"),
    PATHFINDER(3, "Pathfinder"),
    RESPONSIBLE(4, "Responsible");

    public Integer type;
    public String description;

}