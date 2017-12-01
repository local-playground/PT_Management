package org.rssb.phonetree.services;

import org.rssb.phonetree.entity.SevadarVacation;

import java.util.Optional;

public interface SevadarVacationService {
    Optional<SevadarVacation> getSevadarVacationBySevadarId(int sevadarId);
    Optional<SevadarVacation> getSevadarVacationByTeamLeadId(int teamLeadId);
    void save(SevadarVacation sevadarVacation);
}
