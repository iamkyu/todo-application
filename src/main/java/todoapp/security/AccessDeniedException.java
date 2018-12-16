package todoapp.security;

import todoapp.commons.SystemException;

public class AccessDeniedException extends SystemException {

    public AccessDeniedException() {
        super("접근을 거부합니다.");
    }

}
