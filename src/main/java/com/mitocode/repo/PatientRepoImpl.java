package com.mitocode.repo;

import com.mitocode.model.Patient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


@Repository
//@Service
//@Component
//@Controller
public class PatientRepo {
    public Patient getPatientById(int id){
        return new Patient(id, "test", "test");
    }
}
