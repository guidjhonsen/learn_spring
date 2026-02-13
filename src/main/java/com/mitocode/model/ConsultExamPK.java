package com.mitocode.model;


import jakarta.persistence.Embeddable;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;

@Embeddable
@EqualsAndHashCode
public class ConsultExamPK {

    @ManyToOne
    @JoinColumn(name = "id_consult", foreignKey = @ForeignKey(name = "FK_CONSULT_EXAM_C"))
    private Consult consult;

    @ManyToOne
    @JoinColumn(name = "id_exam", foreignKey = @ForeignKey(name = "FK_CONSULT_EXAM_E"))
    private  Exam exam;

}
