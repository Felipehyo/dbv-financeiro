package br.com.dbv.financeiro.service.mapper;

import br.com.dbv.financeiro.dto.CashBookDTO;
import br.com.dbv.financeiro.model.CashBook;

public class CashBookMapper {

    public CashBookDTO toDto(CashBook cashBook) {
        return CashBookDTO.builder()
                .type(cashBook.getType())
                .description(cashBook.getDescription())
                .date(cashBook.getDate())
                .value(String.format("%.2f", cashBook.getValue()))
                .eventId(cashBook.getEvent() != null ? cashBook.getEvent().getId() : null)
                .build();

    }

}
