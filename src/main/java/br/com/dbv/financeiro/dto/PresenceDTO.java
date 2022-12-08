package br.com.dbv.financeiro.dto;

import br.com.dbv.financeiro.enums.PresenceTypeEnum;
import br.com.dbv.financeiro.model.Kit;
import br.com.dbv.financeiro.model.Pathfinder;
import br.com.dbv.financeiro.model.Presence;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PresenceDTO {

    private KitDTO kit;
    private PresenceTypeEnum presenceType;

    public Presence convert(Pathfinder pathfinder, Kit kit) {

        Presence presence = new Presence();
        presence.setPathfinder(pathfinder);
        presence.setDate(LocalDate.now());
        presence.setKit(kit);
        presence.setPresenceType(presenceType);

        return presence;
    }

}
