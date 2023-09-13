package br.com.dbv.financeiro.controller;

import br.com.dbv.financeiro.dto.ClubBankDTO;
import br.com.dbv.financeiro.dto.ClubDTO;
import br.com.dbv.financeiro.dto.ErrorDTO;
import br.com.dbv.financeiro.model.Club;
import br.com.dbv.financeiro.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/club")
public class ClubController {

    @Autowired
    private ClubRepository repository;

    @GetMapping()
    public ResponseEntity<?> getAllClubs() {

        return ResponseEntity.ok().body(repository.findAll());

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClub(@PathVariable("id") Long id) {

        return ResponseEntity.ok().body(repository.findById(id));

    }

    @GetMapping("/bank/{id}")
    public ResponseEntity<?> getClubBank(@PathVariable("id") Long id) {

        var club = repository.findById(id);

        if (club.isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorDTO("400", "Club not found", "Club not found in database"));
        }

        return ResponseEntity.ok().body(new ClubBankDTO(club.get().getBank()));

    }

    @PostMapping
    public ResponseEntity<?> createClub(@RequestBody ClubDTO request) {

        Club club = request.convert();

        return ResponseEntity.ok().body(repository.save(club));

    }

    @PatchMapping("/{id}/deposit/{value}")
    public ResponseEntity<?> deposit(@PathVariable("id") Long id, @PathVariable("value") Double value) {

        var club = repository.findById(id).get();
        club.setBank(club.getBank() + value);
        repository.save(club);

        return ResponseEntity.ok().body(club);

    }

    @PatchMapping("/{id}/withdraw/{value}")
    public ResponseEntity<?> withdraw(@PathVariable("id") Long id, @PathVariable("value") Double value) {

        var club = repository.findById(id).get();
        club.setBank(club.getBank() - value);
        repository.save(club);

        return ResponseEntity.ok().body(club);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClub(@PathVariable("id") Long id) {

        repository.delete(repository.findById(id).get());

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
