package com.example.students.service;

import com.example.students.enums.StudentUnit;
import com.example.students.exception.ResourceNotFoundException;
import com.example.students.frontend.CreateStudent;
import com.example.students.frontend.StudentDto;
import lombok.extern.java.Log;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Log
@Service
public class StudentService {
    private static final String API_URL = "http://localhost:8080/students";
    private final RestTemplate restTemplate;
    private final WebClient webClient;

    public StudentService(RestTemplate restTemplate, WebClient webClient) {
        this.restTemplate = restTemplate;
        this.webClient = webClient;
    }

    public void createStudent(CreateStudent createStudent) {
        webClient.post()
                .bodyValue(createStudent)
                .retrieve()
                .toBodilessEntity()
                .subscribe(response -> log.info("Student save properly"));
        log.info("Response returned");
    }
    public void updateSemester(UUID id){ restTemplate.exchange(API_URL+"/"+id, HttpMethod.PUT, null, Void.class); }
    public void deleteByName(String name){ restTemplate.delete(API_URL + "?name=" + name); }
    public StudentDto getStudentById(UUID id) {
        try{
            return webClient.get()
                    .uri("/{id}", id)
                    .retrieve()
                    .bodyToMono(StudentDto.class)
                    .block(Duration.of(10, ChronoUnit.SECONDS));
        }
        catch (WebClientResponseException e){ throw new ResourceNotFoundException("Student with id " + id + " not found"); }
        catch (HttpServerErrorException e){ throw new RuntimeException("Error during sending request"); }
    }
    public List<StudentDto> getNameBy(String name) {
        try {
            return restTemplate.exchange(API_URL + "/byName?name=" + name, HttpMethod.GET, null, new ParameterizedTypeReference<List<StudentDto>>(){})
                    .getBody();
        }
        catch (WebClientResponseException e) { throw new ResourceNotFoundException("Student with name " + name + " not found");}
        catch (HttpServerErrorException e) { throw new RuntimeException("Error during sending request"); }
    }
    public List<StudentDto> getByNameAndSurname(String name, String surname) {
        try {
            return restTemplate.exchange(API_URL + "/byFullName?name=" + name +"&surname=" + surname,
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<StudentDto>>(){})
                    .getBody();
        }
        catch (WebClientResponseException e) { throw new ResourceNotFoundException("Student with named " + name +" "+ surname + " not found");}
        catch (HttpServerErrorException e) { throw new RuntimeException("Error during sending request"); }
    }
    public List<StudentDto> getByNameAndUnit(String name, StudentUnit unit) {
        try {
            return restTemplate.exchange(API_URL + "/byNameAndUnit?name=" + name +"&unit=" + unit,
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<StudentDto>>(){})
                    .getBody();
        }
        catch (WebClientResponseException e) { throw new ResourceNotFoundException("Student with name " + name +" in "+ unit + " not found");}
        catch (HttpServerErrorException e) { throw new RuntimeException("Error during sending request"); }
    }
    public List<StudentDto> getBySemester(Integer semester) {
        try {
            return restTemplate.exchange(API_URL + "/bySemester?semester=" + semester,
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<StudentDto>>(){})
                    .getBody();
        }
        catch (HttpServerErrorException e) { throw new RuntimeException("Error during sending request"); }
    }
    public List<StudentDto> getAll() {
        try{
            return restTemplate.exchange(API_URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<StudentDto>>(){})
                    .getBody();
        }
        catch (HttpServerErrorException e){ throw new RuntimeException("Error during sending request"); }
    }
}
