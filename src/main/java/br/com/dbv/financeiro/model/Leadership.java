//package br.com.dbv.financeiro.model;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.util.Date;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@EqualsAndHashCode(callSuper = true)
//@Entity
//@Table(name = "LEADERSHIP")
//public class Leadership extends User {
//
//    @Column(name = "Email", length = 120, nullable = false)
//    private String email;
//
//    @Column(name = "Password", length = 12, nullable = false)
//    private String pass;
//
//    @Temporal(TemporalType.DATE)
//    @Column(name = "Birthdate", nullable = false)
//    private Date birthDate;
//
//}