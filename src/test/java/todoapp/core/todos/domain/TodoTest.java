package todoapp.core.todos.domain;

import org.junit.Test;
import todoapp.core.todos.application.TodoCreationException;

import static org.junit.Assert.*;

public class TodoTest {

    @Test(expected = TodoCreationException.class)
    public void 제목이_4자_이상이여야_한다() {
        //when
        Todo.create("세글자");

        //then
        fail("제목이 4자 미만이면 예외가 발생해야 한다");
    }
}