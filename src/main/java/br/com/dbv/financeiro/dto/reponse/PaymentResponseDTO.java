package br.com.dbv.financeiro.dto.reponse;

import br.com.dbv.financeiro.dto.event.EventDTO;
import br.com.dbv.financeiro.dto.UserDTO;
import br.com.dbv.financeiro.model.Payment;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class PaymentResponseDTO {

    private UUID id;
    private UserDTO pathfinder;
    private EventDTO event;
    private Double value;
    private String formOfPayment;
    private LocalDate date;

    public PaymentResponseDTO(Payment payment) {
        this.id = payment.getId();

        var pathfinder = payment.getPathfinder();
        this.pathfinder = new UserDTO();
        this.pathfinder.setId(pathfinder.getId());
        this.pathfinder.setName(pathfinder.getName());
        this.pathfinder.setUserType(pathfinder.getUserType());
        this.pathfinder.setBirthDate(pathfinder.getBirthDate());

        var event = payment.getEvent();
        if (event != null) {
            this.event = new EventDTO();
            this.event.setId(event.getId());
            this.event.setName(event.getEvent());
            this.event.setValue(event.getValue());
            this.event.setDate(event.getDate());
        }

        this.value = payment.getValue();
        this.formOfPayment = payment.getFormOfPayment().description;
        this.date = payment.getDate();

    }

}
