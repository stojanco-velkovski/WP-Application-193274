package com.example.courses_finki.controller.user;

import com.example.courses_finki.entity.user.UserRole;
import com.example.courses_finki.service.professor.ProfessorService;
import com.example.courses_finki.service.student.StudentService;
import com.example.courses_finki.service.subject.SubjectService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProfessorController {

    private final ProfessorService professorService;
    private final SubjectService subjectService;
    private final StudentService studentService;

    public ProfessorController(ProfessorService professorService, SubjectService subjectService, StudentService studentService) {
        this.professorService = professorService;
        this.subjectService = subjectService;
        this.studentService = studentService;
    }

    @GetMapping("/professors")
    public String getProfessorPage(Model model) {
        model.addAttribute("professors", professorService.getProfessors());
        return "professor/professor";
    }

    @GetMapping("/add/professor")
    public String getAddProfessorPage() {
        return "professor/form";
    }

    @GetMapping("/edit/professor/{id}")
    public String getEditProfessorPage(@PathVariable(name = "id") Long id, Model model) throws Exception {
        model.addAttribute("professor", professorService.findById(id));
        return "professor/form";
    }


    @GetMapping(path = {"/search/student/index", "/search/student/name"})
    public String getSearchPage(Model model, HttpServletRequest request) {
        String requestedPath = request.getRequestURI();
        if (requestedPath.equals("/search/student/index")) {
            model.addAttribute("index", "index");
        }
        return "search/form";
    }

    @PostMapping("/add/professor")
    public String addProfessor(@RequestParam(name = "firstName") String firstName,
                               @RequestParam(name = "lastName") String lastName,
                               @RequestParam(name = "username") String username,
                               @RequestParam(name = "password") String password) {
        professorService.addProfessor(firstName, lastName, username, password, UserRole.PROFESSOR);
        return "redirect:/professors";
    }

    @PostMapping("/edit/professor/{id}")
    public String editProfessor(@PathVariable(name = "id") Long id,
                                @RequestParam(name = "firstName") String firstName,
                                @RequestParam(name = "lastName") String lastName,
                                @RequestParam(name = "username") String username,
                                @RequestParam(name = "password") String password) throws Exception {
        professorService.editProfessor(id, firstName, lastName, username, password, UserRole.PROFESSOR);
        return "redirect:/professors";
    }


    @PostMapping("/delete/professor/{id}")
    public String deleteProfessor(@PathVariable(name = "id") Long id) {
        professorService.deleteProfessor(id);
        return "redirect:/professors";
    }


    @PostMapping(path = {"/search/student/index", "/search/student/name"})
    public String getSearchPage(@RequestParam(name = "name", required = false) String name, @RequestParam(name = "index", required = false) String index, Model model) {
        model.addAttribute("students", studentService.filterStudents(index, name));
        return "search/list";
    }
}
