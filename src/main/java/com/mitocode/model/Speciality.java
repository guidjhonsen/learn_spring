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
public class Speciality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idSpeciality;

    @Column(nullable = false,length = 150)
    private String name;

    @Column(nullable = false, length = 150)
    private String description;
}
