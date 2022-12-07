package br.com.dbv.financeiro.model;

import br.com.dbv.financeiro.enums.RecordTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private Units unit;

    @OneToOne
    @JoinColumn(name = "activity_id")
    private Activities activity;

    private RecordTypeEnum type;
    private String reason;
    private LocalDateTime date;
    private Integer points = 0;

}