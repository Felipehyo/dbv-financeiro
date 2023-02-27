package br.com.dbv.financeiro.dto;

import br.com.dbv.financeiro.enums.UserTypeEnum;
import br.com.dbv.financeiro.model.Club;
import br.com.dbv.financeiro.model.User;
import br.com.dbv.financeiro.model.Unit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String name;
    private UserTypeEnum userType;
    private Long unitId;
    private Date birthDate;
    private Long clubId;

    public User convert(Unit unit, Club club) {

        User user = new User();
        user.setName(this.name);
        user.setUserType(this.userType);
        user.setUnit(unit);
        user.setBirthDate(birthDate);
        user.setClub(club);

        return user;
    }

    public User convert(Club club) {

        User user = new User();
        user.setName(this.name);
        user.setUserType(this.userType);
        user.setBirthDate(birthDate);
        user.setClub(club);

        return user;
    }

}