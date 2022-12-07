//package br.com.dbv.financeiro.model;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.util.Date;
//import java.util.UUID;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//@Table(name = "EVENT")
//public class Event {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private UUID id;
//
//    @Column(name = "Name", length = 80, nullable = false)
//    private String name;
//
//    @Column(name = "Description", length = 250)
//    private String description;
//
//    @Column(name = "Date", nullable = false)
//    private Date date;
//
//    @Column(name = "Value", nullable = false)
//    private Double value;
//
//}