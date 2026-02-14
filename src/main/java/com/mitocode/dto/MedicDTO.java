package com.mitocode.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicDTO {

    private Integer idMedic;
    private Integer idSpeciality;
    private String primaryName;
    private String surname;
    private String cmpMedic;
    private String photo;
}
