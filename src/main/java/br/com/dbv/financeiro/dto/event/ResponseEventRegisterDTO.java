package br.com.dbv.financeiro.dto.event;


import br.com.dbv.financeiro.enums.GenderEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseEventRegisterDTO {

    private String event;
    private UUID userId;
    private String user;
    private GenderEnum userGender;
    private Double userBank;
    private Double eventAllocatedAmount;
    private Double percentagePayment;


}
