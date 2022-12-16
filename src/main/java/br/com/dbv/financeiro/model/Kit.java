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

    private Boolean scarf = Boolean.FALSE;
    private Boolean bible = Boolean.FALSE;
    private Boolean activityNotebook = Boolean.FALSE;
    private Boolean bottle = Boolean.FALSE;
    private Boolean cap = Boolean.FALSE;
    private Boolean pencil = Boolean.FALSE;
    private Boolean bibleStudy = Boolean.FALSE;

}