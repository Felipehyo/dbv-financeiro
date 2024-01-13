package br.com.dbv.financeiro.dto.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventDTO {

    private String name;
    private Double value;
    private LocalDate date;

}
