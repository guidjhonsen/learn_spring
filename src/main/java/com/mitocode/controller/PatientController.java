package com.mitocode.controller;

import com.mitocode.dto.PatientDTO;
import com.mitocode.model.Patient;
import com.mitocode.service.IPatientService;
import com.mitocode.util.MapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.metamodel.mapping.EntityValuedModelPart;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;



import javax.swing.text.html.parser.Entity;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:4200")
public class PatientController {

    //@Autowired
    private final IPatientService service;

    //@Qualifier("defaultMapper")
    //private final ModelMapper modelMapper;
    private final MapperUtil mapperUtil;

    /*public PatientController(IPatientService service) {
        this.service = service;
    }*/
    @GetMapping
    public ResponseEntity<List<PatientDTO>> findAll() throws Exception{

        //ModelMapper modelMapper =new ModelMapper();
        //List<PatientDTO> list= service.findAll().stream().map( this:: convertToDTO).toList();
        List<PatientDTO> list=mapperUtil.mapList(service.findAll(), PatientDTO.class);

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> findById(@PathVariable Integer id) throws Exception{
        //PatientDTO obj= convertToDTO(service.findById(id));//modelMapper.map(service.findById(id), PatientDTO.class);

        PatientDTO obj= mapperUtil.map(service.findById(id), PatientDTO.class);

        return ResponseEntity.ok(obj);
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody PatientDTO dto) throws Exception{
        //Patient obj= service.save(convertToEntity(dto));//modelMapper.map(dto, Patient.class));

        Patient obj= service.save(mapperUtil.map(dto, Patient.class));
        URI location= ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdPatient()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> update(@Valid @RequestBody PatientDTO dto, @PathVariable Integer id) throws Exception{
        //dto.setIdPatient(id);

        //Patient obj =service.update(convertToEntity(dto), id);//modelMapper.map(dto, Patient.class), id);
        Patient obj =service.update(mapperUtil.map (dto, Patient.class), id);
        //return ResponseEntity.ok(convertToDTO(obj));//modelMapper.map(obj, PatientDTO.class));
        return ResponseEntity.ok(mapperUtil.map (obj, PatientDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) throws Exception{
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<PatientDTO> findByIdHateoas(@PathVariable Integer id) throws Exception{
        Patient obj = service.findById(id);
        //EntityModel<PatientDTO> resource=EntityModel.of(convertToDTO(obj));
        EntityModel<PatientDTO> resource=EntityModel.of(mapperUtil.map(obj, PatientDTO.class));
        WebMvcLinkBuilder link1 = linkTo(methodOn(PatientController.class).findById(obj.getIdPatient()));
        WebMvcLinkBuilder link2 = linkTo(methodOn(PatientController.class).findAll());
        resource.add(link1.withRel("patient-self-info"));
        resource.add(link2.withRel("all-patients"));

        return resource;
    }

    /*private Patient convertToEntity(PatientDTO dto){
        return modelMapper.map(dto, Patient.class);
    }

    private PatientDTO convertToDTO(Patient entity){
        return modelMapper.map(entity, PatientDTO.class);
    }

    /*@GetMapping
    public Patient getPatient(){
        //service=new PatientService();
        return service.validPatient(1);
    }*/
}
