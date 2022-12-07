package br.com.dbv.financeiro.dto;

import br.com.dbv.financeiro.model.Activities;
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


    public Activities convert(){
        Activities activity = new Activities();
        activity.setName(this.name);
        activity.setDescription(this.description);
        activity.setMerit(this.merit);
        activity.setDemerit(this.demerit);

        return activity;
    }

}