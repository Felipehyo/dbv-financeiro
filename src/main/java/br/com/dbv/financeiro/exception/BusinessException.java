package br.com.dbv.financeiro.exception;

import br.com.dbv.financeiro.enums.ErrorBusinessEnum;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorBusinessEnum error;

    public BusinessException(ErrorBusinessEnum error) {
        this.error = error;
    }

}
