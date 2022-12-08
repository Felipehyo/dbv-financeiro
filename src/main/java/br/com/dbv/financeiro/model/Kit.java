package br.com.dbv.financeiro.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KIT")
public class Kit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Boolean scarf;
    private Boolean bible;
    private Boolean activityNotebook;
    private Boolean bottle;
    private Boolean cap;
    private Boolean pencil;
    private Boolean bibleStudy;

}