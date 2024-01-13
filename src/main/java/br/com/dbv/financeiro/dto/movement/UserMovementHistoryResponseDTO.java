package br.com.dbv.financeiro.dto.movement;

import br.com.dbv.financeiro.enums.MovementTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserMovementHistoryResponseDTO {

    private Long eventId;
    private UUID pathfinderId;
    private MovementTypeEnum movementType;
    private Double amount;
    private Double userBank;
    private Double eventBank;

}