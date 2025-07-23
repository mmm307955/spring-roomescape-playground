package roomescape.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException {

    private final String code;
    private final Map<String, Object> arguments;
    private final HttpStatus httpStatus;

    public BusinessException(String code, String message, HttpStatus httpStatus) {
        super(message);
        this.code = code;
        this.arguments = new HashMap<>();
        this.httpStatus = httpStatus;
    }

    protected void addArgument(String key, Object value) {
        arguments.put(key, value);
    }

    public String getCode() {
        return code;
    }

    public Map<String, Object> getArguments() {
        return arguments;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
