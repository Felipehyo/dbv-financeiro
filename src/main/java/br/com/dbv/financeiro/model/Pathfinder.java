package br.com.dbv.financeiro.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Pathfinder extends User {

    private String age;
    private String sex;
    private String birthDate;
    private List<Responsible> responsible;

}