package com.nicusor.SchoolApp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nicusor.SchoolApp.model.Course;
import com.nicusor.SchoolApp.model.Student;
import com.nicusor.SchoolApp.service.StudentService;
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
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = StudentController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentService studentService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllStudents() throws Exception{
        Student student = Student
                .builder()
                .firstName("John")
                .lastName("Doe")
                .age(20)
                .build();

        given(studentService.getAllStudents())
                .willReturn(new ArrayList<>(Arrays.asList(student)));

        ResultActions response = mockMvc
                .perform(get("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(
                                "$.size()",
                                CoreMatchers.is(1)));
    }

    @Test
    public void testCreateStudent() throws Exception{
        Student student = Student
                .builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .age(20)
                .build();

        given(studentService.create(ArgumentMatchers.any()))
                .willReturn(student);

        ResultActions response = mockMvc
                .perform(post("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(student)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.firstName",
                        CoreMatchers.is(student.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.lastName",
                        CoreMatchers.is(student.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.age",
                        CoreMatchers.is(student.getAge())));
    }

    @Test
    public void testUpdateStudent() throws Exception{
        Student updatedStudentData = Student
                .builder()
                .id(1L)
                .firstName("Jack")
                .lastName("Scott")
                .age(25)
                .build();

        given(studentService.update(
                ArgumentMatchers.any(),
                ArgumentMatchers.any()))
                .willReturn(updatedStudentData);

        ResultActions response = mockMvc
                .perform(put("/api/v1/students/" + updatedStudentData.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(updatedStudentData)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.firstName",
                        CoreMatchers.is(updatedStudentData.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.lastName",
                        CoreMatchers.is(updatedStudentData.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.age",
                        CoreMatchers.is(updatedStudentData.getAge())));
    }

    @Test
    public void testAddCourseToStudent() throws Exception{
        Course course = Course
                .builder()
                .id(1L)
                .subject("Maths")
                .numberOfHours(45)
                .build();

        Student student = Student
                .builder()
                .id(1L)
                .firstName("Jack")
                .lastName("Scott")
                .age(25)
                .courses(new ArrayList<>(List.of(course)))
                .build();

        given(studentService.addCourseToStudent(
                ArgumentMatchers.any(),
                ArgumentMatchers.any()))
                .willReturn(student);

        ResultActions response = mockMvc
                .perform(put("/api/v1/students/"
                                + student.getId()
                                + "/add-course/"
                                + course.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.firstName",
                        CoreMatchers.is(student.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.lastName",
                        CoreMatchers.is(student.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.age",
                        CoreMatchers.is(student.getAge())))
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.courses.size()",
                        CoreMatchers.is(1)));
    }

    @Test
    public void testRemoveCourseFromStudent() throws Exception{
        Course course = Course
                .builder()
                .id(1L)
                .subject("Maths")
                .numberOfHours(45)
                .build();

        Student student = Student
                .builder()
                .id(1L)
                .firstName("Jack")
                .lastName("Scott")
                .age(25)
                .courses(new ArrayList<>())
                .build();

        given(studentService.removeCourseFromStudent(
                ArgumentMatchers.any(),
                ArgumentMatchers.any()))
                .willReturn(student);

        ResultActions response = mockMvc
                .perform(put("/api/v1/students/"
                        + student.getId()
                        + "/remove-course/"
                        + course.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.firstName",
                        CoreMatchers.is(student.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.lastName",
                        CoreMatchers.is(student.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.age",
                        CoreMatchers.is(student.getAge())))
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.courses.size()",
                        CoreMatchers.is(0)));
    }



}