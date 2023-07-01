package com.nicusor.SchoolApp.controller;

import com.nicusor.SchoolApp.model.Student;
import com.nicusor.SchoolApp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Struct;
import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @GetMapping
    public List<Student> getAllStudents(){
        return studentService.getAllStudents();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Student createStudent(
            @RequestBody Student student
    ){
        return studentService.create(student);
    }

    @PutMapping("/{id}")
    public Student updateStudent(
            @PathVariable("id") Long studentId,
            @RequestBody Student student
    ) throws Exception {
        return studentService.update(studentId, student);
    }

    @PutMapping("/{studentId}/add-course/{courseId}")
    public Student addCourseToStudent(
            @PathVariable("studentId") Long studentId,
            @PathVariable("courseId") Long courseId
    ){
        return studentService.addCourseToStudent(studentId, courseId);
    }

    @PutMapping("/{studentId}/remove-course/{courseId}")
    public Student removeCourseFromStudent(
            @PathVariable("studentId") Long studentId,
            @PathVariable("courseId") Long courseId
    ){
        return studentService.removeCourseFromStudent(studentId, courseId);
    }
}
