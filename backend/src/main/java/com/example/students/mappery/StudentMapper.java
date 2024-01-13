package com.example.students.mappery;

import com.example.students.data.Student;
import com.example.students.resource.CreateStudent;
import com.example.students.resource.StudentDto;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    public StudentDto toDto(Student student) {
        return new StudentDto(
                student.getId(),
                student.getName(),
                student.getSurname(),
                student.getUnit(),
                student.getGender(),
                student.getIndex(),
                student.getSemester()
        );
    }
    public Student toEntity(CreateStudent createStudent) {
        return new Student(createStudent.getName(), createStudent.getSurname(), createStudent.getUnit(), createStudent.getGender());
    }
}
