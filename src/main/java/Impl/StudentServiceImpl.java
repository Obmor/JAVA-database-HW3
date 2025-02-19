package Impl;

import exceptions.NotFoundExceptions;
import model.Faculty;
import model.Student;
import repository.StudentRepository;
import sevices.StudentService;

import java.util.Collection;
import java.util.Optional;

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
    public Collection<Student> getByAge(Integer minAge, Integer maxAge) {
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    @Override
    public Collection<Student> getAll() {
        return studentRepository.findAll();
    }

    @Override
    public Faculty getFacultyByStudent(Long studentId) {
        return get(studentId).getFaculty();
    }
}
