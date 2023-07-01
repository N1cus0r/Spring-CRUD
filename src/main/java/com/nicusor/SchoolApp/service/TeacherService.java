package com.nicusor.SchoolApp.service;

import com.nicusor.SchoolApp.exceptions.CourseNotFoundException;
import com.nicusor.SchoolApp.exceptions.TeacherNotFoundException;
import com.nicusor.SchoolApp.model.Course;
import com.nicusor.SchoolApp.model.Teacher;
import com.nicusor.SchoolApp.repository.CourseRepository;
import com.nicusor.SchoolApp.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private CourseRepository courseRepository;
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public Teacher create(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public Teacher update(Long teacherId, Teacher teacherData) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new TeacherNotFoundException(
                        "teacher with given id does not exist"));

        teacher.setFirstName(teacherData.getFirstName());
        teacher.setLastName(teacherData.getLastName());

        return teacherRepository.save(teacher);
    }

    public Teacher setTeachersCourse(Long teacherId, Long courseId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new TeacherNotFoundException(
                        "teacher with given id does not exist"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() ->  new CourseNotFoundException(
                        "course with given id does not exist"));

        teacher.setCourse(course);

        return teacherRepository.save(teacher);
    }
}
