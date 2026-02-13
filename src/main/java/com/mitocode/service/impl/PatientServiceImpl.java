package com.mitocode.service;

import com.mitocode.model.Patient;
import com.mitocode.repo.IPatientRepo;
import com.mitocode.repo.PatientRepoImpl;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements IPatientService{

    //@Autowired
    private final IPatientRepo repo;

    @Override
    public Patient save(Patient patient) throws Exception {
        return repo.save(patient);
    }

    @Override
    public Patient update(Patient patient, Integer id) throws Exception {
        patient.setIdPatient(id);
        return repo.save(patient);
    }

    @Override
    public List<Patient> findAll() throws Exception {
        return repo.findAll();
    }

    @Override
    public Patient findById(Integer id) throws Exception {
        return repo.findById(id).orElse(new Patient());
    }

    @Override
    public void delete(Integer id) throws Exception {
        repo.deleteById(id);
    }
    //private String text;

    /*public PatientServiceImpl(IPatientRepo repo) {
        this.repo = repo;
    }*/

    /*@Override
    public Patient validPatient(int id){

        //repo=new IPatientRepo();

        if(id>0) {
            return repo.getPatientById(id);
        }else{
            //return new Patient(0, "default", "default");
            return new Patient();
        }
    }*/
}
