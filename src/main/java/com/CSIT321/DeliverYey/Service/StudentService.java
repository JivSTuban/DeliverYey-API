package com.CSIT321.DeliverYey.Service;

import com.CSIT321.DeliverYey.Repository.StudentRepository;
import com.CSIT321.DeliverYey.Entity.StudentEntity;
import com.CSIT321.DeliverYey.Entity.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class StudentService implements UserDetailsService {

    @Autowired
    private StudentRepository studentRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public StudentService(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public UserDetails loadUserByUsername(String idNumber) throws UsernameNotFoundException {
        StudentEntity student = studentRepository.findByIdNumberAndIsDeletedFalse(idNumber);
        if (student != null){
            return User.builder()
                    .username(student.getEmail())
                    .password(student.getPassword())
                    .roles(String.valueOf(student.getUserType()))
                    .build();

        }else{
            throw new UsernameNotFoundException(idNumber);
        }
    }

    public ResponseEntity<?> signup(StudentEntity input) {
        try{
            if (studentRepository.count() > 0) {
                if (studentRepository.findByIdNumberAndIsDeletedTrue(input.getIdNumber()) != null) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("{\"message\": \"This account was already deleted. Would you like to recover this account?\"}");
                }
            }
        }catch(NullPointerException e){
            System.out.println("There are no deleted accounts yet.");
        }

        if (studentRepository.findByIdNumberAndIsDeletedFalse(input.getIdNumber()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("{\"message\": \"ID number is already in use\"}");
        }

        if (!isValidPassword(input.getPassword())) {
            throw new IllegalArgumentException("Invalid password format. It must be at least 8 characters with 1 uppercase letter.");
        }

        var student = new StudentEntity();
        student.setIdNumber(input.getIdNumber());
        student.setEmail(input.getEmail());
        student.setPassword(passwordEncoder.encode(input.getPassword()));
        student.setUserType(UserType.STUDENT);
        student.setDeleted(false);

        studentRepository.save(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(student);
    }

    public List<StudentEntity> getAllStudent() {
        return studentRepository.findAll();
    }



    public StudentEntity updateStudent(int sid, StudentEntity newStudentDetails){
        StudentEntity student = new StudentEntity();

        //1. check if the student id exists
        if (studentRepository.findById(sid).isPresent()){
            //2. search the ID  number of the student that will be updated
            student = studentRepository.findBySidAndIsDeletedFalse(sid);
            //3. update the record
            student.setIdNumber(newStudentDetails.getIdNumber());
            student.setEmail(newStudentDetails.getEmail());

            student.setPassword(passwordEncoder.encode(newStudentDetails.getPassword()));
        }else{
            throw new NoSuchElementException("Student" + sid + "does not exist!");
        }

        return studentRepository.save(student);

    }

    public String deleteStudent(int sid){
        String msg = "";

        if (studentRepository.findById(sid).isPresent()){
            studentRepository.deleteById(sid);
            msg = "Student " + sid +"is successfully deleted!";
        } else
            msg = "Student " + sid + " does not exist.";
        return msg;
    }

    private boolean isValidPassword(String password) {
        // Password should be at least 8 characters with 1 uppercase letter
        return password.matches("^(?=.*[A-Z]).{8,}$");
    }
}
