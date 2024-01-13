package com.example.students.resource;

import com.example.students.data.Gender;
import com.example.students.data.StudentUnit;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateStudent {

    @NotBlank
    private String name, surname;
    private StudentUnit unit;
    private Gender gender;
    private Integer semester;
}
