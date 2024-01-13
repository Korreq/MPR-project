package com.example.students.frontend;

import com.example.students.enums.Gender;
import com.example.students.enums.StudentUnit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {
    private UUID id;
    private String name, surname;
    private StudentUnit unit;
    private Gender gender;
    private Long index;
    private Integer semester;
}
