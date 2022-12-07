//package br.com.dbv.financeiro.model;
//
//import br.com.dbv.financeiro.enums.UserTypeEnum;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.util.UUID;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
//abstract class User {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private UUID id;
//
//    @Column(name = "Name", length = 130, nullable = false)
//    private String nome;
//
//    @Column(name = "UserType", length = 12, nullable = false)
//    private UserTypeEnum userType;
//
//    @ManyToOne
//    private Club club;
//
//}