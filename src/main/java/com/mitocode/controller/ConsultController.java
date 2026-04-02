package com.mitocode.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.mitocode.dto.ConsultDTO;
import com.mitocode.dto.ConsultListExamDTO;
import com.mitocode.dto.ConsultProcDTO;
import com.mitocode.dto.FilterConsultDTO;
import com.mitocode.model.Consult;
import com.mitocode.model.Exam;
import com.mitocode.model.MediaFile;
import com.mitocode.service.IConsultService;
import com.mitocode.service.IMediaFileService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.cloudinary.json.JSONObject;
import org.jfree.util.ObjectTable;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/consults")
public class ConsultController {

    //@Autowired
    private final IConsultService service;
    @Qualifier("consultMapper")
    private final ModelMapper modelMapper;

    private final IMediaFileService mfService;
    private final Cloudinary cloudinary;
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

    @PostMapping("/search/others")
    public ResponseEntity<List<ConsultDTO>> searchByOthers(@RequestBody FilterConsultDTO dto) {

        List<ConsultDTO> list =service.search(dto.getDni(), dto.getFullname()).stream().map(this::convertToDTO).toList();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/search/dates")
    public ResponseEntity<List<ConsultDTO>> searchByDates(@RequestParam("date1") String date1, @RequestParam("date2") String date2) throws Exception{
        List<ConsultDTO> list = service.searchByDates(LocalDateTime.parse(date1), LocalDateTime.parse(date2)).stream().map(this::convertToDTO).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/callProcedureManual")
    public ResponseEntity<List<ConsultProcDTO>> callProcedureOrFunctionManual(){
        return ResponseEntity.ok(service.callProcedureOrFunctionManual());
    }

    @GetMapping(value = "/generateReport", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)//APPLICATION_PDF_VALUE
    public ResponseEntity<byte[]> generateReport() throws Exception{
        return ResponseEntity.ok(service.generateReport());
    }

    @PostMapping(value="/saveFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveFile(@RequestParam("file") MultipartFile multipartFile) throws Exception{
        MediaFile mf = new MediaFile();
        mf.setFileType(multipartFile.getContentType());
        mf.setFileName(multipartFile.getOriginalFilename());
        mf.setContent(multipartFile.getBytes());
        mfService.save(mf);
       /* File f = convertToFile(multipartFile);
        Map<String, Object> response = cloudinary.uploader().upload(f, ObjectUtils.asMap("resource_type", "auto"));

        JSONObject json = new JSONObject(response);
        String url = json.getString("url");

        System.out.println("URL: "+ url);
*/
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/readFile/{idFile}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> readFile(@PathVariable Integer idFile) throws Exception{
        byte[] data = mfService.findById(idFile).getContent();

        return ResponseEntity.ok(data);
    }

    private File convertToFile(MultipartFile multipartFile) throws Exception{
        File file = new File (multipartFile.getOriginalFilename());
        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(multipartFile.getBytes());
        outputStream.close();
        return file;
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
