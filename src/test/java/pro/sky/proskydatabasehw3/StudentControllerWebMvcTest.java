package pro.sky.proskydatabasehw3;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.StudentController;
import model.Faculty;
import model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import sevices.StudentService;

import static java.nio.file.Paths.get;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
public class StudentControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getStudentTest() throws Exception {
        Student student = new Student(1L, "Student1", 18);
        given(studentService.get(1L)).willReturn(student);

        mockMvc.perform(get("/student?id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Student1"))
                .andExpect(jsonPath("$.age").value(18));
    }

    @Test
    public void addStudentTest() throws Exception {
        Student newStudent = new Student(null, "Student2", 19);
        Student savedStudent = new Student(1L, "Student2", 19);
        given(studentService.add(newStudent)).willReturn(savedStudent);

        mockMvc.perform(post("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newStudent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Student2"))
                .andExpect(jsonPath("$.id").value(19));
    }

    @Test
    public void removeStudentTest() throws Exception {
        given(studentService.delete(1L)).willReturn(true);

        mockMvc.perform(delete("/student?id=1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void updateStudentTest() throws Exception {
        Student updatedStudent = new Student(1L, "Student3", 20);
        given(studentService.update(updatedStudent)).willReturn(updatedStudent);

        mockMvc.perform(put("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedStudent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Student3"))
                .andExpect(jsonPath("$.age").value(20));
    }

    @Test
    public void getByAgeBetweenTest() throws Exception {
        mockMvc.perform(get("/student/byAge?min=15&max=20"))
                .andExpect(status().isOk());
    }

    @Test
    public void getStudentByFacultyTest() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName("first");
        faculty.setColor("any");

        Student student = new Student();
        student.setId(1L);
        student.setName("Student1");
        student.setAge(18);
        student.setFaculty(faculty);

        given(studentService.get(1L)).willReturn(student);

        mockMvc.perform(get("/student/faculty?studentId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("first"))
                .andExpect(jsonPath("$.color").value("any"));
    }
}
