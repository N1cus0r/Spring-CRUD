package com.nicusor.SchoolApp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nicusor.SchoolApp.model.Course;
import com.nicusor.SchoolApp.model.Teacher;
import com.nicusor.SchoolApp.repository.CourseRepository;
import com.nicusor.SchoolApp.service.TeacherService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = TeacherController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class TeacherControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TeacherService teacherService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllTeachers() throws Exception{
        Teacher teacher = Teacher
                .builder()
                .firstName("John")
                .lastName("Doe")
                .build();

        given(teacherService.getAllTeachers())
                .willReturn(new ArrayList<>(Arrays.asList(teacher)));

        ResultActions response = mockMvc
                .perform(get("/api/v1/teachers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.size()",
                        CoreMatchers.is(1)));
    }

    @Test
    public void testCreateTeacher() throws Exception{
        Teacher teacher = Teacher
                .builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .build();

        given(teacherService.create(ArgumentMatchers.any()))
                .willReturn(teacher);

        ResultActions response = mockMvc
                .perform(post("/api/v1/teachers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(teacher)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.firstName",
                        CoreMatchers.is(teacher.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.lastName",
                        CoreMatchers.is(teacher.getLastName())));
    }

    @Test
    public void testUpdateTeacher() throws Exception{
        Teacher teacherUpdateData = Teacher
                .builder()
                .id(1L)
                .firstName("Jack")
                .lastName("Scott")
                .build();

        given(teacherService.update(
                ArgumentMatchers.any(),
                ArgumentMatchers.any()))
                .willReturn(teacherUpdateData);

        ResultActions response = mockMvc
                .perform(put("/api/v1/teachers/" + teacherUpdateData.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(teacherUpdateData)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.firstName",
                        CoreMatchers.is(teacherUpdateData.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.lastName",
                        CoreMatchers.is(teacherUpdateData.getLastName())));
    }

    @Test
    public void testSetTeachersCourse() throws Exception{
        Course course = Course
                .builder()
                .id(1L)
                .subject("Maths")
                .numberOfHours(45)
                .build();

        Teacher teacher = Teacher
                .builder()
                .id(1L)
                .firstName("Jack")
                .lastName("Scott")
                .course(course)
                .build();

        given(teacherService.setTeachersCourse(
                ArgumentMatchers.any(),
                ArgumentMatchers.any()))
                .willReturn(teacher);

        ResultActions response = mockMvc
                .perform(put("/api/v1/teachers/"
                        + teacher.getId()
                        + "/set-course/"
                        + course.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.firstName",
                        CoreMatchers.is(teacher.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.lastName",
                        CoreMatchers.is(teacher.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.course.subject",
                        CoreMatchers.is(course.getSubject())))
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.course.numberOfHours",
                        CoreMatchers.is(course.getNumberOfHours()))
                );
    }
    @Test
    public void testDeleteTeacher() throws Exception{
        Teacher teacher = Teacher
                .builder()
                .id(1L)
                .firstName("Jack")
                .lastName("Scott")
                .build();

        ResultActions response = mockMvc
                .perform(delete("/api/v1/teachers/" + teacher.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"));

        response.andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.content().string(
                        "Teacher with id " + teacher.getId() + " was deleted"));
    }
}