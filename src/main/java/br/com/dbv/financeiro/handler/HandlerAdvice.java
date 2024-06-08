package br.com.dbv.financeiro.handler;

import br.com.dbv.financeiro.dto.ErrorDTO;
import br.com.dbv.financeiro.exception.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandlerAdvice {

    @ExceptionHandler(value = {BusinessException.class})
    public ResponseEntity<Object> handlerBusiness(BusinessException businessException) {
        var error = businessException.getError();
        return ResponseEntity.status(error.getHttpStatus()).body(new ErrorDTO(error.name(), error.getMessage(), ""));
    }

}
