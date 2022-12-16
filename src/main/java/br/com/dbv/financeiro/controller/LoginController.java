package br.com.dbv.financeiro.controller;

import br.com.dbv.financeiro.dto.ErrorDTO;
import br.com.dbv.financeiro.dto.LoginDTO;
import br.com.dbv.financeiro.dto.LoginResponseDTO;
import br.com.dbv.financeiro.model.User;
import br.com.dbv.financeiro.repository.PathfinderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/user/login")
public class LoginController {

    @Autowired
    private PathfinderRepository repository;

    @PostMapping
    public ResponseEntity<?> doLogin(@RequestBody LoginDTO request) {

        Optional<User> user = repository.findByEmail(request.getEmail());

        if (!user.isPresent()) {
            return ResponseEntity.badRequest().body(new ErrorDTO("400", "User not found", "User not found in database"));
        }

        if (!user.get().getPassword().equals(request.getPassword()))
            return ResponseEntity.badRequest().body(new ErrorDTO("400", "Incorrect username or password", "Check the information and try again"));

        return ResponseEntity.ok().body(new LoginResponseDTO(user.get()));

    }

}