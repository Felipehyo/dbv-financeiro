package br.com.dbv.financeiro.controller;

import br.com.dbv.financeiro.dto.ErrorDTO;
import br.com.dbv.financeiro.dto.PaymentDTO;
import br.com.dbv.financeiro.model.Event;
import br.com.dbv.financeiro.model.Payment;
import br.com.dbv.financeiro.repository.ClubRepository;
import br.com.dbv.financeiro.repository.EventRepository;
import br.com.dbv.financeiro.repository.PaymentRepository;
import br.com.dbv.financeiro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentRepository repository;

    @GetMapping("/club/{clubId}")
    public ResponseEntity<?> getAllPaymentsByClub(@PathVariable("clubId") Long clubId) {

        return ResponseEntity.ok().body(repository.findByClubId(clubId));

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPayment(@PathVariable("id") UUID id) {

        return ResponseEntity.ok().body(repository.findById(id));

    }

    @PostMapping
    public ResponseEntity<?> createPayment(@RequestBody PaymentDTO request) {

        var club = clubRepository.findById(request.getClubId());

        if (!club.isPresent()) {
            return ResponseEntity.badRequest().body(new ErrorDTO("400", "Club not found", "Club not found in database"));
        }

        Optional<Event> event = null;

        if (request.getEventId() != null) {
            event = eventRepository.findById(request.getEventId());

            if (!event.isPresent()) {
                return ResponseEntity.badRequest().body(new ErrorDTO("400", "Event not found", "Event not found in database"));
            }
        }

        var user = userRepository.findById(request.getUserId());

        if (!user.isPresent()) {
            return ResponseEntity.badRequest().body(new ErrorDTO("400", "User not found", "User not found in database"));
        }

        Payment payment = request.convert(club.get(), (event != null ? event.get() : null), user.get());

        club.get().setBank(club.get().getBank() + request.getValue());

        return ResponseEntity.ok().body(repository.save(payment));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable("id") UUID id) {

        try {
            Payment payment = repository.findById(id).get();
            payment.getClub().setBank(payment.getClub().getBank() - payment.getValue());
            repository.delete(payment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDTO("404", "Payment not found", "Payment does not exist or has already been deleted"));
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

}