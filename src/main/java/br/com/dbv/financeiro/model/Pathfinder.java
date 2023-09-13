package br.com.dbv.financeiro.model;

import br.com.dbv.financeiro.enums.UserTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PATHFINDER")
public class Pathfinder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @Column(name = "Name", length = 130, nullable = false)
    private String name;

    @Column(name = "Email", length = 50)
    private String email;

    @Column(name = "Password", length = 16)
    private String password;

    @Column(name = "UserType", length = 12, nullable = false)
    private UserTypeEnum userType;

    @OneToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    private Boolean active = Boolean.TRUE;

    @Temporal(TemporalType.DATE)
    @Column(name = "Birthdate", nullable = false)
    private Date birthDate;

    private Double bank = 0.0;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

}