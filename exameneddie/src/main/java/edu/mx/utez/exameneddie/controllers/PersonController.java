package edu.mx.utez.exameneddie.controllers;

import edu.mx.utez.exameneddie.config.ApiResponse;
import edu.mx.utez.exameneddie.controllers.dto.PersonDto;
import jakarta.validation.Valid;
import edu.mx.utez.exameneddie.services.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/person")
@CrossOrigin(origins = {"*"})
public class PersonController {
    private final PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAll() {
        return service.findAll();
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody PersonDto dto){
        ResponseEntity<ApiResponse> save = service.save(dto.toEntity());
        return save;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        ResponseEntity<ApiResponse> response = service.delete(id);
        return response;
    }
}
