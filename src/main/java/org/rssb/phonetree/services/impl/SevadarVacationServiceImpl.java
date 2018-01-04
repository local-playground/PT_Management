package org.rssb.phonetree.services.impl;


import org.rssb.phonetree.domain.SevadarVacationPlan;
import org.rssb.phonetree.domain.SevadarsMonthlyAvailability;
import org.rssb.phonetree.domain.VacationDate;
import org.rssb.phonetree.entity.Sevadar;
import org.rssb.phonetree.entity.SevadarVacation;
import org.rssb.phonetree.entity.TeamLead;
import org.rssb.phonetree.helper.VacationPlanHelper;
import org.rssb.phonetree.repository.SevadarVacationRepository;
import org.rssb.phonetree.services.SevadarVacationService;
import org.rssb.phonetree.services.TeamLeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SevadarVacationServiceImpl implements SevadarVacationService{

    @Autowired
    private SevadarVacationRepository sevadarVacationRepository;

    @Autowired
    private TeamLeadService teamLeadService;

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

    @Override
    public Map<String, List<SevadarVacationPlan>> getAllSevadarsVacationPlan(LocalDate startMonth, LocalDate endMonth) {
        List<TeamLead> teamLeadList = teamLeadService.findAllTeamLeads();
        Map<String, List<SevadarVacationPlan>> vacationPlanMap = new HashMap<>();
        for(TeamLead teamLead: teamLeadList){
            List<SevadarVacationPlan> sevadarVacationPlanList = new ArrayList<>();
            Optional<SevadarVacation> sevadarVacationOptional = getSevadarVacationByTeamLeadId(teamLead.getTeamLeadId());
            sevadarVacationPlanList.add(getSevadarVacationPlan(startMonth, endMonth, teamLead.getTeamLeadName(),sevadarVacationOptional));

            for (Sevadar sevadar : teamLead.getSevadarsList()) {
                sevadarVacationOptional = getSevadarVacationBySevadarId(sevadar.getSevadarsId());
                sevadarVacationPlanList.add(getSevadarVacationPlan(startMonth, endMonth,
                        sevadar.getSevadarName(),sevadarVacationOptional));
            }

            vacationPlanMap.put(teamLead.getTeamLeadName(), sevadarVacationPlanList);
        }

        return vacationPlanMap;
    }

    private SevadarVacationPlan getSevadarVacationPlan(LocalDate startMonth,LocalDate endMonth,
                                                       String sevadarName,
                                                       Optional<SevadarVacation> sevadarVacationOptional){
        SevadarVacation sevadarVacation = null;
        List<VacationDate> vacationDateList = new ArrayList<>();
        if (sevadarVacationOptional.isPresent()) {
            sevadarVacation = sevadarVacationOptional.get();
            vacationDateList = sevadarVacation.getVacationDateList();
        }

        List<SevadarsMonthlyAvailability> sevadarsMonthlyAvailabilityList =
                VacationPlanHelper.getSevadarAvailabilityDetails(sevadarName,
                        vacationDateList, startMonth, endMonth);



        SevadarVacationPlan sevadarVacationPlan = new SevadarVacationPlan();
        sevadarVacationPlan.setSevadarName(sevadarName);
        sevadarVacationPlan.setSevadarsMonthlyAvailabilities(sevadarsMonthlyAvailabilityList);

        return sevadarVacationPlan;
    }
}
