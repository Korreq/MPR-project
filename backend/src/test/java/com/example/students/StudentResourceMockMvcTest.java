package com.example.students;

import com.example.students.data.Gender;
import com.example.students.data.Student;
import com.example.students.data.StudentRepository;
import com.example.students.data.StudentUnit;
import com.example.students.exception.ErrorResponse;
import com.example.students.resource.StudentDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
class StudentResourceMockMvcTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void cleanUp() {
        studentRepository.deleteAll();
    }

    @Test
    void givenNotExistingStudentId_whenGetStudentById_thenRecordNotFoundExceptionIsThrown() throws Exception {
        var studentId = UUID.randomUUID();

        var response = mockMvc.perform(get("/students/" + studentId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("not found")))
                .andReturn()
                .getResponse();

        assertEquals(response.getStatus(), 404);
        assertTrue(response.getContentAsString().contains("not found"));
    }

    @Test
    void givenNoStudentsWhenGetByNameThenReturnEmptyList() throws Exception {
        mockMvc.perform(get("/students?name=Karola"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[]")));
    }
    @Test
    void givenNoStudentsWhenGetByNameAndSurnameThenReturnEmptyList() throws Exception {
        mockMvc.perform(get("/students?name=Karola&surname=Sur"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[]")));
    }
    @Test
    void givenNoStudentsWhenGetByNameAndUnitThenReturnEmptyList() throws Exception {
        mockMvc.perform(get("/students?name=Karola&unit=WARSZAWA"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[]")));
    }
    @Test
    void givenNoStudentsWhenGetBySemesterThenReturnEmptyList() throws Exception {
        mockMvc.perform(get("/students/semester?semester=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[]")));
    }
    @Test
    void givenStudentsWithNameWhenGetByNameThenReturnValidList() throws Exception {
        var student1 = new Student("Karola","Sur", StudentUnit.GDANSK, Gender.FEMALE, 15L);
        var student2 = new Student("Karola", "Nam", StudentUnit.WARSZAWA, Gender.FEMALE, 5L);
        var student3 = new Student("Jan", "Kowalski", StudentUnit.BYTOM, Gender.MALE, 17L);
        studentRepository.saveAll(List.of(student1, student2, student3));

        var response = mockMvc.perform(get("/students?name=Karola"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        var returnedStudents = objectMapper.readValue(response.getContentAsString(), new TypeReference<List<StudentDto>>() {});

        assertEquals(returnedStudents.size(), 2);
        assertEquals(returnedStudents.get(0).getName(), "Karola");
        assertEquals(returnedStudents.get(0).getUnit(), StudentUnit.GDANSK);
    }
    @Test
    void givenStudentsWithNameAndSurnameWhenGetByNameAndSurnameThenReturnValidList() throws Exception {
        var student1 = new Student("Karola","Sur", StudentUnit.GDANSK, Gender.FEMALE, 15L);
        var student2 = new Student("Karola", "Nam", StudentUnit.WARSZAWA, Gender.FEMALE, 5L);
        var student3 = new Student("Jan", "Kowalski", StudentUnit.BYTOM, Gender.MALE, 17L);
        studentRepository.saveAll(List.of(student1, student2, student3));

        var response = mockMvc.perform(get("/students/full?name=Karola&surname=Nam"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        var returnedStudents = objectMapper.readValue(response.getContentAsString(), new TypeReference<List<StudentDto>>() {});

        assertEquals(returnedStudents.size(), 1);
        assertEquals(returnedStudents.get(0).getName(), "Karola");
        assertEquals(returnedStudents.get(0).getUnit(), StudentUnit.WARSZAWA);
    }
    @Test
    void givenStudentsWithNameAndUnitWhenGetByNameAndUnitThenReturnValidList() throws Exception {
        var student1 = new Student("Karola","Sur", StudentUnit.GDANSK, Gender.FEMALE, 15L);
        var student2 = new Student("Karola", "Nam", StudentUnit.WARSZAWA, Gender.FEMALE, 5L);
        var student3 = new Student("Jan", "Kowalski", StudentUnit.BYTOM, Gender.MALE, 17L);
        studentRepository.saveAll(List.of(student1, student2, student3));

        var response = mockMvc.perform(get("/students/unit?name=Karola&unit=GDANSK"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        var returnedStudents = objectMapper.readValue(response.getContentAsString(), new TypeReference<List<StudentDto>>() {});

        assertEquals(returnedStudents.size(), 1);
        assertEquals(returnedStudents.get(0).getName(), "Karola");
        assertEquals(returnedStudents.get(0).getUnit(), StudentUnit.GDANSK);
    }
    @Test
    void givenStudentsWithNameWhenDeleteByNameThenDeleteStudents() throws Exception {
        var student1 = new Student("Karola","Sur", StudentUnit.GDANSK, Gender.FEMALE, 15L);
        var student2 = new Student("Karola", "Nam", StudentUnit.WARSZAWA, Gender.FEMALE, 5L);
        var student3 = new Student("Jan", "Kowalski", StudentUnit.BYTOM, Gender.MALE, 17L);
        studentRepository.saveAll(List.of(student1, student2, student3));

        mockMvc.perform(delete("/students?name=Karola"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        var students = studentRepository.findAll();
        assertEquals(students.size(), 1);
        assertEquals(students.get(0).getName(), "Jan");
    }
    @Test
    void givenStudentWithSemester_whenUpdateSemester_thenSemesterOutOfBoundsExceptionIsThrown() throws Exception {
        var student = new Student("Name", "Surname", 16);
        studentRepository.save(student);
        UUID id = studentRepository.findByName("Name").get(0).getId();
        var response = mockMvc.perform(post("/students/" + id))
                .andDo(print())
                .andExpect(content().string(containsString("within bounds")))
                .andReturn()
                .getResponse();

        student = studentRepository.findAll().get(0);
        assertEquals(student.getSemester(), 16);
        assertTrue(response.getContentAsString().contains("within bounds"));
    }
    @Test
    void givenNoStudentsWhenDeleteByNameThenReturnNotFound() throws Exception {
        mockMvc.perform(delete("/students?name=Karola"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}