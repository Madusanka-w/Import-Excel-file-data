package com.example.demo.module;

import jakarta.persistence.*;
import jdk.jfr.DataAmount;
import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.List;

@Data
@Entity
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String candidateID;

    private String refNo;

    private String dateReg;

    private String firstName;

    private String lastName;

    private String dateOfBirth;

    private String nic;

    private String nationality;

    private String contactNumber;

    private String email;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Disciplines> disciplines;

}
