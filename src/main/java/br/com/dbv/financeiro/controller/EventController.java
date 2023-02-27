package br.com.dbv.financeiro.controller;

import br.com.dbv.financeiro.dto.ErrorDTO;
import br.com.dbv.financeiro.dto.EventDTO;
import br.com.dbv.financeiro.model.Event;
import br.com.dbv.financeiro.repository.ClubRepository;
import br.com.dbv.financeiro.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventRepository repository;


    @Autowired
    private ClubRepository clubRepository;

    @GetMapping("/club/{clubId}")
    public ResponseEntity<?> getAllEventsByClub(@PathVariable("clubId") Long clubId) {

        return ResponseEntity.ok().body(repository.findByClubId(clubId));

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEvent(@PathVariable("id") Long id) {

        return ResponseEntity.ok().body(repository.findById(id));

    }

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody EventDTO request) {

        var club = clubRepository.findById(request.getClubId());

        if (!club.isPresent()) {
            return ResponseEntity.badRequest().body(new ErrorDTO("400", "Club not found", "Club not found in database"));
        }

        Event event = request.convert(club.get());

        return ResponseEntity.ok().body(repository.save(event));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable("id") Long id) {

        repository.delete(repository.findById(id).get());

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
