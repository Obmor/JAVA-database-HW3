package pro.sky.proskydatabasehw3;

import model.Faculty;
import model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTestRestTemplate {

    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate template;

    @Test
    void getFacultyTest() throws Exception {
        Faculty faculty = new Faculty(null, "faculty_test", "color_test");
        ResponseEntity<Faculty> postResponse = template.postForEntity("/faculty", faculty, Faculty.class);
        Faculty addedFaculty = postResponse.getBody();

        var result = template.getForObject("http://localhost:" + port + "/faculty?id="
                + addedFaculty.getId(), Faculty.class);
        assertThat(result.getColor()).isEqualTo("color_test");
        assertThat(result.getName()).isEqualTo("faculty_test");

        ResponseEntity<Faculty> resultAfterDelete = template.exchange("/faculty?id=-1",
                HttpMethod.GET, null, Faculty.class);
        assertThat(resultAfterDelete.getStatusCode().value()).isEqualTo(404);
    }

    @Test
    void removeTest() {
        Faculty faculty = new Faculty(null, "faculty_test1", "color_test");
        ResponseEntity<Faculty> postResponse = template.postForEntity("/faculty", faculty, Faculty.class);
        Faculty addedFaculty = postResponse.getBody();

        var result = template.getForObject("http://localhost:" + port + "/faculty?id="
                + addedFaculty.getId(), Faculty.class);
        assertThat(result.getColor()).isEqualTo("color_test");
        assertThat(result.getName()).isEqualTo("faculty_test");

        template.delete("/faculty?id=" + addedFaculty.getId());

        ResponseEntity<Faculty> resultAfterDelete = template.exchange("/faculty?id="
                + addedFaculty.getId(), HttpMethod.GET, null, Faculty.class);
        assertThat(resultAfterDelete.getStatusCode().value()).isEqualTo(404);
    }

    @Test
    void updateTest() {
        Faculty faculty = new Faculty(null, "faculty_test", "color_test");
        ResponseEntity<Faculty> postResponse = template.postForEntity("/faculty", faculty, Faculty.class);
        Faculty addedFaculty = postResponse.getBody();

        addedFaculty.setName("name_changing");
        addedFaculty.setColor("color_changing");
        template.put("/faculty?id=" + addedFaculty.getId(), addedFaculty);

        var result = template.getForObject("http://localhost:" + port + "/faculty?id="
                + addedFaculty.getId(), Faculty.class);
        assertThat(result.getName()).isEqualTo("name_changing");
        assertThat(result.getColor()).isEqualTo("color_changing");
    }

    @Test
    void filterTest() {
        var filter1 = template.postForEntity("/faculty", new Faculty(null,
                "name_test1", "color_test1"), Faculty.class).getBody();
        var filter2 = template.postForEntity("/faculty", new Faculty(null,
                "name_test2", "color_test2"), Faculty.class).getBody();
        var filter3 = template.postForEntity("/faculty", new Faculty(null,
                "name_test3", "color_test3"), Faculty.class).getBody();
        var filter4 = template.postForEntity("/faculty", new Faculty(null,
                "name_test4", "color_test4"), Faculty.class).getBody();

        var faculties = template.getForObject("/faculty/byColorAndName?name=name_test1&color=color_test2",
                Faculty[].class);
        assertThat(faculties.length).isEqualTo(4);
        assertThat(faculties).containsExactlyInAnyOrder(filter1, filter2, filter3, filter4);
    }

    @Test
    void getFacultyStudentsTest() {
        var f = new Faculty(null, "name_test1", "color_test1");
        var f1 = template.postForEntity("/faculty", f, Faculty.class).getBody();

        Student newStudent = new Student(null, "student_1", 18);
        Student newStudent2 = new Student(null, "student_2", 19);
        newStudent.setFaculty(f1);
        newStudent2.setFaculty(f1);
        var s1 = template.postForEntity("/student", newStudent, Student.class).getBody();
        var s2 = template.postForEntity("/student", newStudent2, Student.class).getBody();

        ResponseEntity<List<Student>> result = template.exchange("/faculty/students?facultyId=" + f1.getId(),
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });

        assertThat(result.getBody()).containsExactlyInAnyOrder(
                new Student(1L, "student_1", 18),
                new Student(2L, "student_2", 19),
                new Student(3L, "student_2", 20),
                new Student(4L, "student_2", 21));
    }
}
