package com.football.football.service;

import com.football.football.model.Equipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EquipeService {
    Page<Equipe> getAllEquipes(Pageable pageable, String sortBy);
    Equipe addEquipe(Equipe equipe);
}
