package com.CSIT321.DeliverYey.Service;

import com.CSIT321.DeliverYey.DTO.ReqRes;
import com.CSIT321.DeliverYey.Entity.StudentEntity;
import com.CSIT321.DeliverYey.Entity.UserType;
import com.CSIT321.DeliverYey.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class AuthService {

    @Autowired
    private StaffService staffService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private StudentService studentService;

    public ReqRes register(ReqRes registrationRequest){

        try{
            if (studentRepository.count() > 0) {
                if (studentRepository.findByIdNumberAndIsDeletedTrue(registrationRequest.getIdNumber()) != null) {
                    registrationRequest.setMessage("{\"message\": \"This account was already deleted. Would you like to recover this account?\"}");
                    return registrationRequest;
                }
            }
        }catch(NullPointerException e){
            System.out.println("There are no deleted accounts yet.");
        }

        if (studentRepository.findByIdNumberAndIsDeletedFalse(registrationRequest.getIdNumber()) != null) {
            registrationRequest.setMessage("{\"message\": \"ID number is already in use\"}");
            return registrationRequest;
        }

        if (!isValidPassword(registrationRequest.getPassword())) {
            throw new IllegalArgumentException("Invalid password format. It must be at least 8 characters with 1 uppercase letter.");
        }

        ReqRes resp = new ReqRes();

        try {
            var student = new StudentEntity();
            student.setIdNumber(registrationRequest.getIdNumber());
            student.setEmail(registrationRequest.getEmail());
            student.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            student.setUserType(UserType.STUDENT);
            student.setDeleted(false);

            StudentEntity newStudent = studentRepository.save(student);

            if (student.getSid()>0) {
                resp.setStudent((newStudent));
                resp.setMessage("User Saved Successfully");
                resp.setStatusCode(200);
            }

        }catch (Exception e){
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
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

    public ReqRes login(ReqRes loginRequest) {
        ReqRes response = new ReqRes();
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getIdNumber(),
                            loginRequest.getPassword()));
            var user = studentRepository.findByIdNumberAndIsDeletedFalse(loginRequest.getIdNumber());
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRole(user.getUserType().name());
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hrs");
            response.setMessage("Successfully Logged In");

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    private boolean isValidPassword(String password) {
        // Password should be at least 8 characters with 1 uppercase letter
        return password.matches("^(?=.*[A-Z]).{8,}$");
    }
}
