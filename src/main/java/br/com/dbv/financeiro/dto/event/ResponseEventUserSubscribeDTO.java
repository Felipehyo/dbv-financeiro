package br.com.dbv.financeiro.dto.event;


import br.com.dbv.financeiro.enums.GenderEnum;
import br.com.dbv.financeiro.enums.UserTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseEventUserSubscribeDTO {

    private String event;
    private UUID userId;
    private String user;
    private GenderEnum userGender;
    private UserTypeEnum userType;
    private Boolean subscribed;

}
