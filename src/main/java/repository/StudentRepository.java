package repository;

import model.Student;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

//public interface StudentRepository extends JpaRepository<Student, Long> {
//    Collection<Student> findAllByAge(int age);
//}

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAgeBetween(Integer minAge, Integer maxAge);
}