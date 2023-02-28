package br.com.dbv.financeiro.dto;

import br.com.dbv.financeiro.enums.RecordTypeEnum;
import br.com.dbv.financeiro.model.Activity;
import br.com.dbv.financeiro.model.ActivityRecord;
import br.com.dbv.financeiro.model.Unit;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityRecordDTO {

    private String title;
    private String reason;
    private RecordTypeEnum type;
    private Integer points = 0;


    public ActivityRecord convert(Unit unit, Activity activity) {

        ActivityRecord record = new ActivityRecord();
        record.setUnit(unit);
        record.setActivity(activity);
        record.setDate(LocalDate.now());
        record.setCreatedDate(LocalDateTime.now());
        record.setType(type);

        if (activity == null) {
            record.setTitle(title);
            record.setReason(reason);
            record.setPoints(type == RecordTypeEnum.MERIT ? points : -points);
        } else {
            record.setTitle(activity.getName());
            record.setReason(activity.getDescription());
            record.setPoints(type == RecordTypeEnum.MERIT ? activity.getMerit() : type == RecordTypeEnum.DEMERIT ? -activity.getDemerit() : 0);
        }

        return record;

    }

}