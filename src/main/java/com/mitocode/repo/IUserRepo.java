package com.mitocode.repo;


import com.mitocode.model.User;
import org.springframework.data.jpa.repository.Query;

public interface IUserRepo extends IGenericRepo<User, Integer>{

    /*User findOneByUserName(String username);

    org.springframework.security.core.userdetails.User findOneByUserName(String userName);

    User findOneByUserName(String userName);*/

    User findOneByUsername(String username);
}
