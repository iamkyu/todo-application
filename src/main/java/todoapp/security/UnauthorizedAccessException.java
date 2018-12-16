package todoapp.security;

import todoapp.commons.SystemException;

public class UnauthorizedAccessException extends SystemException {

    public UnauthorizedAccessException() {
        super("인증되지 않은 접근입니다.");
    }

}
