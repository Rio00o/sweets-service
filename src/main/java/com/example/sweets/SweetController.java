package com.example.sweets;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


import java.net.URI;
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

    @PostMapping("/sweets")
    public ResponseEntity<SweetResponse> insert(@RequestBody SweetRequest sweetRequest, UriComponentsBuilder uriBuilder) {
        Sweet sweet = sweetService.insert(sweetRequest.getName(), sweetRequest.getCompany(), sweetRequest.getPrice(), sweetRequest.getPrefecture());
        URI location = uriBuilder.path("/sweets/{id}").buildAndExpand(sweet.getId()).toUri();
        SweetResponse body = new SweetResponse("sweet created");
        return ResponseEntity.created(location).body(body);
    }

    @PatchMapping("/sweets/{id}")
    public ResponseEntity<SweetResponse> update(@PathVariable("id") Integer id, @RequestBody SweetRequest sweetRequest) {
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
