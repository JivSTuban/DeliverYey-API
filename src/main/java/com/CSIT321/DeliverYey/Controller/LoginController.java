package com.CSIT321.DeliverYey.Controller;

import com.CSIT321.DeliverYey.Service.StaffService;
import com.CSIT321.DeliverYey.Service.StudentService;
import com.CSIT321.DeliverYey.Config.JwtProvider;
import com.CSIT321.DeliverYey.DTOs.LoginUserDTO;
import com.CSIT321.DeliverYey.Entity.StudentEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/login")
public class LoginController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private StaffService staffService;

    @Autowired
    PasswordEncoder passwordEncoder;




    @PostMapping(path = "/student",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loginStudent(@Valid @RequestBody LoginUserDTO input) {


    }

    private Authentication authenticate(String idNumber, String password) {

        System.out.println(idNumber+"---++----"+password);

        UserDetails userDetails = studentService.loadUserByUsername(idNumber);

        System.out.println("Sig in in user details"+ userDetails);

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
        StudentEntity myUser = studentRepository.findByIdNumberAndIsDeletedTrue(userDto.getIdNumber());

        if (myUser != null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"This account is in a state of deletion. Would you like to recover it?\"}");
        }

        myUser = studentRepository.findByIdNumberAndIsDeletedFalse(userDto.getIdNumber());
        if (myUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"Incorrect student ID\"}");
        }
        if (!passwordEncoder.matches(userDto.getPassword(), myUser.getPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"Incorrect Password\"}");
        }
        return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"Login Successful\"}");
    }
}
