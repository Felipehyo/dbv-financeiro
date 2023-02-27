package br.com.dbv.financeiro.controller;

import br.com.dbv.financeiro.dto.ErrorDTO;
import br.com.dbv.financeiro.dto.UnitDTO;
import br.com.dbv.financeiro.model.Unit;
import br.com.dbv.financeiro.repository.ClubRepository;
import br.com.dbv.financeiro.repository.UnitRepository;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/unit")
public class UnitController {

    @Autowired
    private UnitRepository repository;

    @Autowired
    private ClubRepository clubRepository;

    @GetMapping()
    public ResponseEntity<?> getAllUnits() {

        return ResponseEntity.ok().body(repository.findAll());

    }

    @GetMapping("/club/{clubId}")
    public ResponseEntity<?> getUnitsByClub(@PathVariable("clubId") Long clubId) {

        var club = clubRepository.findById(clubId);

        if (!club.isPresent()) {
            return ResponseEntity.badRequest().body(new ErrorDTO("400", "Club not found", "Club not found in database"));
        }

        return ResponseEntity.ok().body(repository.findByClubId(clubId));

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUnit(@PathVariable("id") Long id) {

        return ResponseEntity.ok().body(repository.findById(id));

    }

    @PostMapping
    public ResponseEntity<?> createUnit(@RequestBody UnitDTO request) {

        var club = clubRepository.findById(request.getClubId());

        if (!club.isPresent()) {
            return ResponseEntity.badRequest().body(new ErrorDTO("400", "Club not found", "Club not found in database"));
        }

        Unit units = request.convert(club.get());

        return ResponseEntity.ok().body(repository.save(units));

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putUnit(@PathVariable("id") Long id, @RequestBody UnitDTO request) {

        var unit = repository.findById(id).get();
        if (!StringUtils.isBlank(request.getName())) unit.setName(request.getName());
        if (request.getQtdPoints() != null && request.getQtdPoints() >= 0)
            unit.setQtdPoints(request.getQtdPoints());

        repository.save(unit);

        return ResponseEntity.ok().body(unit);

    }

    @PatchMapping("/{id}/add/{qtdPoints}")
    public ResponseEntity<?> addPoints(@PathVariable("id") Long id, @PathVariable("qtdPoints") Integer qtdPoints) {

        var unit = repository.findById(id).get();
        unit.setQtdPoints(unit.getQtdPoints() + qtdPoints);
        repository.save(unit);

        return ResponseEntity.ok().body(unit);

    }

    @PatchMapping("/{id}/remove/{qtdPoints}")
    public ResponseEntity<?> removePoints(@PathVariable("id") Long id, @PathVariable("qtdPoints") Integer qtdPoints) {

        var unit = repository.findById(id).get();
        unit.setQtdPoints(unit.getQtdPoints() - qtdPoints);
        repository.save(unit);

        return ResponseEntity.ok().body(unit);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUnit(@PathVariable("id") Long id) {

        repository.delete(repository.findById(id).get());

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
