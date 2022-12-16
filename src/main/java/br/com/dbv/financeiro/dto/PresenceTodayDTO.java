package br.com.dbv.financeiro.dto;

import br.com.dbv.financeiro.enums.PresenceTypeEnum;
import br.com.dbv.financeiro.model.Pathfinder;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PresenceTodayDTO {

    private PresenceTypeEnum status;
    private Pathfinder user;

    public String getUserName(){
        return this.user.getName();
    }

}