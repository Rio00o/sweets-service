package com.example.sweets;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


import java.net.URI;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SweetController {
    private final SweetService sweetService;
    @Autowired
    public SweetController(SweetService sweetService) {
        this.sweetService = sweetService;
    }

    @GetMapping("/sweets/{id}")
    public Sweet getSweet(@PathVariable("id") int id) {
        return sweetService.findSweet(id);
    }

    @PostMapping("/sweets")
    public ResponseEntity<SweetResponse> insert(@RequestBody @Valid SweetRequest sweetRequest, UriComponentsBuilder uriBuilder) {
        Sweet sweet = sweetService.insert(sweetRequest.getName(), sweetRequest.getCompany(), sweetRequest.getPrice(), sweetRequest.getPrefecture());
        URI location = uriBuilder.path("/sweets/{id}").buildAndExpand(sweet.getId()).toUri();
        SweetResponse body = new SweetResponse("sweet created");
        return ResponseEntity.created(location).body(body);
    }

    @PatchMapping("/sweets/{id}")
    public ResponseEntity<SweetResponse> update(@PathVariable("id") Integer id, @RequestBody @Valid SweetRequest sweetRequest) {
        sweetService.update(id, sweetRequest.getName(), sweetRequest.getCompany(), sweetRequest.getPrice(), sweetRequest.getPrefecture());
        SweetResponse body = new SweetResponse("sweet updated");
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/sweets/{id}")
    public ResponseEntity<SweetResponse> delete(@PathVariable("id") Integer id) {
        sweetService.delete(id);
        SweetResponse body = new SweetResponse("sweet deleted");
        return ResponseEntity.ok(body);
    }


    @ExceptionHandler(value = SweetNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleSweetNotFoundException(SweetNotFoundException e, HttpServletRequest request) {
            Map<String, String> body = Map.of(
                "timestamp", ZonedDateTime.now().toString(),
                "status", String.valueOf(HttpStatus.NOT_FOUND.value()),
                "error", HttpStatus.NOT_FOUND.getReasonPhrase(),
                "message", e.getMessage(),
                "path", request.getRequestURI());
            return new ResponseEntity(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = SweetDuplicatedException.class)
    public ResponseEntity<Map<String, String>> handleSweetDuplicatedException(
            SweetDuplicatedException e, HttpServletRequest request){
        Map<String, String> body = Map.of(
                "timestamp", ZonedDateTime.now().toString(),
                "status", String.valueOf(HttpStatus.CONFLICT.value()),
                "error", HttpStatus.CONFLICT.getReasonPhrase(),
                "message", e.getMessage(),
                "path", request.getRequestURI());
        return new ResponseEntity(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(SweetValidationException.class)
    public ResponseEntity<Map<String, String>> handleSweetValidationException(SweetValidationException e) {
        Map<String, String> response = new HashMap<>();
        response.put("timestamp", ZonedDateTime.now().toString());
        response.put("status", String.valueOf(HttpStatus.BAD_REQUEST.value()));
        response.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        response.put("message", e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

}
