package br.com.dbv.financeiro.dto;

import br.com.dbv.financeiro.model.Kit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KitDTO {

    private Boolean scarf;
    private Boolean bible;
    private Boolean activityNotebook;
    private Boolean bottle;
    private Boolean cap;
    private Boolean pencil;
    private Boolean bibleStudy;

    public Kit convert() {

        Kit kit = new Kit();
        kit.setScarf(scarf);
        kit.setBible(bible);
        kit.setActivityNotebook(activityNotebook);
        kit.setBottle(bottle);
        kit.setCap(cap);
        kit.setPencil(pencil);
        kit.setBibleStudy(bibleStudy);

        return kit;

    }

}