package br.com.dbv.financeiro.service;

import br.com.dbv.financeiro.dto.ErrorDTO;
import br.com.dbv.financeiro.exception.CustomException;
import br.com.dbv.financeiro.model.Payment;
import br.com.dbv.financeiro.repository.PaymentRepository;
import br.com.dbv.financeiro.repository.UserEventBankRepository;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository repository;

    @Autowired
    private UserEventBankRepository userEventBankRepository;

    public ResponseEntity<?> deletePayment(String id) throws CustomException {

        if (StringUtils.isBlank(id)) {
            throw new CustomException(new ErrorDTO("404", "Empty payment", "Payment id is required"));
        }

        UUID paymentId;

        try {
            paymentId = UUID.fromString(id);
        } catch (Exception e) {
            throw new CustomException(new ErrorDTO("404", "Invalid format", "Invalid UUID format"));
        }

        Optional<Payment> response = repository.findById(paymentId);

        if (response.isEmpty()) {
            throw new CustomException(new ErrorDTO("404", "Payment not found", "Payment does not exist or has already been deleted"));
        }

        var payment = response.get();
        var valuePayment = payment.getValue();
        var pathfinder = payment.getPathfinder();
        var club = pathfinder.getClub();

        if (valuePayment > 0) {
            if (pathfinder.getBank() >= valuePayment) {
                var oldUserBank = pathfinder.getBank();
                pathfinder.setBank(oldUserBank - valuePayment);
                valuePayment = valuePayment - oldUserBank;
            } else {
                //Zera banco e atualiza valor de dívida
                if (pathfinder.getBank() > 0) {
                    valuePayment = valuePayment - pathfinder.getBank();
                    pathfinder.setBank(0.0);
                }

                if (payment.getEvent() != null) {

                    var event = payment.getEvent();

                    var optionalUserEventBank = userEventBankRepository.findByEventIdAndPathfinderId(payment.getEvent().getId(), pathfinder.getId());

                    if (optionalUserEventBank.isPresent()) {
                        var userBank = optionalUserEventBank.get();
                        if (userBank.getAmount() > 0) {
                            if (userBank.getAmount() <= valuePayment) {
                                valuePayment = valuePayment - userBank.getAmount();
                                event.setBank(event.getBank() - userBank.getAmount());
                                userBank.setAmount(0.0);
                            } else {
                                event.setBank(event.getBank() - valuePayment);
                                userBank.setAmount(userBank.getAmount() - valuePayment);
                                valuePayment = 0.0;
                            }
                        }
                    }
                }
            }
        }

        // set negative bank
        if (valuePayment > 0) {
            pathfinder.setBank(pathfinder.getBank() - valuePayment);
        }

        //remove value of club bank
        club.setBank(club.getBank() - payment.getValue());

        //delete payment register
        repository.delete(payment);

        return ResponseEntity.noContent().build();

    }

}
