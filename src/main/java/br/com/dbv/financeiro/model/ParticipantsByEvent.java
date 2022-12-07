//package br.com.dbv.financeiro.model;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.util.UUID;
//
//@Data
//@Entity
//@Table(name = "PARTICIPANTS-BY-EVENT")
//@NoArgsConstructor
//@AllArgsConstructor
//public class ParticipantsByEvent {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private UUID id;
//
//    @ManyToOne
//    @JoinColumn(name = "event_id")
//    private Event event;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//
//}