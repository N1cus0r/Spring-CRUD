package com.nicusor.SchoolApp.service;

import com.nicusor.SchoolApp.model.Course;
import com.nicusor.SchoolApp.model.Teacher;
import com.nicusor.SchoolApp.repository.CourseRepository;
import com.nicusor.SchoolApp.repository.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {
    @Mock
    private TeacherRepository teacherRepository;
    @Mock
    private CourseRepository courseRepository;
    @InjectMocks
    private TeacherService teacherService;

    @Test
    public void testGetAllTeachers(){
        teacherService.getAllTeachers();
        verify(teacherRepository).findAll();
    }

    @Test
    public void testCreateTeacher(){
        Teacher teacher = Teacher
                .builder()
                .firstName("John")
                .lastName("Doe")
                .build();

        teacherService.create(teacher);

        ArgumentCaptor<Teacher> teacherArgumentCaptor =
                ArgumentCaptor.forClass(Teacher.class);

        verify(teacherRepository)
                .save(teacherArgumentCaptor.capture());

        assertThat(teacherArgumentCaptor.getValue()).isEqualTo(teacher);
    }

    @Test
    public void testUpdateTeacher(){
        Teacher teacherBeforeUpdate = Teacher
                .builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .build();

        Teacher teacherUpdateData = Teacher
                .builder()
                .id(1L)
                .firstName("Jack")
                .lastName("Scott")
                .build();

        given(teacherRepository.findById(teacherBeforeUpdate.getId()))
                .willReturn(Optional.of(teacherBeforeUpdate));

        teacherService.update(teacherBeforeUpdate.getId(), teacherUpdateData);

        ArgumentCaptor<Teacher> teacherArgumentCaptor =
                ArgumentCaptor.forClass(Teacher.class);

        verify(teacherRepository)
                .save(teacherArgumentCaptor.capture());

        assertThat(teacherArgumentCaptor.getValue()).isEqualTo(teacherUpdateData);
    }

    @Test
    public void testSetTeacherCourse(){
        Teacher teacher = Teacher
                .builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .build();

        Course course = Course
                .builder()
                .id(1L)
                .subject("Maths")
                .numberOfHours(45)
                .build();

        given(teacherRepository.findById(teacher.getId()))
                .willReturn(Optional.of(teacher));

        given(courseRepository.findById(course.getId()))
                .willReturn(Optional.of(course));

        teacherService.setTeachersCourse(teacher.getId(), course.getId());

        ArgumentCaptor<Teacher> teacherArgumentCaptor =
                ArgumentCaptor.forClass(Teacher.class);

        verify(teacherRepository)
                .save(teacherArgumentCaptor.capture());

        assertThat(teacherArgumentCaptor.getValue().getCourse())
                .isEqualTo(course);
    }

}