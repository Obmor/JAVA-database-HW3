package Impl;

import exceptions.ArgumentExceptions;
import exceptions.NotFoundExceptions;
import model.Faculty;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import repository.FacultyRepository;
import sevices.FacultyService;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty add(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty remove(Long id) {
        Faculty faculty = get(id);
        facultyRepository.deleteById(id);
        return faculty;
    }

    @Override
    public Faculty update(Faculty faculty) {
        Faculty existedFaculty = get(faculty.getId());
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty get(Long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);

        if (faculty.isPresent()) {
            return faculty.get();
        } else {
            throw new NotFoundExceptions();
        }
    }

    @Override
    public Collection<Faculty> getByColor(String color) {
        if (!StringUtils.hasText(color)) {
            throw new ArgumentExceptions("Incorrect Color");
        }
        return getAll().stream()
                .filter(f -> f.getColor().equals(color))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Faculty> getAll() {
        return facultyRepository.findAll();
    }
}
