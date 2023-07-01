package com.nicusor.SchoolApp.service;

import com.nicusor.SchoolApp.model.Course;
import com.nicusor.SchoolApp.repository.CourseRepository;
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
class CourseServiceTest {
    @Mock
    private CourseRepository courseRepository;
    @InjectMocks
    private CourseService courseService;

    @Test
    public void testGetAllCourses(){
        courseService.getAllCourses();
        verify(courseRepository).findAll();
    }

    @Test
    public void testCreateCourse(){
        Course course = Course.builder()
                .subject("Maths")
                .numberOfHours(45)
                .build();
        courseService.create(course);

        ArgumentCaptor<Course> courseArgumentCaptor =
                ArgumentCaptor.forClass(Course.class);

        verify(courseRepository)
                .save(courseArgumentCaptor.capture());

        assertThat(courseArgumentCaptor.getValue()).isEqualTo(course);
    }

    @Test
    public void testUpdateCourse(){
        Course courseBeforeUpdate = Course.builder()
                .id(1L)
                .subject("Maths")
                .numberOfHours(45)
                .build();

        Course courseUpdateData = Course.builder()
                .id(1L)
                .subject("Biology")
                .numberOfHours(35)
                .build();

        given(courseRepository.findById(courseBeforeUpdate.getId()))
                .willReturn(Optional.of(courseBeforeUpdate));

        courseService.update(courseBeforeUpdate.getId(), courseUpdateData);

        ArgumentCaptor<Course> courseArgumentCaptor =
                ArgumentCaptor.forClass(Course.class);

        verify(courseRepository)
                .save(courseArgumentCaptor.capture());

        assertThat(courseArgumentCaptor.getValue()).isEqualTo(courseUpdateData);
    }
}