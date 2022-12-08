package br.com.dbv.financeiro.controller;

import br.com.dbv.financeiro.dto.ErrorDTO;
import br.com.dbv.financeiro.dto.PathfinderDTO;
import br.com.dbv.financeiro.dto.UnitDTO;
import br.com.dbv.financeiro.model.Pathfinder;
import br.com.dbv.financeiro.model.Unit;
import br.com.dbv.financeiro.repository.PathfinderRepository;
import br.com.dbv.financeiro.repository.UnitRepository;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/user/pathfinder")
public class PathfinderController {

    @Autowired
    private PathfinderRepository repository;

    @Autowired
    private UnitRepository unitRepository;

    @GetMapping()
    public ResponseEntity<?> getAllUnits() {

        return ResponseEntity.ok().body(repository.findAll());

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPathfinderById(@PathVariable("id") UUID id) {

        return ResponseEntity.ok().body(repository.findById(id));

    }

    @GetMapping("/unit/{unitId}")
    public ResponseEntity<?> getPathfinderByUnitId(@PathVariable("unitId") Long unitId) {

        return ResponseEntity.ok().body(repository.findByUnitId(unitId));

    }

    @PostMapping
    public ResponseEntity<?> createUnit(@RequestBody PathfinderDTO request) {

        Optional<Unit> unit = unitRepository.findById(request.getUnitId());

        if (!unit.isPresent()) {
            return ResponseEntity.badRequest().body(new ErrorDTO("400", "Unit not found", "Unit not found in database"));
        }

        Pathfinder pathfinder = request.convert(unit.get());

        return ResponseEntity.ok().body(repository.save(pathfinder));

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
