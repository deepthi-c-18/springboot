package com.studentapp.service;

import com.studentapp.entity.Student;
import com.studentapp.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit Tests for StudentService
 * Tests business logic of student operations
 */
@DisplayName("StudentService Tests")
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student testStudent;
    private List<Student> studentList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize test data
        testStudent = new Student();
        testStudent.setId(1L);
        testStudent.setName("John Doe");
        testStudent.setEmail("john@example.com");
        testStudent.setCourse("Java");
        testStudent.setCreatedAt(LocalDateTime.now());
        testStudent.setUpdatedAt(LocalDateTime.now());

        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("Jane Smith");
        student2.setEmail("jane@example.com");
        student2.setCourse("Spring Boot");

        studentList = Arrays.asList(testStudent, student2);
    }

    @Test
    @DisplayName("Should retrieve all students")
    void testGetAllStudents() {
        // Arrange
        when(studentRepository.findAll()).thenReturn(studentList);

        // Act
        List<Student> result = studentService.getAllStudents();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should retrieve student by ID")
    void testGetStudentById() {
        // Arrange
        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));

        // Act
        Optional<Student> result = studentService.getStudentById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getName());
        assertEquals("john@example.com", result.get().getEmail());
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should return empty when student not found")
    void testGetStudentByIdNotFound() {
        // Arrange
        when(studentRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<Student> result = studentService.getStudentById(999L);

        // Assert
        assertTrue(result.isEmpty());
        verify(studentRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Should add a new student")
    void testAddStudent() {
        // Arrange
        Student newStudent = new Student();
        newStudent.setName("New Student");
        newStudent.setEmail("new@example.com");
        newStudent.setCourse("Docker");

        when(studentRepository.existsByEmail("new@example.com")).thenReturn(false);
        when(studentRepository.save(newStudent)).thenReturn(testStudent);

        // Act
        Student result = studentService.addStudent(newStudent);

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(studentRepository, times(1)).existsByEmail("new@example.com");
        verify(studentRepository, times(1)).save(newStudent);
    }

    @Test
    @DisplayName("Should throw exception when adding duplicate email")
    void testAddStudentDuplicateEmail() {
        // Arrange
        Student newStudent = new Student();
        newStudent.setEmail("existing@example.com");

        when(studentRepository.existsByEmail("existing@example.com")).thenReturn(true);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            studentService.addStudent(newStudent);
        });

        verify(studentRepository, times(1)).existsByEmail("existing@example.com");
        verify(studentRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should update an existing student")
    void testUpdateStudent() {
        // Arrange
        Student updatedStudent = new Student();
        updatedStudent.setName("John Smith");
        updatedStudent.setEmail("john.smith@example.com");
        updatedStudent.setCourse("Spring Boot");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));
        when(studentRepository.existsByEmail("john.smith@example.com")).thenReturn(false);
        when(studentRepository.save(testStudent)).thenReturn(testStudent);

        // Act
        Student result = studentService.updateStudent(1L, updatedStudent);

        // Assert
        assertNotNull(result);
        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).existsByEmail("john.smith@example.com");
        verify(studentRepository, times(1)).save(testStudent);
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent student")
    void testUpdateStudentNotFound() {
        // Arrange
        when(studentRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            studentService.updateStudent(999L, testStudent);
        });

        verify(studentRepository, times(1)).findById(999L);
        verify(studentRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should delete a student")
    void testDeleteStudent() {
        // Arrange
        when(studentRepository.existsById(1L)).thenReturn(true);

        // Act
        studentService.deleteStudent(1L);

        // Assert
        verify(studentRepository, times(1)).existsById(1L);
        verify(studentRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent student")
    void testDeleteStudentNotFound() {
        // Arrange
        when(studentRepository.existsById(999L)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            studentService.deleteStudent(999L);
        });

        verify(studentRepository, times(1)).existsById(999L);
        verify(studentRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Should search students by name")
    void testSearchStudentsByName() {
        // Arrange
        when(studentRepository.searchByName("John")).thenReturn(Arrays.asList(testStudent));

        // Act
        List<Student> result = studentService.searchStudentsByName("John");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
        verify(studentRepository, times(1)).searchByName("John");
    }

    @Test
    @DisplayName("Should get students by course")
    void testGetStudentsByCourse() {
        // Arrange
        when(studentRepository.findByCourse("Java")).thenReturn(Arrays.asList(testStudent));

        // Act
        List<Student> result = studentService.getStudentsByCourse("Java");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Java", result.get(0).getCourse());
        verify(studentRepository, times(1)).findByCourse("Java");
    }

    @Test
    @DisplayName("Should get total student count")
    void testGetTotalStudentCount() {
        // Arrange
        when(studentRepository.count()).thenReturn(2L);

        // Act
        long result = studentService.getTotalStudentCount();

        // Assert
        assertEquals(2L, result);
        verify(studentRepository, times(1)).count();
    }

    @Test
    @DisplayName("Should get student count by course")
    void testGetStudentCountByCourse() {
        // Arrange
        when(studentRepository.countByCourse("Java")).thenReturn(1L);

        // Act
        long result = studentService.getStudentCountByCourse("Java");

        // Assert
        assertEquals(1L, result);
        verify(studentRepository, times(1)).countByCourse("Java");
    }

    @Test
    @DisplayName("Should get student by email")
    void testGetStudentByEmail() {
        // Arrange
        when(studentRepository.findByEmail("john@example.com")).thenReturn(Optional.of(testStudent));

        // Act
        Optional<Student> result = studentService.getStudentByEmail("john@example.com");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("john@example.com", result.get().getEmail());
        verify(studentRepository, times(1)).findByEmail("john@example.com");
    }
}
