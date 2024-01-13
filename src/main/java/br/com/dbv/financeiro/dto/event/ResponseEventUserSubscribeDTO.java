package br.com.dbv.financeiro.dto.event;


import br.com.dbv.financeiro.enums.GenderEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
public class ResponseEventUserSubscribeDTO {

    private String event;
    private UUID userId;
    private String user;
    private GenderEnum userGender;
    private Boolean subscribed;

    public ResponseEventUserSubscribeDTO(String event, UUID userId, String user, GenderEnum userGender, Boolean subscribed) {
        this.event = event;
        this.userId = userId;
        this.user = user;
        this.userGender = userGender;
        this.subscribed = subscribed;
    }
}
