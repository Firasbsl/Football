package com.football.football.service;

import com.football.football.model.Equipe;
import com.football.football.repository.EquipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EquipeServiceImpl implements EquipeService {

    @Autowired
    private EquipeRepository equipeRepository;

    @Override
    public Page<Equipe> getAllEquipes(Pageable pageable, String sortBy) {
        return equipeRepository.findAll(pageable);
    }

    @Override
    public Equipe addEquipe(Equipe equipe) {
        return equipeRepository.save(equipe);
    }
}
