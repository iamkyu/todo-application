package todoapp.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import todoapp.commons.util.StreamUtils;
import todoapp.core.todos.application.WriteTodoCommand;
import todoapp.core.todos.domain.Todo;
import todoapp.core.todos.domain.TodoRepository;
import todoapp.core.todos.domain.TodoState;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TodoRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private TodoRepository todoRepository;

    private Todo sampleTask;

    @Before
    public void setUp() {
        sampleTask = Todo.create("Coding");
        todoRepository.save(sampleTask);
    }

    @Test
    public void getTodos_200() throws Exception {
        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("[0].title").value(sampleTask.getTitle()))
                .andExpect(jsonPath("[0].state").value(sampleTask.getState().getLiteral().toUpperCase()))
                .andDo(print());
    }

    @Test
    public void addTodo_201() throws Exception {
        //given
        List<Todo> before = StreamUtils
                .createStreamFromIterator(todoRepository.findAll().iterator())
                .collect(toList());

        WriteTodoCommand command = new WriteTodoCommand("task one");

        //when
        mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(command)))
                .andExpect(status().isCreated())
                .andDo(print());

        List<Todo> after = StreamUtils
                .createStreamFromIterator(todoRepository.findAll().iterator())
                .collect(toList());

        //then
        assertThat(after.size()).isEqualTo(before.size() + 1);
    }

    @Test
    public void wrongTitle_400() throws Exception {
        //given
        WriteTodoCommand wrongCommand = new WriteTodoCommand("abc");

        //when then
        mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(wrongCommand)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void editTodo_200() throws Exception {
        //given
        WriteTodoCommand updateCommand = new WriteTodoCommand("new title");
        updateCommand.setCompleted(true);

        //when
        mockMvc.perform(put("/api/todos/{id}", sampleTask.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(updateCommand)))
                .andExpect(status().isOk())
                .andDo(print());

        //then
        Todo updated = todoRepository.findById(sampleTask.getId()).get();
        assertThat(updated.getTitle()).isEqualTo(updateCommand.getTitle());
        assertThat(updated.getState().getLiteral()).isEqualTo(TodoState.COMPLETED.getLiteral());
    }

    @Test
    public void deleteTodo_200() throws Exception {
        //given when
        mockMvc.perform(delete("/api/todos/{id}", sampleTask.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andDo(print());

        //then
        Optional<Todo> delete = todoRepository.findById(sampleTask.getId());
        assertThat(delete.isPresent()).isFalse();
    }
}