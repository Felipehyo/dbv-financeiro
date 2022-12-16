package br.com.dbv.financeiro.dto;

import br.com.dbv.financeiro.enums.UserTypeEnum;
import br.com.dbv.financeiro.model.User;
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


    public User convert(Unit unit) {

        User pathfinder = new User();
        pathfinder.setName(this.name);
        pathfinder.setUserType(this.userType);
        pathfinder.setUnit(unit);
        pathfinder.setBirthDate(birthDate);

        return pathfinder;
    }

    public User convert() {

        User pathfinder = new User();
        pathfinder.setName(this.name);
        pathfinder.setUserType(this.userType);
        pathfinder.setBirthDate(birthDate);

        return pathfinder;
    }

}