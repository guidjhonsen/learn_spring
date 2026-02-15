package com.mitocode.controller;

import com.mitocode.dto.MedicDTO;
import com.mitocode.model.Medic;
import com.mitocode.service.IMedicService;
import com.mitocode.util.MapperUtil;
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
@RequestMapping("/medics")
public class MedicController {

    //@Autowired
    private final IMedicService service;
    //@Qualifier("medicMapper")
    //private final ModelMapper modelMapper;
    private final MapperUtil mapperUtil;

    /*public MedicController(IMedicService service) {
        this.service = service;
    }*/
    @GetMapping
    public ResponseEntity<List<MedicDTO>> findAll() throws Exception{

        //ModelMapper modelMapper =new ModelMapper();
        //List<MedicDTO> list= service.findAll().stream().map( this:: convertToDTO).toList();
        List<MedicDTO> list= mapperUtil.mapList(service.findAll(), MedicDTO.class, "medicMapper");

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicDTO> findById(@PathVariable Integer id) throws Exception{
        //MedicDTO obj= convertToDTO(service.findById(id));//modelMapper.map(service.findById(id), MedicDTO.class);

        MedicDTO obj= mapperUtil.map(service.findById(id), MedicDTO.class, "medicMapper");

        return ResponseEntity.ok(obj);
    }

    @PostMapping
    public ResponseEntity<MedicDTO> save(@RequestBody MedicDTO dto) throws Exception{
        //Medic obj= service.save(convertToEntity(dto));//modelMapper.map(dto, Medic.class));
        Medic obj= service.save(mapperUtil.map(dto, Medic.class, "medicMapper"));
        URI location= ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdMedico()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicDTO> update(@RequestBody MedicDTO dto, @PathVariable Integer id) throws Exception{
        //Medic obj =service.update(convertToEntity(dto), id);//modelMapper.map(dto, Medic.class), id);

        Medic obj =service.update(mapperUtil.map(dto, Medic.class, "medicMapper"), id);
        //return ResponseEntity.ok(convertToDTO(obj));//modelMapper.map(obj, MedicDTO.class));
        return ResponseEntity.ok(mapperUtil.map(obj, MedicDTO.class, "medicMapper"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) throws Exception{
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<MedicDTO> findByIdHateoas(@PathVariable Integer id) throws Exception{
        Medic obj = service.findById(id);
        //EntityModel<MedicDTO> resource=EntityModel.of(convertToDTO(obj));
        EntityModel<MedicDTO> resource=EntityModel.of(mapperUtil.map(obj, MedicDTO.class, "medicMapper"));
        WebMvcLinkBuilder link1 = linkTo(methodOn(MedicController.class).findById(obj.getIdMedico()));
        WebMvcLinkBuilder link2 = linkTo(methodOn(MedicController.class).findAll());
        resource.add(link1.withRel("medic-self-info"));
        resource.add(link2.withRel("all-medics"));

        return resource;
    }
    
    /*
    private Medic convertToEntity(MedicDTO dto){
        return modelMapper.map(dto, Medic.class);
    }

    private MedicDTO convertToDTO(Medic entity){
        return modelMapper.map(entity, MedicDTO.class);
    }

    /*@GetMapping
    public Medic getMedic(){
        //service=new MedicService();
        return service.validMedic(1);
    }*/
}
