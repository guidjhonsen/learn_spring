package com.mitocode.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {


    private Integer idPatient;

    @NotNull
    @Size(min = 3, max = 70, message = "{firstName.size}")
    //@Pattern(regexp = "^[A-Za-z]+$")
    private String firstName;
    @NotNull
    @Size(min = 3, max = 70, message = "{lastname.size}")
    private String lastName;
    @NotNull
    private String dni;
    @NotNull
    private String address;
    @NotNull
    @Pattern(regexp = "[0-9]+", message = "{phone.regexp")
    private String phone;
    @NotNull
    @Email (message = "{email.valid}")
    private String email;

}
