package br.com.dbv.financeiro.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserTypeEnum {

    EXECUTIVE("Executive"),
    DIRECTION("Direction"),
    PATHFINDER("Pathfinder"),
    EVENTUAL("Eventual");

    public String description;

}