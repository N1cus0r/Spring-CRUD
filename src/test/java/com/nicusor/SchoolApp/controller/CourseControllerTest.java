package com.nicusor.SchoolApp.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.nicusor.SchoolApp.model.Course;
import com.nicusor.SchoolApp.service.CourseService;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = CourseController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class CourseControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CourseService courseService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllUsers() throws Exception {
        Course course = Course
                .builder()
                .subject("Maths")
                .numberOfHours(45)
                .build();

        given(courseService.getAllCourses())
                .willReturn(new ArrayList<>(Arrays.asList(course)));

        ResultActions response = mockMvc
                .perform(get("/api/v1/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.size()"
                        , CoreMatchers.is(1)));
    }

    @Test
    public void testCreateCourse() throws Exception{
        Course course = Course
                .builder()
                .id(1L)
                .subject("Maths")
                .numberOfHours(45)
                .build();

        given(courseService.create(ArgumentMatchers.any()))
                .willAnswer((invocationOnMock -> invocationOnMock.getArgument(0)));

        ResultActions response = mockMvc
                .perform(post("/api/v1/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(course)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.subject", CoreMatchers.is(course.getSubject())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfHours", CoreMatchers.is(course.getNumberOfHours())));
    }

    @Test
    public void testUpdateCourse() throws Exception{
        Course updatedCourseData = Course
                .builder()
                .id(1L)
                .subject("Maths")
                .numberOfHours(45)
                .build();

        given(courseService.update(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .willReturn(updatedCourseData);

        ResultActions response = mockMvc
                .perform(put("/api/v1/courses/" + updatedCourseData.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(updatedCourseData)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

}