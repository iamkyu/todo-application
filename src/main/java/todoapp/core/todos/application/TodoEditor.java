package todoapp.core.todos.application;

import todoapp.core.todos.domain.Todo;

/**
 * 할 일 편집기 인터페이스
 */
public interface TodoEditor {

    /**
     * 새로운 할 일 을 등록한다.
     *
     * @param title 할 일
     * @return 생성된 할 일 개체
     */
    Todo create(String title);

    /**
     * 해당 할 일 번호로 등록된 할 일을 변경한다.
     *
     * @param id        할 일 번호
     * @param title     할 일
     * @param completed 완료여부
     * @return 변경된 할 일 개체
     */
    Todo update(Long id, String title, boolean completed);

    /**
     * 해당 할 일 번호로 등록된 할 일을 삭제한다.
     *
     * @param id 할 일 번호
     * @return 삭제된 할 일 개체
     */
    Todo delete(Long id);

}