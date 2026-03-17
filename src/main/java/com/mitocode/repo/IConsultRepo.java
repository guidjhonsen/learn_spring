package com.mitocode.repo;

import com.mitocode.model.Consult;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IConsultRepo extends IGenericRepo<Consult, Integer>{

    //jpql


    @Query("FROM Consult c WHERE c.patient.dni=:dni OR LOWER(c.patient.firstName) LIKE %:fullname% OR LOWER(c.patient.lastName) LIKE %:fullname%")
    List<Consult> search(@Param("dni") String dni,@Param("fullname") String fullname);
}
