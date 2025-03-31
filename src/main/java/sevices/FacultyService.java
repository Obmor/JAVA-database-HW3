package sevices;

import model.Faculty;
import model.Student;

import java.util.Collection;

public interface FacultyService {

    Faculty add(Faculty faculty);

    Faculty remove(Long id);

    Faculty update(Faculty faculty);

    Faculty get(Long id);

    Collection<Faculty> getByColor(String color);

    Collection<Faculty> getAll();

    Collection<Faculty> getByNameOrColor(String name, String color);

    Collection<Student> getStudents(Long facultyId);

    String getLongestFacultyName();
}
