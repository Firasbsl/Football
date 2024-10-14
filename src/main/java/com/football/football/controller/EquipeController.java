package com.football.football.controller;

import com.football.football.model.Equipe;

import com.football.football.repository.EquipeRepository;
import com.football.football.service.EquipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/equipes")
public class EquipeController {

    @Autowired
    private EquipeService equipeService;
    @Autowired
    private EquipeRepository equipeRepository;

    @GetMapping
    public Page<Equipe> getEquipes(@PageableDefault(page = 0, size = 10)
                                       @SortDefault.SortDefaults({
                                               @SortDefault(sort = "name", direction = Sort.Direction.ASC),
                                               @SortDefault(sort = "acronym", direction = Sort.Direction.ASC),
                                               @SortDefault(sort = "budget", direction = Sort.Direction.DESC)
                                       }) Pageable pageable) {
        return equipeRepository.findAll(pageable);
    }

    @PostMapping
    public Equipe createEquipe(@RequestBody Equipe equipe) {
        return equipeService.addEquipe(equipe);
    }
}
