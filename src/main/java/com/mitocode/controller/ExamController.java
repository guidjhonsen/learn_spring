package com.mitocode.controller;

import com.mitocode.dto.ExamDTO;
import com.mitocode.model.Exam;
import com.mitocode.service.IExamService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exams")
public class ExamController {

    //@Autowired
    private final IExamService service;
    @Qualifier("defaultMapper")
    private final ModelMapper modelMapper;
    /*public ExamController(IExamService service) {
        this.service = service;
    }*/
    @GetMapping
    public ResponseEntity<List<ExamDTO>> findAll() throws Exception{

        //ModelMapper modelMapper =new ModelMapper();
        List<ExamDTO> list= service.findAll().stream().map( this:: convertToDTO).toList();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamDTO> findById(@PathVariable Integer id) throws Exception{
        ExamDTO obj= convertToDTO(service.findById(id));//modelMapper.map(service.findById(id), ExamDTO.class);
        return ResponseEntity.ok(obj);
    }

    @PostMapping
    public ResponseEntity<ExamDTO> save(@RequestBody ExamDTO dto) throws Exception{
        Exam obj= service.save(convertToEntity(dto));//modelMapper.map(dto, Exam.class));
        URI location= ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdExam()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamDTO> update(@RequestBody ExamDTO dto, @PathVariable Integer id) throws Exception{
        Exam obj =service.update(convertToEntity(dto), id);//modelMapper.map(dto, Exam.class), id);
        return ResponseEntity.ok(convertToDTO(obj));//modelMapper.map(obj, ExamDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) throws Exception{
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<ExamDTO> findByIdHateoas(@PathVariable Integer id) throws Exception{
        Exam obj = service.findById(id);
        EntityModel<ExamDTO> resource=EntityModel.of(convertToDTO(obj));
        WebMvcLinkBuilder link1 = linkTo(methodOn(ExamController.class).findById(obj.getIdExam()));
        WebMvcLinkBuilder link2 = linkTo(methodOn(ExamController.class).findAll());
        resource.add(link1.withRel("exam-self-info"));
        resource.add(link2.withRel("all-exams"));

        return resource;
    }

    private Exam convertToEntity(ExamDTO dto){
        return modelMapper.map(dto, Exam.class);
    }

    private ExamDTO convertToDTO(Exam entity){
        return modelMapper.map(entity, ExamDTO.class);
    }

    /*@GetMapping
    public Exam getExam(){
        //service=new ExamService();
        return service.validExam(1);
    }*/
}
