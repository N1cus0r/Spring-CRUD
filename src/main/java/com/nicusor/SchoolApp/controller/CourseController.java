package com.nicusor.SchoolApp.controller;

import com.nicusor.SchoolApp.model.Course;
import com.nicusor.SchoolApp.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {
    @Autowired
    public CourseService courseService;
    @GetMapping
    public List<Course> getAllCourses(){return courseService.getAllCourses();}
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Course createCourse(
            @RequestBody Course course
    ) {
        return courseService.create(course);
    }
    @PutMapping("/{id}")
    public Course updateCourse(
            @PathVariable("id") Long courseId,
            @RequestBody Course course
    ){
        return courseService.update(courseId, course);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteCourse(
            @PathVariable("id") Long courseId
    ){
        courseService.delete(courseId);
        return "Course with id " + courseId + " was deleted";
    }
}
