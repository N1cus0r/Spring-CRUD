package com.nicusor.SchoolApp.service;

import com.nicusor.SchoolApp.exceptions.CourseNotFoundException;
import com.nicusor.SchoolApp.exceptions.StudentNotFoundException;
import com.nicusor.SchoolApp.model.Course;
import com.nicusor.SchoolApp.model.Student;
import com.nicusor.SchoolApp.repository.CourseRepository;
import com.nicusor.SchoolApp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public Student create(Student student) {
        return studentRepository.save(student);
    }

    public Student update(
            Long studentId,
            Student studentData
    ) throws StudentNotFoundException {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(
                        "student with given id does not exist"));

        student.setFirstName(studentData.getFirstName());
        student.setLastName(studentData.getLastName());
        student.setAge(studentData.getAge());

        return studentRepository.save(student);
    }

    public Student addCourseToStudent(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(
                        "student with given id does not exist"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() ->  new CourseNotFoundException(
                        "course with given id does not exist"));

        student.getCourses().add(course);

        return studentRepository.save(student);
    }

    public Student removeCourseFromStudent(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(
                        "student with given id does not exist"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() ->  new CourseNotFoundException(
                        "course with given id does not exist"));

        student.getCourses().remove(course);

        return studentRepository.save(student);
    }
}
