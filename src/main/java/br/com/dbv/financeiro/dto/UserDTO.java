package br.com.dbv.financeiro.dto;

import br.com.dbv.financeiro.enums.GenderEnum;
import br.com.dbv.financeiro.enums.UserTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {

    private UUID id;
    private String name;
    private UserTypeEnum userType;
    private Long unitId;
    private Date birthDate;
    private Long clubId;
    private Double bank;
    private GenderEnum gender;
    private Boolean active;

}