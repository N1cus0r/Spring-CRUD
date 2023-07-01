package com.nicusor.SchoolApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Course {
    @Id
    @SequenceGenerator(
            name = "course_sequence",
            sequenceName = "course_sequence"

    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "course_sequence"
    )
    private Long id;
    @NotBlank(message = "Subject is mandatory")
    private String subject;
    @Min(30)
    private Integer numberOfHours;
    @JsonIgnore
    @ManyToMany(mappedBy = "courses", cascade = CascadeType.ALL)
    private List<Student> students = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "course")
    private List<Teacher> teachers = new ArrayList<>();
}
