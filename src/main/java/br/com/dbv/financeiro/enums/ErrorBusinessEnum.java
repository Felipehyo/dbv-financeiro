package br.com.dbv.financeiro.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorBusinessEnum {

    CASH_BOOK_NOT_FOUND("Cash book not found", HttpStatus.NOT_FOUND),
    EVENT_NOT_FOUND("Event not found", HttpStatus.NOT_FOUND),
    CLUB_NOT_FOUND("Club not found", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus httpStatus;

}