package br.com.dbv.financeiro.controller;

import br.com.dbv.financeiro.dto.CashBookDTO;
import br.com.dbv.financeiro.dto.ErrorDTO;
import br.com.dbv.financeiro.dto.reponse.CashBookResponseDTO;
import br.com.dbv.financeiro.enums.BookTypeEnum;
import br.com.dbv.financeiro.model.CashBook;
import br.com.dbv.financeiro.model.Event;
import br.com.dbv.financeiro.repository.CashBookRepository;
import br.com.dbv.financeiro.repository.ClubRepository;
import br.com.dbv.financeiro.repository.EventRepository;
import br.com.dbv.financeiro.service.CashBookService;
import br.com.dbv.financeiro.service.mapper.CashBookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/cash-book")
public class CashBookController {

    @Autowired
    private CashBookRepository repository;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CashBookService service;

    private final CashBookMapper cashBookMapper = new CashBookMapper();

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(cashBookMapper.toDto(service.getById(id)));
    }

    @GetMapping("/club/{clubId}")
    public ResponseEntity<?> getCashBookByClub(@PathVariable("clubId") Long id,
//                                               @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
//                                               @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                               @RequestParam(value = "eventId", required = false) Long eventId) {

        var cashBookList = repository.findByClubIdAndEventId(id, eventId);

        var responseList = new ArrayList<>();

        for (var cashBook : cashBookList) {
            responseList.add(new CashBookResponseDTO(cashBook));
        }

        return ResponseEntity.ok().body(responseList);

    }

    @PostMapping
    public ResponseEntity<?> createCashBook(@RequestHeader(value = "clubId") Long clubId, @RequestBody CashBookDTO request) {

        var club = clubRepository.findById(clubId);

        if (club.isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorDTO("400", "Club not found", "Club not found in database"));
        }

        Event event = null;

        if (request.getEventId() != null) {
            var optionalEvent = eventRepository.findById(request.getEventId());
            if (optionalEvent.isPresent()) {
                event = optionalEvent.get();
            }
        }

        CashBook cashBook = request.convert(club.get(), event);

        if (cashBook.getType() == BookTypeEnum.INPUT) {
            club.get().setBank(club.get().getBank() + Double.parseDouble(request.getValue()));
        } else {
            club.get().setBank(club.get().getBank() - Double.parseDouble(request.getValue()));
        }

        CashBook response;

        try {
            response = repository.save(cashBook);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorDTO("500", "Internal Server Error", "Internal Server Error"));
        }

        return ResponseEntity.ok().body(response);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCashBook(@PathVariable("id") Long id, @RequestBody CashBookDTO request) {
        return ResponseEntity.ok().body(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCashBook(@PathVariable("id") Long id) {

        Optional<CashBook> optionalCashBook = repository.findById(id);

        if (optionalCashBook.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDTO("404", "Payment not found", "Payment does not exist or has already been deleted"));
        }

        var cashBook = optionalCashBook.get();

        if (cashBook.getType() == BookTypeEnum.INPUT) {
            cashBook.getClub().setBank(cashBook.getClub().getBank() - cashBook.getValue());
        } else {
            cashBook.getClub().setBank(cashBook.getClub().getBank() + cashBook.getValue());
        }

        repository.delete(cashBook);

        return new ResponseEntity<>(HttpStatus.OK);

    }

}