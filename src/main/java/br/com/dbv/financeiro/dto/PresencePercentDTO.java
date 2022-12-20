package br.com.dbv.financeiro.dto;

import br.com.dbv.financeiro.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PresencePercentDTO {

    private User user;
    private Integer percent;

    public String getUserName() {
        return user.getName();
    }

}