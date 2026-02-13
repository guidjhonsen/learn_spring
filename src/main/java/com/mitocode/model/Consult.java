package com.mitocode.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
/*@Getter
@Setter
@ToString
@EqualsAndHashCode*/
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Consult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idConsult;

    @ManyToOne
    @JoinColumn(name="id_patient", nullable = false, foreignKey = @ForeignKey(name = "fk_consult_patient"))
    private Patient patient;


    @ManyToOne
    @JoinColumn(name="id_medic", nullable = false, foreignKey = @ForeignKey(name = "fk_consult_medic"))
    private Medic medic;

    @ManyToOne
    @JoinColumn(name="id_user", nullable = false, foreignKey = @ForeignKey(name = "fk_consult_user"))
    private User user;

    @Column(nullable = false, length = 3)
    private String numConsult;

    @Column(nullable = false)
    private LocalDateTime consultaDate;

    @OneToMany(mappedBy = "consult", cascade=CascadeType.ALL)
    private List<ConsultDetail> details;
}
