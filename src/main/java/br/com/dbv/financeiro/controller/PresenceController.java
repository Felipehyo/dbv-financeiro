package br.com.dbv.financeiro.controller;

import br.com.dbv.financeiro.dto.ErrorDTO;
import br.com.dbv.financeiro.dto.KitDTO;
import br.com.dbv.financeiro.dto.PresenceDTO;
import br.com.dbv.financeiro.model.Kit;
import br.com.dbv.financeiro.model.Pathfinder;
import br.com.dbv.financeiro.model.Presence;
import br.com.dbv.financeiro.repository.KitRepository;
import br.com.dbv.financeiro.repository.PathfinderRepository;
import br.com.dbv.financeiro.repository.PresenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/presence")
public class PresenceController {

    @Autowired
    private PresenceRepository repository;

    @Autowired
    private PathfinderRepository pathfinderRepository;

    @Autowired
    private KitRepository kitRepository;

    @GetMapping()
    public ResponseEntity<?> getAllUnits() {

        return ResponseEntity.ok().body(repository.findAll());

    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getPathfinderById(@PathVariable("userId") UUID userId) {

        return ResponseEntity.ok().body(repository.findByPathfinderId(userId));

    }

    @PostMapping("/{userId}")
    public ResponseEntity<?> createUnit(@PathVariable("userId") UUID userId, @RequestBody PresenceDTO request) {

        Optional<Pathfinder> pathfinder = pathfinderRepository.findById(userId);

        if (!pathfinder.isPresent()) {
            return ResponseEntity.badRequest().body(new ErrorDTO("400", "Pathfinder not found", "Pathfinder not found in database"));
        }

        Kit kit = kitRepository.save(request.getKit().convert());

        Presence presence = request.convert(pathfinder.get(), kit);

        return ResponseEntity.ok().body(repository.save(presence));

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePresence(@PathVariable("id") Long id) {

        repository.delete(repository.findById(id).get());

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
