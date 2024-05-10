package com.example.sweets;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.Map;

@RestController
public class SweetController {
    private SweetService sweetService;
    public SweetController(SweetService sweetService) {
        this.sweetService = sweetService;
    }

    @GetMapping("/sweets/{id}")
    public Sweet getSweet(@PathVariable("id") int id) {
        return sweetService.findSweet(id);
    }

    @ExceptionHandler(value = SweetNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleSweetNotFoundException(
        SweetNotFoundException e, HttpServletRequest request) {
            Map<String, String> body = Map.of(
                "timestamp", ZonedDateTime.now().toString(),
                "status", String.valueOf(HttpStatus.NOT_FOUND.value()),
                "error", HttpStatus.NOT_FOUND.getReasonPhrase(),
                "message", e.getMessage(),
                "path", request.getRequestURI());
            return new ResponseEntity(body, HttpStatus.NOT_FOUND);
        }
}
