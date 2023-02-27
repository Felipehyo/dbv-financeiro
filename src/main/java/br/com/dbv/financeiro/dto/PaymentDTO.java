package br.com.dbv.financeiro.dto;

import br.com.dbv.financeiro.enums.FormOfPaymentEnum;
import br.com.dbv.financeiro.model.Club;
import br.com.dbv.financeiro.model.Event;
import br.com.dbv.financeiro.model.Payment;
import br.com.dbv.financeiro.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {

    private Double value;
    private FormOfPaymentEnum formOfPayment;
    private LocalDate date;
    private Long clubId;
    private Long eventId;
    private UUID userId;

    public Payment convert(Club club, Event event, User user) {

        Payment payment = new Payment();
        payment.setValue(value);
        payment.setFormOfPayment(formOfPayment);
        payment.setDate(date);
        payment.setClub(club);
        payment.setEvent(event);
        payment.setUser(user);

        return payment;

    }

}