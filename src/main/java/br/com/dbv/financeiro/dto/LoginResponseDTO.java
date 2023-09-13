package br.com.dbv.financeiro.dto;

import br.com.dbv.financeiro.enums.UserTypeEnum;
import br.com.dbv.financeiro.model.Pathfinder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {

    private UUID id;
    private String name;
    private UserTypeEnum userType;
    private Long clubId;
    private String clubName;

    public LoginResponseDTO(Pathfinder p) {
        this.id = p.getId();
        this.name = p.getName();
        this.userType = p.getUserType();
        this.clubId = p.getClub().getId();
        this.clubName = p.getClub().getName();
    }

}