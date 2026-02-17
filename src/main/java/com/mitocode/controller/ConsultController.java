package com.mitocode.controller;

import com.mitocode.dto.ConsultDTO;
import com.mitocode.dto.ConsultListExamDTO;
import com.mitocode.model.Consult;
import com.mitocode.model.Exam;
import com.mitocode.service.IConsultService;
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
@RequestMapping("/consults")
public class ConsultController {

    //@Autowired
    private final IConsultService service;
    @Qualifier("defaultMapper")
    private final ModelMapper modelMapper;
    /*public ConsultController(IConsultService service) {
        this.service = service;
    }*/
    @GetMapping
    public ResponseEntity<List<ConsultDTO>> findAll() throws Exception{

        //ModelMapper modelMapper =new ModelMapper();
        List<ConsultDTO> list= service.findAll().stream().map( this:: convertToDTO).toList();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultDTO> findById(@PathVariable Integer id) throws Exception{
        ConsultDTO obj= convertToDTO(service.findById(id));//modelMapper.map(service.findById(id), ConsultDTO.class);
        return ResponseEntity.ok(obj);
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody ConsultListExamDTO dto) throws Exception{

        Consult obj1= convertToEntity(dto.getConsult());
        List<Exam> list = dto.getLstExam().stream().map(ex->modelMapper.map(ex, Exam.class)).toList();

        Consult obj = service.saveTransactional(obj1, list);

        URI location= ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdConsult()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultDTO> update(@RequestBody ConsultDTO dto, @PathVariable Integer id) throws Exception{
        Consult obj =service.update(convertToEntity(dto), id);//modelMapper.map(dto, Consult.class), id);
        return ResponseEntity.ok(convertToDTO(obj));//modelMapper.map(obj, ConsultDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) throws Exception{
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<ConsultDTO> findByIdHateoas(@PathVariable Integer id) throws Exception{
        Consult obj = service.findById(id);
        EntityModel<ConsultDTO> resource=EntityModel.of(convertToDTO(obj));
        WebMvcLinkBuilder link1 = linkTo(methodOn(ConsultController.class).findById(obj.getIdConsult()));
        WebMvcLinkBuilder link2 = linkTo(methodOn(ConsultController.class).findAll());
        resource.add(link1.withRel("consult-self-info"));
        resource.add(link2.withRel("all-consults"));

        return resource;
    }

    private Consult convertToEntity(ConsultDTO dto){
        return modelMapper.map(dto, Consult.class);
    }

    private ConsultDTO convertToDTO(Consult entity){
        return modelMapper.map(entity, ConsultDTO.class);
    }

    /*@GetMapping
    public Consult getConsult(){
        //service=new ConsultService();
        return service.validConsult(1);
    }*/
}
