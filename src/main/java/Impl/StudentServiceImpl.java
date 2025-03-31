package Impl;

import exceptions.NotFoundExceptions;
import model.Faculty;
import model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.StudentRepository;
import sevices.StudentService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StudentServiceImpl implements StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student add(Student student) {
        logger.info("It was invoked method for add student: {}", student);
        return studentRepository.save(student);
    }

    @Override
    public Student remove(Long id) {
        logger.info("Was invoked method for delete student by id: {}", id);
        Student student = get(id);
        studentRepository.deleteById(id);
        return student;
    }

    @Override
    public Student update(Student student) {
        logger.info("Was invoked method for update student: {}", student);
        Student existedStudent = get(student.getId());
        return studentRepository.save(student);
    }

    @Override
    public Student get(Long id) {
        logger.info("It was invoked method for get students by id: {}", id);
        return studentRepository.findById(id).orElseThrow(() -> {
            logger.error("There are no student with id = {}", id);
            return new NotFoundExceptions();
        });
    }

    @Override
    public Collection<Student> getByAge(Integer minAge, Integer maxAge) {
        logger.info("Was invoked method for get students by age between: {} and {}", minAge, maxAge);
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    @Override
    public Collection<Student> getAll() {
        logger.info("Was invoked method for get all students");
        return studentRepository.findAll();
    }

    @Override
    public Faculty getFacultyByStudent(Long studentId) {
        logger.info("It was invoked method to get faculty by students");
        return get(studentId).getFaculty();
    }

    public List<String> getNamesStartingWithA() {
        logger.info("It was invoked method to get name started with 'A'");
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .filter(name -> name.startsWith("A"))
                .map(String::toUpperCase)
                .sorted()
                .collect(Collectors.toList());
    }

    public double getAverageAgeOfAllStudents() {
        logger.info("It was invoked method to get average age");
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0.0);
    }
}
