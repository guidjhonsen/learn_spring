package com.mitocode.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(ConsultExamPK.class)
public class ConsultExam {

    @Id
 /*   @JoinColumn(name = "id_consult", nullable = false)
    @ManyToOne(optional = false)
   */ private Consult consult;

    @Id
  /*  @ManyToOne(optional = false)
    @JoinColumn(name = "id_exam", nullable = false)
    */private Exam exam;


}
