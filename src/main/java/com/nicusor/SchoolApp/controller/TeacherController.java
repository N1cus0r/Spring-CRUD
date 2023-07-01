package com.nicusor.SchoolApp.controller;

import com.nicusor.SchoolApp.model.Teacher;
import com.nicusor.SchoolApp.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teachers")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @GetMapping
    public List<Teacher> getAllTeachers(){
        return teacherService.getAllTeachers();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Teacher createTeacher(
            @RequestBody Teacher teacher
    ){
        return teacherService.create(teacher);
    }
    @PutMapping("/{id}")
    public Teacher updateTeacher(
            @PathVariable("id") Long teacherId,
            @RequestBody Teacher teacher
    ){
        return teacherService.update(teacherId, teacher);
    }

    @PutMapping("/{teacherId}/set-course/{courseId}")
    public Teacher setTeachersCourse(
            @PathVariable("teacherId") Long teacherId,
            @PathVariable("courseId") Long courseId
    ){
        return teacherService.setTeachersCourse(teacherId, courseId);
    }
}
