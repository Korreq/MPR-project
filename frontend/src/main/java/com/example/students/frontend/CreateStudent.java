package com.example.students.frontend;

import com.example.students.enums.Gender;
import com.example.students.enums.StudentUnit;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateStudent {
    @NotBlank
    private String name, surname;
    private StudentUnit unit;
    private Gender gender;
    private Integer semester;
}
