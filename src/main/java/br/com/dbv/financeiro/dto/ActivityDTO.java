package br.com.dbv.financeiro.dto;

import br.com.dbv.financeiro.model.Activity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDTO {

    private String name;
    private String description;
    private Integer merit = 0;
    private Integer demerit = 0;
    private Boolean saturday;
    private Boolean sunday;


    public Activity convert() {
        Activity activity = new Activity();
        activity.setName(this.name);
        activity.setDescription(this.description);
        activity.setMerit(this.merit);
        activity.setDemerit(this.demerit);
        activity.setSaturday(saturday);
        activity.setSunday(sunday);

        return activity;
    }

}