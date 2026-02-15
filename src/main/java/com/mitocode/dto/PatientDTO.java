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
    @Size(min = 3, max = 70, message = "El nombre debe tener entre 3 y 70 caracteres")
    //@Pattern(regexp = "^[A-Za-z]+$")
    private String firstName;
    @NotNull
    @Size(min = 3, max = 70, message = "El apellido debe tener entre 3 y 70 caracteres")
    private String lastName;
    @NotNull
    private String dni;
    @NotNull
    private String address;
    @NotNull
    @Pattern(regexp = "[0-9]+", message = "El teléfono solo debe contener números")
    private String phone;
    @NotNull
    @Email (message = "Formato de correo no valido")
    private String email;

}
