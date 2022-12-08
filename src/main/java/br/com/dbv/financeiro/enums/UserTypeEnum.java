package br.com.dbv.financeiro.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserTypeEnum {

    EXECUTIVE("Leadership"),
    DIRECTION("Direction"),
    PATHFINDER("Pathfinder"),
    RESPONSIBLE("Responsible");

    public String description;

}