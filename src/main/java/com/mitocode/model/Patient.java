package com.mitocode.model;


import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
/*@Getter
@Setter
@ToString
@EqualsAndHashCode*/
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Patient {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idPatient;

    @Column(nullable = false, length = 70)
    private String firstName;
    @Column(nullable = false, length = 70)
    private String lastName;

    @Column(nullable = false, length = 10)
    private String dni;
    @Column(length = 150)
    private String address;

    @Column(nullable = false, length = 9)
    private String phone;

    @Column(nullable = false, length = 55)
    private String email;

}
