package br.com.dbv.financeiro.controller;

import br.com.dbv.financeiro.dto.ErrorDTO;
import br.com.dbv.financeiro.dto.EventRegisterDTO;
import br.com.dbv.financeiro.model.EventRegister;
import br.com.dbv.financeiro.repository.EventRegisterRepository;
import br.com.dbv.financeiro.repository.EventRepository;
import br.com.dbv.financeiro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event/register")
public class EventRegisterController {

    @Autowired
    private EventRegisterRepository repository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{eventId}")
    public ResponseEntity<?> getAllEventRegisterByEvent(@PathVariable("eventId") Long eventId) {

        return ResponseEntity.ok().body(repository.findByEventId(eventId));

    }

    @GetMapping("/{eventId}/count")
    public ResponseEntity<?> getCountRegistersByClub(@PathVariable("eventId") Long eventId) {

        return ResponseEntity.ok().body(repository.findByEventId(eventId).size());

    }

    @PostMapping
    public ResponseEntity<?> createEventRegister(@RequestBody EventRegisterDTO request) {

        var event = eventRepository.findById(request.getEventId());
        var pathfinder = userRepository.findById(request.getPathfinderId());

        if (!event.isPresent() || !pathfinder.isPresent()) {
            return ResponseEntity.badRequest().body(new ErrorDTO("400", "Event or Pathfinder not found", "Event or Pathfinder not found in database"));
        }

        var exist = repository.findByEventIdAndPathfinderId(event.get().getId(), pathfinder.get().getId());

        if (exist != null) {
            return ResponseEntity.ok().body(exist);
        }

        EventRegister eventRegister = request.convert(event.get(), pathfinder.get());
        pathfinder.get().setBank(pathfinder.get().getBank() - event.get().getValue());

        return ResponseEntity.ok().body(repository.save(eventRegister));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEventRegister(@PathVariable("id") Long id) {

        repository.delete(repository.findById(id).get());

        return new ResponseEntity<>(HttpStatus.OK);

    }

}