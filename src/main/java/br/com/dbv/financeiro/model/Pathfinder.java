//package br.com.dbv.financeiro.model;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.util.Date;
//import java.util.List;
//import java.util.UUID;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@EqualsAndHashCode(callSuper = true)
//@Entity
//@Table(name = "PATHFINDER")
//public class Pathfinder extends User {
//
//    @Column(name = "Sex", length = 6, nullable = false)
//    private String sex;
//
//    @Temporal(TemporalType.DATE)
//    @Column(name = "Birthdate", nullable = false)
//    private Date birthDate;
//
////    @Column(name = "Responsible")
////    @OneToMany(cascade = CascadeType.ALL, mappedBy = "id")
////    private List<Responsible> responsible;
//
//}