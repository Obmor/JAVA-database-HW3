package repository;

import model.Faculty;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

//public interface FacultyRepository extends JpaRepository<Faculty, Long> {
//    Collection<Faculty> findFacultiesByColor(String color);
//}

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Collection<Faculty> findByNameIgnoreCaseOrColorIgnoreCase(String name, String color);
}