package com.studentapp.controller;

import com.studentapp.entity.Student;
import com.studentapp.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

/**
 * Student Controller Class
 * Handles HTTP requests for Student operations
 * Provides both web views (Thymeleaf templates) and REST endpoints
 */
@Controller
@RequestMapping("/students")
@RequiredArgsConstructor
@Slf4j
public class StudentController {

    private final StudentService studentService;

    /**
     * Home page - Redirect to student list
     */
    @GetMapping("/home")
    public String home() {
        log.info("Accessing home page");
        return "redirect:/students/list";
    }

    /**
     * Display list of all students
     */
    @GetMapping("/list")
    public String listStudents(Model model, @RequestParam(required = false) String search) {
        log.info("Listing students");

        List<Student> students;
        if (search != null && !search.isEmpty()) {
            log.info("Searching students with pattern: {}", search);
            students = studentService.searchStudentsByName(search);
            model.addAttribute("search", search);
        } else {
            students = studentService.getAllStudents();
        }

        model.addAttribute("students", students);
        model.addAttribute("totalStudents", students.size());
        return "student-list";
    }

    /**
     * Display add student form
     */
    @GetMapping("/add")
    public String showAddForm(Model model) {
        log.info("Displaying add student form");
        model.addAttribute("student", new Student());
        return "add-student";
    }

    /**
     * Save a new student
     */
    @PostMapping("/save")
    public String saveStudent(@ModelAttribute Student student, RedirectAttributes redirectAttributes) {
        try {
            log.info("Saving new student with email: {}", student.getEmail());
            studentService.addStudent(student);
            redirectAttributes.addFlashAttribute("message", "Student added successfully!");
            redirectAttributes.addFlashAttribute("alertType", "success");
            return "redirect:/students/list";
        } catch (IllegalArgumentException e) {
            log.error("Error saving student: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("alertType", "danger");
            return "redirect:/students/add";
        } catch (Exception e) {
            log.error("Unexpected error while saving student", e);
            redirectAttributes.addFlashAttribute("message", "An error occurred while saving the student");
            redirectAttributes.addFlashAttribute("alertType", "danger");
            return "redirect:/students/add";
        }
    }

    /**
     * Display edit student form
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        log.info("Displaying edit form for student ID: {}", id);

        Optional<Student> student = studentService.getStudentById(id);
        if (student.isPresent()) {
            model.addAttribute("student", student.get());
            return "update-student";
        } else {
            log.error("Student not found with ID: {}", id);
            redirectAttributes.addFlashAttribute("message", "Student not found!");
            redirectAttributes.addFlashAttribute("alertType", "danger");
            return "redirect:/students/list";
        }
    }

    /**
     * Update an existing student
     */
    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute Student student, RedirectAttributes redirectAttributes) {
        try {
            log.info("Updating student with ID: {}", id);
            studentService.updateStudent(id, student);
            redirectAttributes.addFlashAttribute("message", "Student updated successfully!");
            redirectAttributes.addFlashAttribute("alertType", "success");
            return "redirect:/students/list";
        } catch (IllegalArgumentException e) {
            log.error("Error updating student: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("alertType", "danger");
            return "redirect:/students/edit/" + id;
        } catch (RuntimeException e) {
            log.error("Student not found while updating: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("message", "Student not found!");
            redirectAttributes.addFlashAttribute("alertType", "danger");
            return "redirect:/students/list";
        } catch (Exception e) {
            log.error("Unexpected error while updating student", e);
            redirectAttributes.addFlashAttribute("message", "An error occurred while updating the student");
            redirectAttributes.addFlashAttribute("alertType", "danger");
            return "redirect:/students/edit/" + id;
        }
    }

    /**
     * Delete a student
     */
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            log.info("Deleting student with ID: {}", id);
            studentService.deleteStudent(id);
            redirectAttributes.addFlashAttribute("message", "Student deleted successfully!");
            redirectAttributes.addFlashAttribute("alertType", "success");
            return "redirect:/students/list";
        } catch (RuntimeException e) {
            log.error("Student not found while deleting: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("message", "Student not found!");
            redirectAttributes.addFlashAttribute("alertType", "danger");
            return "redirect:/students/list";
        } catch (Exception e) {
            log.error("Unexpected error while deleting student", e);
            redirectAttributes.addFlashAttribute("message", "An error occurred while deleting the student");
            redirectAttributes.addFlashAttribute("alertType", "danger");
            return "redirect:/students/list";
        }
    }

    /**
     * REST API - Get all students as JSON
     */
    @GetMapping("/api/all")
    @ResponseBody
    public List<Student> getAllStudentsApi() {
        log.info("API: Fetching all students");
        return studentService.getAllStudents();
    }

    /**
     * REST API - Get student by ID
     */
    @GetMapping("/api/{id}")
    @ResponseBody
    public Optional<Student> getStudentApi(@PathVariable Long id) {
        log.info("API: Fetching student with ID: {}", id);
        return studentService.getStudentById(id);
    }

    /**
     * REST API - Create new student
     */
    @PostMapping("/api/create")
    @ResponseBody
    public Student createStudentApi(@RequestBody Student student) {
        log.info("API: Creating new student with email: {}", student.getEmail());
        return studentService.addStudent(student);
    }

    /**
     * REST API - Update student
     */
    @PutMapping("/api/update/{id}")
    @ResponseBody
    public Student updateStudentApi(@PathVariable Long id, @RequestBody Student student) {
        log.info("API: Updating student with ID: {}", id);
        return studentService.updateStudent(id, student);
    }

    /**
     * REST API - Delete student
     */
    @DeleteMapping("/api/delete/{id}")
    @ResponseBody
    public String deleteStudentApi(@PathVariable Long id) {
        log.info("API: Deleting student with ID: {}", id);
        studentService.deleteStudent(id);
        return "Student deleted successfully with ID: " + id;
    }

    /**
     * Root mapping - Redirect to home
     */
    @GetMapping("")
    public String root() {
        log.info("Accessing root URL, redirecting to home");
        return "redirect:/students/home";
    }
}
