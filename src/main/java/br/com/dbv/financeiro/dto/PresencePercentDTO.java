package br.com.dbv.financeiro.dto;

import br.com.dbv.financeiro.model.Pathfinder;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PresencePercentDTO {

    private Pathfinder user;
    private Integer percent;

    public String getUserName() {
        return user.getName();
    }

}