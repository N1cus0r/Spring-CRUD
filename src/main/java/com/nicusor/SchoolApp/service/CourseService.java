package com.nicusor.SchoolApp.service;

import com.nicusor.SchoolApp.exceptions.CourseNotFoundException;
import com.nicusor.SchoolApp.exceptions.StudentNotFoundException;
import com.nicusor.SchoolApp.model.Course;
import com.nicusor.SchoolApp.model.Student;
import com.nicusor.SchoolApp.repository.CourseRepository;
import com.nicusor.SchoolApp.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    public List<Course> getAllCourses(){return courseRepository.findAll();}

    public Course create(Course course) {
        return courseRepository.save(course);
    }

    public Course update(Long courseId, Course courseData) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() ->  new CourseNotFoundException(
                        "course with given id does not exist"));

        course.setSubject(courseData.getSubject());
        course.setNumberOfHours(courseData.getNumberOfHours());

        return courseRepository.save(course);
    }
}
