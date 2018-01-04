package org.rssb.phonetree.services;

import org.rssb.phonetree.domain.SevadarVacationPlan;
import org.rssb.phonetree.entity.SevadarVacation;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SevadarVacationService {
    Optional<SevadarVacation> getSevadarVacationBySevadarId(int sevadarId);
    Optional<SevadarVacation> getSevadarVacationByTeamLeadId(int teamLeadId);
    void save(SevadarVacation sevadarVacation);
    Map<String,List<SevadarVacationPlan>> getAllSevadarsVacationPlan(LocalDate startMonth,LocalDate endMonth);
}
