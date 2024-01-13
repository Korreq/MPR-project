package com.example.students.frontend;

import com.example.students.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students-page")
public class StudentsPageController {
    private final StudentService studentService;

    public StudentsPageController(StudentService studentService) {
        this.studentService = studentService;
    }

    @ModelAttribute("data")
    private StudentDto newStudentDto(){ return new StudentDto(); }
    //Index
    @GetMapping
    public String getStudentsPage(Model model){
        var students = studentService.getAll();
        model.addAttribute("students", students);
        return "index";
    }
    //Adding Students
    @GetMapping("/add")
    public String addStudentPage(Model model) {
        model.addAttribute("student", new CreateStudent());
        return "addStudent";
    }
    @PostMapping("/add")
    public String addStudentAndRedirectToIndex(@ModelAttribute CreateStudent createStudent){
        studentService.createStudent(createStudent);
        return "redirect:/students-page";
    }
    //Deleting Students
    @GetMapping("/delete")
    public String deleteStudentPage(Model model){
        model.addAttribute("student", new StudentDto());
        return "deleteStudent";
    }
    @PostMapping("/delete")
    public String deleteStudentAndRedirectToIndex(@ModelAttribute StudentDto studentDto){
        studentService.deleteByName(studentDto.getName());
        return "redirect:/students-page";
    }
    //Updating semester for student
    @GetMapping("/update")
    public String updateStudentsSemesterPage(Model model){
        model.addAttribute("student", new StudentDto());
        return "updateSemester";
    }
    @PostMapping("/update")
    public String updateStudentsSemesterAndRedirectToIndex(@ModelAttribute StudentDto studentDto){
        studentService.updateSemester(studentDto.getId());
        return "redirect:/students-page";
    }
    //Getting student by name
    @GetMapping("/byName")
    public String getStudentByName(Model model){
        model.addAttribute("data", new StudentDto());
        return "byName";
    }
    @PostMapping("/byName")
    public String getStudentByNameAndRefresh(Model model, @ModelAttribute StudentDto studentDto){
        var filteredStudents = studentService.getNameBy(studentDto.getName());
        model.addAttribute("filteredStudents", filteredStudents);
        return "byName";
    }
    //Getting student by full name
    @GetMapping("/byFullName")
    public String getStudentByNameAndSurname(Model model){
        model.addAttribute("data", new StudentDto());
        return "byFullName";
    }
    @PostMapping("/byFullName")
    public String getStudentByNameAndSurnameAndRefresh(Model model, @ModelAttribute StudentDto studentDto){
        var filteredStudents = studentService.getByNameAndSurname(studentDto.getName(), studentDto.getSurname());
        model.addAttribute("filteredStudents", filteredStudents);
        return "byFullName";
    }
    //Getting student by name and unit
    @GetMapping("/byNameAndUnit")
    public String getStudentByNameAndUnit(Model model){
        model.addAttribute("data", new StudentDto());
        return "byNameAndUnit";
    }
    @PostMapping("/byNameAndUnit")
    public String getStudentByNameAndUnitAndRefresh(Model model, @ModelAttribute StudentDto studentDto){
        var filteredStudents = studentService.getByNameAndUnit(studentDto.getName(), studentDto.getUnit());
        model.addAttribute("filteredStudents", filteredStudents);
        return "byNameAndUnit";
    }
    //Getting student by semester
    @GetMapping("/bySemester")
    public String getStudentBySemester(Model model){
        model.addAttribute("data", new StudentDto());
        return "bySemester";
    }
    @PostMapping("/bySemester")
    public String getStudentBySemesterAndRefresh(Model model, @ModelAttribute StudentDto studentDto){
        var filteredStudents = studentService.getBySemester(studentDto.getSemester());
        model.addAttribute("filteredStudents", filteredStudents);
        return "bySemester";
    }
}
