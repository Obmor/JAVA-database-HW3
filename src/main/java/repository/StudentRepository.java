package repository;

import model.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;


public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAgeBetween(Integer minAge, Integer maxAge);

    @Query(value = "Count of Students", nativeQuery = true)
    int countStudents();

    @Query(value = "Average age of Students", nativeQuery = true)
    double avgAge();

    @Query(value = "Last 5 Students", nativeQuery = true)
    Collection<Student> getLastFive();
}