package br.com.dbv.financeiro.dto;

import br.com.dbv.financeiro.model.Activity;
import br.com.dbv.financeiro.model.Club;
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
    private Integer activityOrder;
    private Boolean alwaysDisplay;
    private Long clubId;

    public Activity convert(Club club) {
        Activity activity = new Activity();
        activity.setName(name);
        activity.setDescription(description);
        activity.setMerit(merit);
        activity.setDemerit(demerit);
        activity.setActivityOrder(activityOrder);
        activity.setAlwaysDisplay(alwaysDisplay);
        activity.setClub(club);

        return activity;
    }

}