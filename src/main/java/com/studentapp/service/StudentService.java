package com.studentapp.service;

import com.studentapp.entity.Student;
import com.studentapp.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Student Service Class
 * Implements business logic for Student operations
 * Uses @Transactional for transaction management
 * Uses Lombok @Slf4j for logging
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class StudentService {

    private final StudentRepository studentRepository;

    /**
     * Get all students
     *
     * @return List of all students
     */
    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        log.info("Fetching all students");
        return studentRepository.findAll();
    }

    /**
     * Get a student by ID
     *
     * @param id the student ID
     * @return Optional containing the student if found
     */
    @Transactional(readOnly = true)
    public Optional<Student> getStudentById(Long id) {
        log.info("Fetching student with ID: {}", id);
        return studentRepository.findById(id);
    }

    /**
     * Get a student by email
     *
     * @param email the student email
     * @return Optional containing the student if found
     */
    @Transactional(readOnly = true)
    public Optional<Student> getStudentByEmail(String email) {
        log.info("Fetching student with email: {}", email);
        return studentRepository.findByEmail(email);
    }

    /**
     * Get students by course
     *
     * @param course the course name
     * @return List of students in the course
     */
    @Transactional(readOnly = true)
    public List<Student> getStudentsByCourse(String course) {
        log.info("Fetching students for course: {}", course);
        return studentRepository.findByCourse(course);
    }

    /**
     * Search students by name (case-insensitive)
     *
     * @param name the name pattern to search
     * @return List of matching students
     */
    @Transactional(readOnly = true)
    public List<Student> searchStudentsByName(String name) {
        log.info("Searching students with name pattern: {}", name);
        return studentRepository.searchByName(name);
    }

    /**
     * Add a new student
     *
     * @param student the student to add
     * @return the saved student
     * @throws IllegalArgumentException if email already exists
     */
    public Student addStudent(Student student) {
        log.info("Adding new student with email: {}", student.getEmail());

        if (studentRepository.existsByEmail(student.getEmail())) {
            log.error("Email already exists: {}", student.getEmail());
            throw new IllegalArgumentException("Email already exists: " + student.getEmail());
        }

        Student savedStudent = studentRepository.save(student);
        log.info("Student added successfully with ID: {}", savedStudent.getId());
        return savedStudent;
    }

    /**
     * Update an existing student
     *
     * @param id the student ID
     * @param studentDetails the updated student details
     * @return the updated student
     * @throws RuntimeException if student not found
     */
    public Student updateStudent(Long id, Student studentDetails) {
        log.info("Updating student with ID: {}", id);

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Student not found with ID: {}", id);
                    return new RuntimeException("Student not found with ID: " + id);
                });

        // Check if new email is already taken by another student
        if (!student.getEmail().equals(studentDetails.getEmail()) &&
                studentRepository.existsByEmail(studentDetails.getEmail())) {
            log.error("Email already taken by another student: {}", studentDetails.getEmail());
            throw new IllegalArgumentException("Email already taken: " + studentDetails.getEmail());
        }

        student.setName(studentDetails.getName());
        student.setEmail(studentDetails.getEmail());
        student.setCourse(studentDetails.getCourse());

        Student updatedStudent = studentRepository.save(student);
        log.info("Student updated successfully with ID: {}", id);
        return updatedStudent;
    }

    /**
     * Delete a student
     *
     * @param id the student ID
     * @throws RuntimeException if student not found
     */
    public void deleteStudent(Long id) {
        log.info("Deleting student with ID: {}", id);

        if (!studentRepository.existsById(id)) {
            log.error("Student not found with ID: {}", id);
            throw new RuntimeException("Student not found with ID: " + id);
        }

        studentRepository.deleteById(id);
        log.info("Student deleted successfully with ID: {}", id);
    }

    /**
     * Count total students
     *
     * @return total count of students
     */
    @Transactional(readOnly = true)
    public long getTotalStudentCount() {
        long count = studentRepository.count();
        log.info("Total students count: {}", count);
        return count;
    }

    /**
     * Get count of students in a specific course
     *
     * @param course the course name
     * @return count of students in the course
     */
    @Transactional(readOnly = true)
    public long getStudentCountByCourse(String course) {
        long count = studentRepository.countByCourse(course);
        log.info("Students count for course {}: {}", course, count);
        return count;
    }
}
