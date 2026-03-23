package com.mitocode.service.impl;

import com.mitocode.dto.ConsultProcDTO;
import com.mitocode.model.Consult;
import com.mitocode.model.Exam;
import com.mitocode.repo.IConsultExamRepo;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.repo.IConsultRepo;
import com.mitocode.service.IConsultService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public List<Consult> search(String dni, String fullname) {
        return consultRepo.search(dni, fullname);
    }

    @Override
    public List<Consult> searchByDates(LocalDateTime date1, LocalDateTime date2) {
        final int OFFSET_DAYS=1;
        return consultRepo.searchByDate(date1, date2.plusDays(OFFSET_DAYS));
    }

    @Override
    public List<ConsultProcDTO> callProcedureOrFunctionManual(){

        List<ConsultProcDTO> list= new ArrayList<>();

        consultRepo.callProcedureOrFunctionManual().forEach(
                el ->{
                    ConsultProcDTO dto = new ConsultProcDTO();
                    dto.setQuantity(el.getQuantity());
                    dto.setConsultDate(el.getConsultDate());

                    list.add(dto);
                }
        );

        return list;
    }

    public byte[] generateReport() throws Exception{
        byte[] data=null;
        Map<String, Object> params = new HashMap<>();

        params.put("txt_title", "MEDIAPP CONSULT REPORT");

        File file= new ClassPathResource("/reports/consultas.jasper").getFile();

        JasperPrint print = JasperFillManager.fillReport(file.getPath(), params, new JRBeanCollectionDataSource(callProcedureOrFunctionManual()));

        return JasperExportManager.exportReportToPdf(print);
    }
}
