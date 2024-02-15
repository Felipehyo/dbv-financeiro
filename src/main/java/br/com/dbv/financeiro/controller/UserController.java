package br.com.dbv.financeiro.controller;

import br.com.dbv.financeiro.dto.UserDTO;
import br.com.dbv.financeiro.enums.UserTypeEnum;
import br.com.dbv.financeiro.exception.CustomException;
import br.com.dbv.financeiro.repository.UserRepository;
import br.com.dbv.financeiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserService service;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") UUID id) {

        try {
            return ResponseEntity.ok().body(service.getUsersById(id));
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getError());
        }

    }

    @GetMapping("/club/{clubId}")
    public ResponseEntity<?> getUsersByClub(@PathVariable("clubId") Long id, @PathParam("eventualUser") Boolean eventualUser, @PathParam("onlyActives") Boolean onlyActives) {

        return ResponseEntity.ok().body(service.getUsersByClubWithEventualUsers(id, eventualUser, onlyActives));

    }

    @GetMapping("/club/{clubId}/type")
    public ResponseEntity<?> getUsersByClub(@PathVariable("clubId") Long id, @PathParam("userType") UserTypeEnum userType) {

        try {
            return ResponseEntity.ok().body(service.getUsersByClubAndUserType(id, userType));
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getError());
        }

    }

    @GetMapping("/unit/{unitId}")
    public ResponseEntity<?> getUserByUnitId(@PathVariable("unitId") Long unitId) {

        return ResponseEntity.ok().body(service.getUserByUnitId(unitId));

    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDTO request) {

        try {
            return ResponseEntity.ok().body(service.createUser(request));
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getError());
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") UUID userId, @RequestBody UserDTO request) {

        try {
            return ResponseEntity.ok().body(service.updateUser(userId, request));
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getError());
        }

    }

    @PatchMapping("/{id}/update-status")
    public ResponseEntity<?> activeOrInactiveUser(@PathVariable("id") UUID userId) {

        try {
            service.activeOrInactiveUser(userId);
            return ResponseEntity.noContent().build();
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getError());
        }

    }

}
