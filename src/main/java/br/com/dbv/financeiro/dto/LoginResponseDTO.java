package br.com.dbv.financeiro.dto;

import br.com.dbv.financeiro.enums.UserTypeEnum;
import br.com.dbv.financeiro.model.User;
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

    public LoginResponseDTO(User p) {
        this.id = p.getId();
        this.name = p.getName();
        this.userType = p.getUserType();
    }

}