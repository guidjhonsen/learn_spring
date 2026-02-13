package com.mitocode.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
/*@Getter
@Setter
@ToString
@EqualsAndHashCode*/
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Medic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idMedico;

    @ManyToOne//FK
    @JoinColumn(name="id_speciality",nullable = false, foreignKey = @ForeignKey(name="fk_medic_speciality"))
    private Speciality speciality;//JPQL

    @Column(nullable = false, length = 70)
    private String firstName;

    @Column(nullable = false, length = 70)
    private String lastName;

    @Column(nullable = false, length = 12)
    private String cmp;


    @Column(length = 250)
    private String photoUrl;

}
