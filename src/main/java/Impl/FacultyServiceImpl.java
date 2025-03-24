package Impl;

import exceptions.ArgumentExceptions;
import exceptions.NotFoundExceptions;
import model.Faculty;
import model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import repository.FacultyRepository;
import sevices.FacultyService;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {

    private static final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository facultyRepository;


    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty add(Faculty faculty) {
        logger.info("It was invoked method for add faculty: {}", faculty);
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty remove(Long id) {
        logger.info("Was invoked method for remove faculty by id: {}", id);
        Faculty faculty = get(id);
        facultyRepository.deleteById(id);
        return faculty;
    }

    @Override
    public Faculty update(Faculty faculty) {
        logger.info("Was invoked method for update faculty: {}", faculty);
        Faculty existedFaculty = get(faculty.getId());
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty get(Long id) {
        logger.info("It was invoked method for get faculty by id: {}", id);
        return facultyRepository.findById(id).orElseThrow(() -> {
            logger.error("There are no faculty with id = {}", id);
            return new NotFoundExceptions();
        });
    }

    @Override
    public Collection<Faculty> getByColor(String color) {
        logger.info("It was invoked method for get all faculties by color");
        if (!StringUtils.hasText(color)) {
            throw new ArgumentExceptions("Incorrect Color");
        }
        return getAll().stream()
                .filter(f -> f.getColor().equals(color))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Faculty> getAll() {
        logger.info("It was invoked method for get all faculties");
        return facultyRepository.findAll();
    }

    @Override
    public Collection<Faculty> getByNameOrColor(String name, String color) {
        logger.info("It was invoked method for get faculty by name or color: {} or {}", color, name);
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    @Override
    public Collection<Student> getStudents(Long facultyId) {
        logger.info("It was invoked method for get all students");
        return get(facultyId).getStudents();
    }
}
