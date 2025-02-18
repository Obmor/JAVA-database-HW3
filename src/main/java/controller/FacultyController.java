package controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import model.Faculty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sevices.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("faculties")
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
        Faculty updateFaculty = facultyService.add(faculty);
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
}
