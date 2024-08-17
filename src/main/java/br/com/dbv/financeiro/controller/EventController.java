package br.com.dbv.financeiro.controller;

import br.com.dbv.financeiro.dto.ErrorDTO;
import br.com.dbv.financeiro.dto.event.EventDTO;
import br.com.dbv.financeiro.dto.event.UpdateEventDTO;
import br.com.dbv.financeiro.model.Event;
import br.com.dbv.financeiro.repository.ClubRepository;
import br.com.dbv.financeiro.repository.EventRepository;
import br.com.dbv.financeiro.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventRepository repository;


    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventService eventService;

    @GetMapping("/club/{clubId}")
    public ResponseEntity<?> getAllEventsByClub(@PathVariable("clubId") Long clubId, @RequestParam(required = false) Boolean showOnlyFutureDate) {

        Object response;

        if(showOnlyFutureDate == null || showOnlyFutureDate) {
            response = repository.getEventsAndRegisterByClub(clubId, LocalDate.now(ZoneId.of("America/Sao_Paulo")).minusDays(7));
        } else {
            response = repository.getEventsAndRegisterByClub(clubId);
        }

        return ResponseEntity.ok().body(response);

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

        var oldEvent = eventRepository.findByEvent(request.getName());

        if (oldEvent.isPresent()) {
            return ResponseEntity.badRequest().body(new ErrorDTO("400", "Event already registered", "This event already exists"));
        }

        Event event = request.convert(club.get());

        return ResponseEntity.ok().body(repository.save(event));

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable("id") Long id, @RequestBody UpdateEventDTO request) {

        return eventService.updateEvent(id, request);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable("id") Long id) {

        var event = repository.findById(id);

        if (!event.isPresent()) {
            return ResponseEntity.badRequest().body(new ErrorDTO("400", "Event not exists", "The id provided was not found"));
        }

        repository.delete(event.get());

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
