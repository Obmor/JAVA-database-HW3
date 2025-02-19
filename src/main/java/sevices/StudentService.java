package sevices;

import model.Faculty;
import model.Student;

import java.util.Collection;

public interface StudentService {

    Student add(Student student);

    Student remove(Long id);

    Student update(Student student);

    Student get(Long id);

    Collection<Student> getByAge(Integer minAge, Integer maxAge);

    Collection<Student> getAll();

    Faculty getFacultyByStudent(Long studentId);
}
