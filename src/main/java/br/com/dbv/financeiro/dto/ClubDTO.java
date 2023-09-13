package br.com.dbv.financeiro.dto;

import br.com.dbv.financeiro.model.Club;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClubDTO {

    private String name;

    public Club convert() {

        Club club = new Club();
        club.setName(name);

        return club;

    }

}