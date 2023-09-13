package br.com.dbv.financeiro.controller;

import br.com.dbv.financeiro.dto.CashBookDTO;
import br.com.dbv.financeiro.dto.ErrorDTO;
import br.com.dbv.financeiro.dto.reponse.CashBookResponseDTO;
import br.com.dbv.financeiro.enums.BookTypeEnum;
import br.com.dbv.financeiro.model.CashBook;
import br.com.dbv.financeiro.repository.CashBookRepository;
import br.com.dbv.financeiro.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/cash-book")
public class CashBookController {

    @Autowired
    private CashBookRepository repository;

    @Autowired
    private ClubRepository clubRepository;

    @GetMapping()
    public ResponseEntity<?> getAllCashBook() {

        return ResponseEntity.ok().body(repository.findAll());

    }

    @GetMapping("/{clubId}")
    public ResponseEntity<?> getCashBookByClub(@PathVariable("clubId") Long id) {

        var cashBookList = repository.findByClubId(id);

        var responseList = new ArrayList<>();

        for (var cashBook : cashBookList) {
            responseList.add(new CashBookResponseDTO(cashBook));
        }

        return ResponseEntity.ok().body(responseList);

    }

    @PostMapping
    public ResponseEntity<?> createCashBook(@RequestBody CashBookDTO request) {

        var club = clubRepository.findById(request.getClubId());

        if (!club.isPresent()) {
            return ResponseEntity.badRequest().body(new ErrorDTO("400", "Club not found", "Club not found in database"));
        }

        CashBook cashBook = request.convert(club.get());

        if (cashBook.getType() == BookTypeEnum.INPUT) {
            club.get().setBank(club.get().getBank() + request.getValue());
        } else {
            club.get().setBank(club.get().getBank() - request.getValue());
        }

        CashBook response;

        try {
            response = repository.save(cashBook);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorDTO("500", "Internal Server Error", "Internal Server Error"));
        }

        return ResponseEntity.ok().body(response);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCashBook(@PathVariable("id") Long id) {
        try {
            CashBook cashBook = repository.findById(id).get();

            if (cashBook.getType() == BookTypeEnum.INPUT) {
                cashBook.getClub().setBank(cashBook.getClub().getBank() - cashBook.getValue());
            } else {
                cashBook.getClub().setBank(cashBook.getClub().getBank() + cashBook.getValue());
            }

            repository.delete(cashBook);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDTO("404", "Payment not found", "Payment does not exist or has already been deleted"));
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

}