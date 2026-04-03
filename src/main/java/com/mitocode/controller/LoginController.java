package com.mitocode.controller;
import com.mitocode.security.JwtRequest;
import com.mitocode.security.JwtResponse;
import com.mitocode.security.JwtTokenUtil;
import com.mitocode.security.JwtUserDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService jwtUserDetailsService;

    @PostMapping("/login")
    //public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest) throws Exception {
    public ResponseEntity<Boolean> login(@RequestBody JwtRequest jwtRequest, HttpServletResponse response) throws Exception {
        try{
            authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());

            final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
            final String accessToken = jwtTokenUtil.generateToken(userDetails);

            ResponseCookie cookie = ResponseCookie.from("jwt", accessToken)
                    .httpOnly(true)
                    .secure(false) //true para produccion en https
                    .path("/")
                    .sameSite("Lax") //None para produccion con https secure true
                    .maxAge(Duration.ofHours(5))
                    .build();

            response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            return ResponseEntity.ok(true);
            //return ResponseEntity.ok(new JwtResponse(accessToken));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    private void authenticate(String username, String password) throws Exception{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @GetMapping("/auth/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(false) //true para produccion en https
                .path("/")
                .sameSite("Lax") //None para produccion con https secure true
                .maxAge(0)
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok().build();
    }
}
