package br.com.dbv.financeiro.controller;

import br.com.dbv.financeiro.dto.movement.UserMovementHistoryRequestDTO;
import br.com.dbv.financeiro.exception.CustomException;
import br.com.dbv.financeiro.service.UserMovementHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-movement-history")
public class UserMovementHistoryController {

    @Autowired
    private UserMovementHistoryService service;

    @PostMapping
    public ResponseEntity<?> createMovement(@RequestBody UserMovementHistoryRequestDTO request) {
        try {
            return ResponseEntity.ok().body(service.movement(request));
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getError());
        }
    }


}