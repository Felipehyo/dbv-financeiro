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
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//@Table(name = "RESPONSIBLE")
//public class Responsible {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private UUID id;
//
//    @Column(name = "Name", length = 130, nullable = false)
//    private String nome;
//
//    @Column(name = "Contact", length = 12, nullable = false)
//    private String contact;
//
//    @Column(name = "Address", length = 150, nullable = false)
//    private String address;
//
//}