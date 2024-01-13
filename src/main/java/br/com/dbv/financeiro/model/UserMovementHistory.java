package br.com.dbv.financeiro.model;

import br.com.dbv.financeiro.enums.MovementTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USER_MOVEMENT_HISTORY")
public class UserMovementHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @OneToOne
    @JoinColumn(name = "pathfinder_id")
    private Pathfinder pathfinder;

    private MovementTypeEnum movementType;

    private Double amount;

    @CreatedDate
    private LocalDateTime createdDate;

}