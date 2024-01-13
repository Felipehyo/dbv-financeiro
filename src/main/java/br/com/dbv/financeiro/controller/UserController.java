package br.com.dbv.financeiro.controller;

import br.com.dbv.financeiro.dto.ErrorDTO;
import br.com.dbv.financeiro.dto.UserDTO;
import br.com.dbv.financeiro.model.Pathfinder;
import br.com.dbv.financeiro.model.Unit;
import br.com.dbv.financeiro.repository.ClubRepository;
import br.com.dbv.financeiro.repository.UserRepository;
import br.com.dbv.financeiro.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private ClubRepository clubRepository;

    @GetMapping("/club/{clubId}")
    public ResponseEntity<?> getUsersByClub(@PathVariable("clubId") Long id) {

        return ResponseEntity.ok().body(repository.findByClubIdAndActive(id, Boolean.TRUE));

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") UUID id) {

        return ResponseEntity.ok().body(repository.findById(id));

    }

    @GetMapping("/unit/{unitId}")
    public ResponseEntity<?> getUserByUnitId(@PathVariable("unitId") Long unitId) {

        return ResponseEntity.ok().body(repository.findByUnitIdAndActive(unitId, Boolean.TRUE));

    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDTO request) {

        Optional<Unit> unit;
        Pathfinder user;

        var club = clubRepository.findById(request.getClubId());

        if (!club.isPresent()) {
            return ResponseEntity.badRequest().body(new ErrorDTO("400", "Club not found", "Club not found in database"));
        }

        var oldUser = repository.findByName(request.getName());

        if (oldUser.isPresent()) {
            return ResponseEntity.badRequest().body(new ErrorDTO("400", "User already exists", "User already exists"));
        }

        if (request.getUnitId() != null) {
            unit = unitRepository.findById(request.getUnitId());

            if (!unit.isPresent()) {
                return ResponseEntity.badRequest().body(new ErrorDTO("400", "Unit not found", "Unit not found in database"));
            }

            user = request.convert(unit.get(), club.get());
        } else {
            user = request.convert(club.get());
        }

        return ResponseEntity.ok().body(repository.save(user));

    }

//    @PutMapping("/{id}")
//    public ResponseEntity<?> putUnit(@PathVariable("id") Long id, @RequestBody UnitDTO request) {
//
//        var unit = repository.findById(id).get();
//        if (!StringUtils.isBlank(request.getName())) unit.setName(request.getName());
//        if (request.getQtdPoints() != null && request.getQtdPoints() >= 0)
//            unit.setQtdPoints(request.getQtdPoints());
//
//        repository.save(unit);
//
//        return ResponseEntity.ok().body(unit);
//
//    }
//
//    @PatchMapping("/{id}/add/{qtdPoints}")
//    public ResponseEntity<?> addPoints(@PathVariable("id") Long id, @PathVariable("qtdPoints") Integer qtdPoints) {
//
//        var unit = repository.findById(id).get();
//        unit.setQtdPoints(unit.getQtdPoints() + qtdPoints);
//        repository.save(unit);
//
//        return ResponseEntity.ok().body(unit);
//
//    }
//
//    @PatchMapping("/{id}/remove/{qtdPoints}")
//    public ResponseEntity<?> removePoints(@PathVariable("id") Long id, @PathVariable("qtdPoints") Integer qtdPoints) {
//
//        var unit = repository.findById(id).get();
//        unit.setQtdPoints(unit.getQtdPoints() - qtdPoints);
//        repository.save(unit);
//
//        return ResponseEntity.ok().body(unit);
//
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteUnit(@PathVariable("id") Long id) {
//
//        repository.delete(repository.findById(id).get());
//
//        return new ResponseEntity<>(HttpStatus.OK);
//
//    }

}
