package com.mitocode.security;

import com.mitocode.model.User;
import com.mitocode.repo.IUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
//Clase s4
@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final IUserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        User user = repo.findOneByUsername(userName);

        if (user==null){
            throw new UsernameNotFoundException("User not found: "+userName);
        }

        List<GrantedAuthority> roles = new ArrayList<>();
        user.getRoles().forEach(role->roles.add(new SimpleGrantedAuthority(role.getName())));

        return new org.springframework.security.core.userdetails.User(user.getUsername()
                , user.getPassword()
                , roles);
    }
}
