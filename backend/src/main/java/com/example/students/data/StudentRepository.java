package com.example.students.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
    @Query("select max(s.index) from Student s")
    Optional<Long> findMaxIndex();
    List<Student> findByName(String name);
    List<Student> findByNameAndSurname(String name, String surname);
    List<Student> findByNameAndUnit(String name, StudentUnit unit);
    List<Student> findBySemester(Integer semester);
}
