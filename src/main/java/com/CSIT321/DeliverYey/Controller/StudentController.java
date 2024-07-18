package com.CSIT321.DeliverYey.Controller;

import com.CSIT321.DeliverYey.Service.StudentService;
import com.CSIT321.DeliverYey.Entity.StudentEntity;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@CrossOrigin(origins = "http://localhost:5173")
public class StudentController {

    @Autowired
    private StudentService studentService;


    @GetMapping
    public List<StudentEntity> getAllStudent() {
        return studentService.getAllStudent();
    }

    @PutMapping("/update")
    public StudentEntity updateStudent(@RequestParam int sid, @RequestBody StudentEntity newStudentDetails) {
        return studentService.updateStudent(sid, newStudentDetails);
    }

    @DeleteMapping("/deleteStudent/{sid}")
    public String deleteStudent(@PathVariable int sid) {
        return studentService.deleteStudent(sid);
    }
}
