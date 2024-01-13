package com.example.students.resource;

import com.example.students.data.StudentUnit;
import com.example.students.exception.NotAllowedOperationException;
import com.example.students.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/students")
@RequiredArgsConstructor
public class StudentsResource {

    private final StudentService studentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createStudent(@RequestBody @Validated CreateStudent student) {
        studentService.createStudent(student);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateSemester(@PathVariable UUID id) { studentService.updateSemester(id); }

    @DeleteMapping
    public void deleteByName(String name) {
        if ("admin".equals(name)) { throw new NotAllowedOperationException(); }
        studentService.deleteByName(name);
    }
    @GetMapping("/{id}")
    public StudentDto getStudentById(@PathVariable UUID id){ return studentService.getStudentById(id); }

    @GetMapping("/byName")
    public List<StudentDto> getByName(@RequestParam String name){ return studentService.getNameBy(name); }

    @GetMapping("/bySemester")
    public List<StudentDto> getBySemester(@RequestParam Integer semester){ return studentService.getBySemester(semester); }

    @GetMapping("/byFullName")
    public List<StudentDto> getByNameAndSurname(@RequestParam String name, @RequestParam String surname){ return studentService.getByNameAndSurname(name, surname); }

    @GetMapping("/byNameAndUnit")
    public List<StudentDto> getByNameAndUnit(@RequestParam String name, @RequestParam StudentUnit unit){ return studentService.getByNameAndUnit(name, unit); }

    @GetMapping
    public List<StudentDto> getAll(){ return studentService.getAll(); }
}