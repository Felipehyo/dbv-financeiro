package br.com.dbv.financeiro.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MovementTypeEnum {

    TRANSFER_TO_EVENT,
    TRANSFER_TO_USER_BANK

}