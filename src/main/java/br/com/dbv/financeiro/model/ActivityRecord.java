package br.com.dbv.financeiro.model;

import br.com.dbv.financeiro.enums.RecordTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ACTIVITY_RECORD")
public class ActivityRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @OneToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @NotNull
    private RecordTypeEnum type;

    private String reason;
    private LocalDateTime createdDate;
    private LocalDate date;
    private Integer points = 0;

}