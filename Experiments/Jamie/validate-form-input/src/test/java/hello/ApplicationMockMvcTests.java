package hello;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationMockMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void baseCase() throws Exception {
        MockHttpServletRequestBuilder createPerson = post("/")
                .param("name", "Jamie")
                .param("age", "20");

        mockMvc.perform(createPerson)
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void nameMissing() throws Exception {
        MockHttpServletRequestBuilder createPerson = post("/")
                .param("age", "21");

        mockMvc.perform(createPerson)
                .andExpect(model().hasErrors());
    }

    @Test
    public void nameError() throws Exception {
        MockHttpServletRequestBuilder createPerson = post("/")
                .param("name", "J")
                .param("age", "20");

        mockMvc.perform(createPerson)
                .andExpect(model().hasErrors());
    }

    @Test
    public void ageMissing() throws Exception {
        MockHttpServletRequestBuilder createPerson = post("/")
                .param("name", "Jamie");

        mockMvc.perform(createPerson)
                .andExpect(model().hasErrors());
    }

    @Test
    public void ageError() throws Exception {
        MockHttpServletRequestBuilder createPerson = post("/")
                .param("age", "17")
                .param("name", "Jamie");

        mockMvc.perform(createPerson)
                .andExpect(model().hasErrors());
    }
}
