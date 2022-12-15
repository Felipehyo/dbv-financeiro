package br.com.dbv.financeiro.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserTypeEnum {

    EXECUTIVE("Executive"),
    DIRECTION("Direction"),
    PATHFINDER("Pathfinder"),
    RESPONSIBLE("Responsible");

    public String description;

}