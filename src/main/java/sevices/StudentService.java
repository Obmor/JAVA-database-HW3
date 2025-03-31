package sevices;

import model.Faculty;
import model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentService {

    Student add(Student student);

    Student remove(Long id);

    Student update(Student student);

    Student get(Long id);

    Collection<Student> getByAge(Integer minAge, Integer maxAge);

    Collection<Student> getAll();

    Faculty getFacultyByStudent(Long studentId);

    List<String> getNamesStartingWithA();

    double getAverageAgeOfAllStudents();
}
