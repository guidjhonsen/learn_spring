package com.mitocode.controller;

import com.mitocode.dto.SpecialityDTO;
import com.mitocode.model.Speciality;
import com.mitocode.service.ISpecialityService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
@RequestMapping("/specialities")
public class SpecialityController {

    //@Autowired
    private final ISpecialityService service;
    private final ModelMapper modelMapper;
    /*public SpecialityController(ISpecialityService service) {
        this.service = service;
    }*/
    @GetMapping
    public ResponseEntity<List<SpecialityDTO>> findAll() throws Exception{

        //ModelMapper modelMapper =new ModelMapper();
        List<SpecialityDTO> list= service.findAll().stream().map( this:: convertToDTO).toList();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecialityDTO> findById(@PathVariable Integer id) throws Exception{
        SpecialityDTO obj= convertToDTO(service.findById(id));//modelMapper.map(service.findById(id), SpecialityDTO.class);
        return ResponseEntity.ok(obj);
    }

    @PostMapping
    public ResponseEntity<SpecialityDTO> save(@RequestBody SpecialityDTO dto) throws Exception{
        Speciality obj= service.save(convertToEntity(dto));//modelMapper.map(dto, Speciality.class));
        URI location= ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdSpeciality()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpecialityDTO> update(@RequestBody SpecialityDTO dto, @PathVariable Integer id) throws Exception{
        Speciality obj =service.update(convertToEntity(dto), id);//modelMapper.map(dto, Speciality.class), id);
        return ResponseEntity.ok(convertToDTO(obj));//modelMapper.map(obj, SpecialityDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) throws Exception{
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<SpecialityDTO> findByIdHateoas(@PathVariable Integer id) throws Exception{
        Speciality obj = service.findById(id);
        EntityModel<SpecialityDTO> resource=EntityModel.of(convertToDTO(obj));
        WebMvcLinkBuilder link1 = linkTo(methodOn(SpecialityController.class).findById(obj.getIdSpeciality()));
        WebMvcLinkBuilder link2 = linkTo(methodOn(SpecialityController.class).findAll());
        resource.add(link1.withRel("speciality-self-info"));
        resource.add(link2.withRel("all-specialitys"));

        return resource;
    }

    private Speciality convertToEntity(SpecialityDTO dto){
        return modelMapper.map(dto, Speciality.class);
    }

    private SpecialityDTO convertToDTO(Speciality entity){
        return modelMapper.map(entity, SpecialityDTO.class);
    }

    /*@GetMapping
    public Speciality getSpeciality(){
        //service=new SpecialityService();
        return service.validSpeciality(1);
    }*/
}
