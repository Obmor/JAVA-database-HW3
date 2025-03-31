package pro.sky.proskydatabasehw3;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.FacultyController;
import model.Faculty;
import model.Student;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import repository.FacultyRepository;
import sevices.AvatarService;
import sevices.FacultyService;
import sevices.StudentService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class FacultyControllerTestWebMvc {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    FacultyRepository facultyRepository;

    @MockitoSpyBean
    FacultyService facultyService;

    @MockitoBean
    StudentService studentService;

    @MockitoBean
    AvatarService avatarService;

    @InjectMocks
    FacultyController controller;

    @Test
    void getTest() throws Exception {
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(new Faculty(1L,
                "faculty_mvc_test", "color_mvc_test")));

        mvc.perform(MockMvcRequestBuilders.get("/faculty?id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("faculty_mvc_test"))
                .andExpect(jsonPath("$.color").value("color_mvc_test"));
    }

    @Test
    void updateTest() throws Exception {
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(new Faculty(1L,
                "faculty_mvc_test", "color_mvc_test")));
        Faculty faculty = new Faculty(1L, "name_update", "color_update");
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        ObjectMapper objectMapper = new ObjectMapper();
        mvc.perform(MockMvcRequestBuilders.put("/faculty?id=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(faculty)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("name_update"))
                .andExpect(jsonPath("$.color").value("color_update"));
    }

    @Test
    void removeTest() throws Exception {
        when(facultyRepository.findById(2L)).thenReturn(Optional.of(new Faculty(1L, "faculty_mvc_test",
                "color_mvc_test")));

        mvc.perform(MockMvcRequestBuilders.delete("/faculty?id=2"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void addTest() throws Exception {
        when(facultyRepository.save(any(Faculty.class))).then(invocationOnMock -> {
            Faculty input = invocationOnMock.getArgument(0, Faculty.class);
            Faculty f = new Faculty();
            f.setId(100L);
            f.setColor(input.getColor());
            f.setName(input.getName());
            return f;
        });

        Faculty faculty = new Faculty(null, "first", "any");

        ObjectMapper objectMapper = new ObjectMapper();
        mvc.perform(MockMvcRequestBuilders.post("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(faculty)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(42L))
                .andExpect(jsonPath("$.name").value("first"))
                .andExpect(jsonPath("$.color").value("any"));
    }

    @Test
    void getByNameOrColorTest() throws Exception {
        when(facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(anyString(), anyString()))
                .thenReturn(List.of(
                        new Faculty(1L, "name1", "color1"),
                        new Faculty(2L, "name2", "color2")));

        mvc.perform(MockMvcRequestBuilders.get("/faculty/byColorAndName?name=name1&color=color2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("name1"))
                .andExpect(jsonPath("$[0].color").value("color1"))
                .andExpect(jsonPath("$[1].name").value("name2"))
                .andExpect(jsonPath("$[1].color").value("color2"));
    }

    @Test
    void getStudentsTest() throws Exception {
        Faculty f = new Faculty(1L, "first", "any1");
        f.setStudents(List.of(new Student(1L, "student1", 18)));

        when(facultyRepository.findById(1L)).thenReturn(Optional.of(f));

        mvc.perform(MockMvcRequestBuilders.get("/faculty/students?facultyId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("student1"))
                .andExpect(jsonPath("$[0].age").value(18));
    }
}
