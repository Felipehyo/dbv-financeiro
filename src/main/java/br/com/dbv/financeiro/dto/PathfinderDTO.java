package br.com.dbv.financeiro.dto;

import br.com.dbv.financeiro.enums.UserTypeEnum;
import br.com.dbv.financeiro.model.Pathfinder;
import br.com.dbv.financeiro.model.Unit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PathfinderDTO {

    private String name;
    private UserTypeEnum userType;
    private Long unitId;
    private Date birthDate;


    public Pathfinder convert(Unit unit) {

        Pathfinder pathfinder = new Pathfinder();
        pathfinder.setName(this.name);
        pathfinder.setUserType(this.userType);
        pathfinder.setUnit(unit);
        pathfinder.setBirthDate(birthDate);

        return pathfinder;
    }

    public Pathfinder convert() {

        Pathfinder pathfinder = new Pathfinder();
        pathfinder.setName(this.name);
        pathfinder.setUserType(this.userType);
        pathfinder.setBirthDate(birthDate);

        return pathfinder;
    }

}