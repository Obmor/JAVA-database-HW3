package Impl;

import exceptions.NotFoundExceptions;
import model.Faculty;
import model.Student;
import repository.FacultyRepository;
import repository.StudentRepository;
import sevices.StudentService;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student add(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student remove(Long id) {
        Student student = get(id);
        studentRepository.deleteById(id);
        return student;
    }

    @Override
    public Student update(Student student) {
        Student existedStudent = get(student.getId());
        return studentRepository.save(student);
    }

    @Override
    public Student get(Long id) {
        Optional<Student> student = studentRepository.findById(id);

        if (student.isPresent()) {
            return student.get();
        } else {
            throw new NotFoundExceptions();
        }
    }

    @Override
    public Collection<Student> getByAge(Integer age) {
        if (age <= 10 || age >= 80) {
            throw new IllegalArgumentException("INCORRECT STUDENT AGE");
        }
        return getAll().stream()
                .filter(e -> e.getAge().equals(age))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Student> getAll() {
        return studentRepository.findAll();
    }
}
