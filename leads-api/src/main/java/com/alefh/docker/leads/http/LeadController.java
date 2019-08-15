package com.alefh.docker.leads.http;

import com.alefh.docker.leads.domain.Lead;
import com.alefh.docker.leads.repository.LeadRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("leads")
@AllArgsConstructor
public class LeadController {

    private LeadRepository repository;

    @GetMapping
    public ResponseEntity<List<Lead>> all() {
        return ResponseEntity.ok(repository.findAll());
    }
}
