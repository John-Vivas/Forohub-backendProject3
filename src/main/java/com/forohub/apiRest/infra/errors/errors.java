package com.forohub.apiRest.infra.errors;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class errors {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity thereError404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity thereError400(MethodArgumentNotValidException e) {
        var errores = e.getFieldErrors().stream().map(ErrorDateValidation::new).toList();
        return ResponseEntity.badRequest().body(errores);
    }

    public record ErrorDateValidation(String campo, String error) {
        public ErrorDateValidation(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
