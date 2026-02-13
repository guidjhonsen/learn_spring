package com.mitocode.repo;

import com.mitocode.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPatientRepo extends IGenericRepo<Patient, Integer> {

        //Patient getPatientById(int id);
}
