package controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import model.Faculty;
import model.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sevices.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("faculties")
@Tag(name = "Api: For Data Processing")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    @Operation(summary = "Faculty Creation")
    public ResponseEntity<Faculty> create(@RequestBody Faculty faculty) {
        Faculty addFaculty = facultyService.add(faculty);
        return ResponseEntity.ok(addFaculty);
    }

    @PutMapping
    @ApiResponse(responseCode = "404", description = "Invalid Request")
    @Operation(summary = "Faculty Update")
    public ResponseEntity<Faculty> update(@RequestBody Faculty faculty) {
        Faculty updateFaculty = facultyService.update(faculty);
        return ResponseEntity.ok(updateFaculty);
    }

    @DeleteMapping({"{id}"})
    @ApiResponse(responseCode = "404", description = "Incorrect Request")
    @Operation(summary = "Delete Faculty")
    public ResponseEntity<Faculty> delete(@PathVariable Long id) {
        Faculty deletedFaculty = facultyService.remove(id);
        return ResponseEntity.ok(deletedFaculty);
    }

    @GetMapping({"{id}"})
    @ApiResponce(responseCode = "404", description = "Incorrect Request")
    @Operation(summary = "Getting Faculty By ID")
    public ResponseEntity<Faculty> get(@PathVariable Long id) {
        Faculty faculty = facultyService.get(id);
        return ResponseEntity.ok(faculty);
    }

    @GetMapping({"all"})
    @Operation(summary = "Getting All Faculties")
    public ResponseEntity<Collection<Faculty>> getAll() {
        Collection<Faculty> faculties = facultyService.getAll();
        return ResponseEntity.ok(faculties);
    }

    @GetMapping("color")
    @Operation(summary = "Getting Faculty By Color")
    public ResponseEntity<Collection<Faculty>> getByColor(@RequestParam String color) {
        Collection<Faculty> faculties = facultyService.getByColor(color);
        return ResponseEntity.ok(faculties);
    }

    @GetMapping("get-by-name-or-color/{name-color}")
    @Operation(summary = "Getting Faculty By Color Or Name")
    public ResponseEntity<Collection<Faculty>> getByNameOrColor(@RequestParam String name, @RequestParam String color) {
        Collection<Faculty> faculties = facultyService.getByNameOrColor(name, color);
        return ResponseEntity.ok(faculties);
    }

    @GetMapping("student/{facultyId}")
    @Operation(summary = "Getting All Students By Faculty ID")
    public ResponseEntity<Collection<Student>> getStudentsByFaculty(@PathVariable Long facultyId) {
        Collection<Student> students = facultyService.getStudents(facultyId);
        return ResponseEntity.ok(students);
    }
}
