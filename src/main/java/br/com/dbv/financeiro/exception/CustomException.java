package br.com.dbv.financeiro.exception;

import br.com.dbv.financeiro.dto.ErrorDTO;
import lombok.Getter;

@Getter
public class CustomException extends Exception {

    private ErrorDTO error;

    public CustomException(ErrorDTO error) {
        this.error = error;
    }

}
