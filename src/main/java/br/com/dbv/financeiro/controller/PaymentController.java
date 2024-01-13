package br.com.dbv.financeiro.controller;

import br.com.dbv.financeiro.dto.ErrorDTO;
import br.com.dbv.financeiro.dto.PaymentDTO;
import br.com.dbv.financeiro.dto.event.EventRegisterDTO;
import br.com.dbv.financeiro.dto.movement.UserMovementHistoryRequestDTO;
import br.com.dbv.financeiro.dto.reponse.PaymentResponseDTO;
import br.com.dbv.financeiro.enums.EventTypeRegisterEnum;
import br.com.dbv.financeiro.enums.MovementTypeEnum;
import br.com.dbv.financeiro.exception.CustomException;
import br.com.dbv.financeiro.model.Event;
import br.com.dbv.financeiro.model.Payment;
import br.com.dbv.financeiro.repository.*;
import br.com.dbv.financeiro.service.EventService;
import br.com.dbv.financeiro.service.UserMovementHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @Autowired
    private EventRegisterRepository eventRegisterRepository;

    @Autowired
    private UserMovementHistoryService movementHistoryService;

    @Autowired
    private EventService eventService;

    @GetMapping("/club/{clubId}")
    public ResponseEntity<?> getAllPaymentsByClub(@PathVariable("clubId") Long clubId) {

        var payments = repository.findByClubId(clubId);

        var paymentsResponse = new ArrayList<>();

        payments.forEach(p -> paymentsResponse.add(new PaymentResponseDTO(p)));

        return ResponseEntity.ok().body(paymentsResponse);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPayment(@PathVariable("id") UUID id) {

        return ResponseEntity.ok().body(repository.findById(id));

    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getByUser(@PathVariable("id") UUID id) {

        return ResponseEntity.ok().body(repository.findByPathfinderId(id));

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
        user.get().setBank(user.get().getBank() + request.getValue());

        var paymentResponse = repository.save(payment);

        //verifica se foi associado a algum evento e faz a devida distribuição
        if (event != null) {
            try {
                var eventRegister = eventRegisterRepository.findByEventIdAndPathfinderId(event.get().getId(), user.get().getId());

                if (eventRegister.isPresent()) {
                    movementHistoryService.movement(UserMovementHistoryRequestDTO.builder()
                            .movementType(MovementTypeEnum.TRANSFER_TO_EVENT)
                            .amount(request.getValue())
                            .pathfinderId(user.get().getId())
                            .eventId(event.get().getId())
                            .build());
                } else {
                    eventService.registerUserInEvent(EventRegisterDTO.builder()
                            .eventId(event.get().getId())
                            .pathfinderId(user.get().getId())
                            .registerType(EventTypeRegisterEnum.SUBSCRIBE)
                            .build());
                }
            } catch (CustomException e) {
                return ResponseEntity.badRequest().body(e.getError());
            }
        }

        return ResponseEntity.ok().body(paymentResponse);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> paymentDelete(@PathVariable("id") UUID id) {

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