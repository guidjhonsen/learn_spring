package com.mitocode.service.impl;

import com.mitocode.model.Consult;
import com.mitocode.model.Exam;
import com.mitocode.repo.IConsultExamRepo;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.repo.IConsultRepo;
import com.mitocode.service.IConsultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultServiceImpl extends CRUDImpl<Consult, Integer> implements IConsultService {

    //@Autowired
    private final IConsultRepo consultRepo;
    private final IConsultExamRepo consultExamRepo;

    @Override
    protected IGenericRepo<Consult, Integer> getRepo() {
        return consultRepo;
    }

    @Transactional
    @Override
    public Consult saveTransactional(Consult consult, List<Exam> exams) {
        /*consultRepo.save(consult); //GUARDA EL MAESTRO DETALLE
        exams.forEach(ex -> consultExamRepo.saveExam(consult.getIdConsult(), ex.getIdExam()));

        return consult;*/
            // Asignar el padre a cada detalle
            consult.getDetails().forEach(d -> d.setConsult(consult));

            // Guardar el maestro-detalle
            Consult savedConsult = consultRepo.save(consult);

            // Guardar los exámenes asociados
            exams.forEach(ex -> consultExamRepo.saveExam(savedConsult.getIdConsult(), ex.getIdExam()));

            return savedConsult;
    }
}
