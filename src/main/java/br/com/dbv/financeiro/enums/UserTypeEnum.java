package br.com.dbv.financeiro.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserTypeEnum {

    EXECUTIVE("Executiva"),
    DIRECTION("Diretoria"),
    PATHFINDER("Desbravador"),
    EVENTUAL("Eventual");

    public String description;

}