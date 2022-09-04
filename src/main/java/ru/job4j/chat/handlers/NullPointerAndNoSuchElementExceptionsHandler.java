package ru.job4j.chat.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.NoSuchElementException;

@ControllerAdvice
public class NullPointerAndNoSuchElementExceptionsHandler {

    private final ObjectMapper objectMapper;

    public NullPointerAndNoSuchElementExceptionsHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ExceptionHandler(value = {NullPointerException.class, NoSuchElementException.class})
    public void handleException(Exception e, HttpServletResponse response) throws IOException {
        response.setStatus("NullPointerException".equals(e.getClass().getSimpleName())
                ? HttpStatus.CONFLICT.value() : HttpStatus.NOT_FOUND.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() {
            {
                put("message", e.getMessage());
            }
        }));
    }
}
