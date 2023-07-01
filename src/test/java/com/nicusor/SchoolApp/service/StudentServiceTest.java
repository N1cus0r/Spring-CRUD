package com.nicusor.SchoolApp.service;

import com.nicusor.SchoolApp.model.Course;
import com.nicusor.SchoolApp.model.Student;
import com.nicusor.SchoolApp.repository.CourseRepository;
import com.nicusor.SchoolApp.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private CourseRepository courseRepository;
    @InjectMocks
    private StudentService studentService;

    @Test
    public void testGetAllStudents(){
        studentService.getAllStudents();
        verify(studentRepository).findAll();
    }

    @Test
    public void testCreateStudent(){
        Student student = Student
                .builder()
                .firstName("John")
                .lastName("Doe")
                .age(19)
                .build();

        studentService.create(student);

        ArgumentCaptor<Student> studentArgumentCaptor =
                ArgumentCaptor.forClass(Student.class);

        verify(studentRepository)
                .save(studentArgumentCaptor.capture());

        assertThat(studentArgumentCaptor.getValue()).isEqualTo(student);
    }

    @Test
    public void testUpdateStudent(){
        Student studentBeforeUpdate = Student
                .builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .age(19)
                .build();

        Student studentUpdateData = Student
                .builder()
                .id(1L)
                .firstName("Jack")
                .lastName("Scott")
                .age(19)
                .build();

        given(studentRepository.findById(studentBeforeUpdate.getId()))
                .willReturn(Optional.of(studentBeforeUpdate));

        studentService.update(studentBeforeUpdate.getId(), studentUpdateData);

        ArgumentCaptor<Student> studentArgumentCaptor =
                ArgumentCaptor.forClass(Student.class);

        verify(studentRepository)
                .save(studentArgumentCaptor.capture());

        assertThat(studentArgumentCaptor.getValue()).isEqualTo(studentUpdateData);
    }

    @Test
    public void testAddCourseToStudent(){
        Course course = Course
                .builder()
                .id(1L)
                .subject("Maths")
                .numberOfHours(45)
                .build();

        Student student = Student
                .builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .age(19)
                .courses(new ArrayList<>())
                .build();

        given(studentRepository.findById(student.getId()))
                .willReturn(Optional.of(student));

        given(courseRepository.findById(course.getId()))
                .willReturn(Optional.of(course));

        studentService.addCourseToStudent(
                student.getId(),
                course.getId());

        ArgumentCaptor<Student> studentArgumentCaptor =
                ArgumentCaptor.forClass(Student.class);

        verify(studentRepository)
                .save(studentArgumentCaptor.capture());

        assertThat(studentArgumentCaptor.getValue().getCourses().get(0))
                .isEqualTo(course);
    }

    @Test
    public void testRemoveCourseFromStudent(){
        Course course = Course
                .builder()
                .id(1L)
                .subject("Maths")
                .numberOfHours(45)
                .build();

        Student student = Student
                .builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .age(19)
                .courses(new ArrayList<>(List.of(course)))
                .build();

        given(studentRepository.findById(student.getId()))
                .willReturn(Optional.of(student));

        given(courseRepository.findById(course.getId()))
                .willReturn(Optional.of(course));

        studentService.removeCourseFromStudent(
                student.getId(),
                course.getId());

        ArgumentCaptor<Student> studentArgumentCaptor =
                ArgumentCaptor.forClass(Student.class);

        verify(studentRepository)
                .save(studentArgumentCaptor.capture());

        assertThat(studentArgumentCaptor.getValue().getCourses().size())
                .isEqualTo(0);
    }
}