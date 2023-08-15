package com.example.courses_finki.controller.user;

import com.example.courses_finki.entity.user.UserRole;
import com.example.courses_finki.service.student.StudentService;
import com.example.courses_finki.service.subject.SubjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class StudentController {

    private final StudentService studentService;
    private final SubjectService subjectService;

    public StudentController(StudentService studentService, SubjectService subjectService) {
        this.studentService = studentService;
        this.subjectService = subjectService;
    }

    @GetMapping("/students")
    public String getStudentPage(Model model) {
        model.addAttribute("students", studentService.getStudents());
        return "student/student";
    }

    @GetMapping("/add/student")
    public String getAddStudentPage() {
        return "student/form";
    }

    @GetMapping("/edit/student/{id}")
    public String getEditStudentPage(@PathVariable(name = "id") Long id, Model model) throws Exception {
        model.addAttribute("student", studentService.findById(id));
        return "student/form";
    }


    @PostMapping("/add/student")
    public String addStudent(@RequestParam(name = "firstName") String firstName,
                             @RequestParam(name = "lastName") String lastName,
                             @RequestParam(name = "username") String username,
                             @RequestParam(name = "password") String password) {
        studentService.addStudent(firstName, lastName, username, password, UserRole.STUDENT);
        return "redirect:/students";
    }

    @PostMapping("/edit/student/{id}")
    public String editStudent(@PathVariable(name = "id") Long id,
                              @RequestParam(name = "firstName") String firstName,
                              @RequestParam(name = "lastName") String lastName,
                              @RequestParam(name = "username") String username,
                              @RequestParam(name = "password") String password) throws Exception {
        studentService.editStudent(id, firstName, lastName, username, password, UserRole.STUDENT);
        return "redirect:/students";
    }

    @PostMapping("/delete/student/{id}")
    public String deleteStudent(@PathVariable(name = "id") Long id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }

}
