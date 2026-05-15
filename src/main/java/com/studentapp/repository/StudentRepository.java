package com.studentapp.repository;

import com.studentapp.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Student Repository Interface
 * Extends JpaRepository to provide CRUD operations and custom query methods
 * Uses Spring Data JPA for database operations
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    /**
     * Find a student by email address
     *
     * @param email the email address to search for
     * @return Optional containing the student if found
     */
    Optional<Student> findByEmail(String email);

    /**
     * Find all students enrolled in a specific course
     *
     * @param course the course name to search for
     * @return List of students enrolled in the course
     */
    List<Student> findByCourse(String course);

    /**
     * Find students by name using LIKE query (case-insensitive)
     *
     * @param name the name pattern to search for
     * @return List of students matching the name pattern
     */
    @Query("SELECT s FROM Student s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Student> searchByName(@Param("name") String name);

    /**
     * Check if a student exists by email
     *
     * @param email the email to check
     * @return true if student exists, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Count students in a specific course
     *
     * @param course the course name
     * @return count of students in the course
     */
    long countByCourse(String course);
}
