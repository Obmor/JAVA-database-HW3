package pro.sky.proskydatabasehw3;

import model.Faculty;
import model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTestRest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getStudentTest() {
        Student student = new Student(1L, "Student1", 18);
        restTemplate.postForEntity("http://localhost:" + port + "/student", student, Student.class);
        Student result = restTemplate.getForObject("http://localhost:" + port + "/student?id=1", Student.class);
        assertThat(result.getName()).isEqualTo("Student1");
        assertThat(result.getAge()).isEqualTo(18);
    }

    @Test
    public void addStudentTest() {
        Student student = new Student(null, "Student2", 19);
        ResponseEntity<Student> response = restTemplate.postForEntity("http://localhost:" + port
                + "/student", student, Student.class);
        Student result = response.getBody();
        assertThat(result.getName()).isEqualTo("Student");
        assertThat(result.getAge()).isEqualTo(19);
    }

    @Test
    public void updateStudentTest() {
        Student student = new Student(1L, "Student3", 20);
        restTemplate.postForEntity("http://localhost:" + port + "/student", student, Student.class);
        student.setName("Student3");
        restTemplate.put("http://localhost:" + port + "/student", student, Student.class);
        Student updatedStudent = restTemplate.getForObject("http://localhost:" + port
                + "/student?id=1", Student.class);
        assertThat(updatedStudent.getName()).isEqualTo("Student3");
    }

    @Test
    public void deleteStudentTest() {
        Student student = new Student(2L, "Student4", 21);
        restTemplate.postForEntity("http://localhost:" + port + "/student", student, Student.class);
        restTemplate.delete("http://localhost:" + port + "/student?id=2");
        try {
            restTemplate.getForObject("http://localhost:" + port + "/student?id=2", Student.class);
        } catch (HttpClientErrorException ex) {
            assertThat(ex.getStatusCode().value()).isEqualTo(404);
        }


    }

    @Test
    public void getByAgeBetweenTest() {
        restTemplate.postForEntity("http://localhost:" + port + "/student", new Student(null,
                "Student1", 18), Student.class);
        restTemplate.postForEntity("http://localhost:" + port + "/student", new Student(null,
                "Student2", 20), Student.class);
        Student[] results = restTemplate.getForObject("http://localhost:" + port
                + "/student/byAge?min=18&max=20", Student[].class);
        assertThat(results.length).isEqualTo(1);
        assertThat(results[0].getName()).isEqualTo("Student1");
    }

    @Test
    public void getStudentByFacultyTest() {
        Faculty faculty = new Faculty(null, "first", "any");
        ResponseEntity<Faculty> facultyResponse = restTemplate.postForEntity("http://localhost:" + port
                + "/faculty", faculty, Faculty.class);
        Faculty savedFaculty = facultyResponse.getBody();

        Student student = new Student();
        student.setName("Student1");
        student.setAge(18);
        student.setFaculty(savedFaculty);
        ResponseEntity<Student> studentResponse = restTemplate.postForEntity("http://localhost:" + port
                + "/student", student, Student.class);

        Faculty result = restTemplate.getForObject("http://localhost:" + port
                + "/student/faculty?studentId=" + studentResponse.getBody().getId(), Faculty.class);
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("first");
        assertThat(result.getColor()).isEqualTo("any");
    }
}
