package com.example.students.data;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student {

    @Id
    @GeneratedValue
    private UUID id;
    private String name, surname;
    @Enumerated(EnumType.STRING)
    private StudentUnit unit;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Setter
    private Long index;
    @Setter
    private Integer semester;
    public Student(String name, String surname, StudentUnit unit, Gender gender) {
        this.name = name;
        this.surname = surname;
        this.unit = unit;
        this.gender = gender;
    }
    public Student(String name, String surname, StudentUnit unit, Gender gender, Long index) {
        this.name = name;
        this.surname = surname;
        this.unit = unit;
        this.gender = gender;
        this.index = index;
    }
    public Student(String name, String surname, Integer semester) {
        this.name = name;
        this.surname = surname;
        this.semester = semester;
    }
}
