package com.CSIT321.DeliverYey.Controller;

import com.CSIT321.DeliverYey.Entity.StudentEntity;
import com.CSIT321.DeliverYey.Response.AuthResponse;
import org.springframework.security.core.Authentication;
import com.CSIT321.DeliverYey.Service.StaffService;
import com.CSIT321.DeliverYey.Service.StudentService;
import com.CSIT321.DeliverYey.Config.JwtProvider;
import com.CSIT321.DeliverYey.DTOs.LoginUserDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private StaffService staffService;

    @Autowired
    PasswordEncoder passwordEncoder;


    @PostMapping(path = "/signup",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@Valid @RequestBody StudentEntity input) {
        return studentService.signup(input);
    }

    @PostMapping(path = "/signin",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loginStudent(@Valid @RequestBody LoginUserDTO input) {
        return login(input);
    }

    private Authentication authenticate(String idNumber, String password) {

        System.out.println(idNumber+"---++----"+password);

        UserDetails userDetails = studentService.loadUserByUsername(idNumber);

        System.out.println("Sign in user details"+ userDetails);

        if(userDetails == null) {
            System.out.println("Sign in details - null" + userDetails);

            throw new BadCredentialsException("Invalid username and password");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())) {
            System.out.println("Sign in userDetails - password mismatch"+userDetails);

            throw new BadCredentialsException("Invalid password");

        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

    }

    public ResponseEntity<?> login(LoginUserDTO userDto) {
        String idNumber = userDto.getIdNumber();
        String password = userDto.getPassword();

        System.out.println(idNumber+"-------"+password);


        Authentication authentication = authenticate(idNumber,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = JwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();

        authResponse.setMessage("Login success");
        authResponse.setJwt(token);
        authResponse.setStatus(true);
        return new ResponseEntity<>(authResponse,HttpStatus.OK);
    }
}