package com.example.students.service;

import com.example.students.data.Student;
import com.example.students.data.StudentRepository;
import com.example.students.data.StudentUnit;
import com.example.students.exception.ResourceNotFoundException;
import com.example.students.exception.SemesterOutOfBounds;
import com.example.students.mappery.StudentMapper;
import com.example.students.resource.CreateStudent;
import com.example.students.resource.StudentDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Transactional
    public Student createStudent(CreateStudent createStudent) {
        var studentToSave = studentMapper.toEntity(createStudent);
        var index = createIndex(createStudent.getUnit());
        var semester = setSemester(createStudent.getSemester());
        studentToSave.setIndex(index);
        studentToSave.setSemester(semester);
        studentRepository.save(studentToSave);
        return studentToSave;
    }
    public StudentDto getStudentById(UUID id) {
        return studentRepository.findById(id)
                .map(studentMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Student " + id + " not found"));
    }
    @Transactional
    public void deleteByName(String name) {
        var students = studentRepository.findByName(name);
        if(students.isEmpty()) {
            throw new ResourceNotFoundException("Student with name " + name + " not found.");
        }
        studentRepository.deleteAll(students);
    }
    private Long createIndex(StudentUnit unit) {
        long maxIndex = studentRepository.findMaxIndex().orElse(1L);
        switch (unit){
            case BYTOM -> { return 7 * maxIndex; }
            case GDANSK -> { return 5 * maxIndex; }
            default -> { return 3 * maxIndex; }
        }
    }
    private Integer setSemester(Integer semester){
        if(semester == null){ return 1; }
        if(semester < 1 || semester > 16){
            throw new SemesterOutOfBounds("Semester must be within bounds of 0 and 17");
        }
        return semester;
    }
    public void updateSemester(UUID id){
        Student student;
        if(studentRepository.findById(id).isPresent()){
             student = studentRepository.findById(id).get();
        }
        else{
            throw new ResourceNotFoundException("Student " + id + " not found");
        }
        int nextSemester = student.getSemester() + 1;
        if(nextSemester < 2 || nextSemester > 16){
            throw new SemesterOutOfBounds("Semester must be within bounds of 0 and 17");
        }
        student.setSemester(nextSemester);
        studentRepository.save(student);
    }
    public List<StudentDto> getNameBy(String name) {
        return studentRepository.findByName(name)
                .stream().map(studentMapper::toDto)
                .toList();
    }
    public List<StudentDto> getBySemester(Integer semester){
        return studentRepository.findBySemester(semester).stream()
                .map(studentMapper::toDto)
                .toList();
    }
    public List<StudentDto> getByNameAndSurname(String name, String surname){
        return studentRepository.findByNameAndSurname(name, surname).stream()
                .map(studentMapper::toDto)
                .toList();
    }
    public List<StudentDto> getByNameAndUnit(String name, StudentUnit unit){
        return studentRepository.findByNameAndUnit(name, unit).stream()
                .map(studentMapper::toDto)
                .toList();
    }
    public List<StudentDto> getAll(){
        return studentRepository.findAll().stream()
                .map(studentMapper::toDto)
                .toList();
    }
}
