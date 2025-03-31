package controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import model.Faculty;
import model.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sevices.StudentService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("students")
@Tag(name = "Api: For Data Processing")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    @Operation(summary = "Student Creation")
    public ResponseEntity<Student> create(@RequestBody Student student) {
        Student addedStudent = studentService.add(student);
        return ResponseEntity.ok(addedStudent);
    }

    @PutMapping
    @Operation(summary = "Student Update")
    @ApiResponse(responseCode = "404", description = "Incorrect Request")
    public ResponseEntity<Student> update(@RequestBody Student student) {
        Student updatedStudent = studentService.update(student);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping({"{id}"})
    @ApiResponse(responseCode = "404", description = "Incorrect Request")
    @Operation(summary = "Student Delete")
    public ResponseEntity<Student> delete(@PathVariable Long id) {
        Student deletedStudent = studentService.remove(id);
        return ResponseEntity.ok(deletedStudent);
    }

    @GetMapping({"{id}"})
    @ApiResponse(responseCode = "404", description = "Incorrect Request")
    @Operation(summary = "Getting Student By ID")
    public ResponseEntity<Student> get(@PathVariable Long id) {
        Student student = studentService.get(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping({"all"})
    @Operation(summary = "Getting All Students List")
    public ResponseEntity<Collection> getAll() {
        Collection<Student> students = studentService.getAll();
        return ResponseEntity.ok(students);
    }

    @GetMapping("age")
    @Operation(summary = "Getting All Students List By Age")
    public ResponseEntity<Collection> getByAge(@RequestParam Integer minAge, @RequestParam Integer maxAge) {
        Collection<Student> students = studentService.getByAge(minAge, maxAge);
        return ResponseEntity.ok(students);
    }

    @GetMapping("faculty/{studentId}")
    @Operation(summary = "Getting Faculty By Student ID")
    public ResponseEntity<Faculty> getFacultyByStudent(@PathVariable Long studentId) {
        Faculty faculty = studentService.getFacultyByStudent(studentId);
        return ResponseEntity.ok(faculty);
    }

    @GetMapping("/names-starting-with-a")
    public List<String> getNamesStartingWithA() {
        return studentService.getNamesStartingWithA();
    }

    @GetMapping("/average-age")
    public double getAverageAgeOfAllStudents() {
        return studentService.getAverageAgeOfAllStudents();
    }
}
