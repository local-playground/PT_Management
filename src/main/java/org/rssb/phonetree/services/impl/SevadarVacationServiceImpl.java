package org.rssb.phonetree.services.impl;


import org.rssb.phonetree.entity.SevadarVacation;
import org.rssb.phonetree.repository.SevadarVacationRepository;
import org.rssb.phonetree.services.SevadarVacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SevadarVacationServiceImpl implements SevadarVacationService{

    @Autowired
    private SevadarVacationRepository sevadarVacationRepository;

    @Override
    public Optional<SevadarVacation> getSevadarVacationBySevadarId(int sevadarId) {
        return Optional.ofNullable(sevadarVacationRepository.findBySevadarSevadarsId(sevadarId));
    }

    @Override
    public Optional<SevadarVacation> getSevadarVacationByTeamLeadId(int teamLeadId) {
        return Optional.ofNullable(sevadarVacationRepository.findByTeamLeadTeamLeadId(teamLeadId));
    }

    @Override
    public void save(SevadarVacation sevadarVacation) {
        sevadarVacationRepository.saveAndFlush(sevadarVacation);
    }
}
