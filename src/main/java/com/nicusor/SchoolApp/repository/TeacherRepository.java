package com.nicusor.SchoolApp.repository;

import com.nicusor.SchoolApp.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
