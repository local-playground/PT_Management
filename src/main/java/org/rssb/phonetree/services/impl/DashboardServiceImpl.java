package org.rssb.phonetree.services.impl;

import org.rssb.phonetree.domain.DashboardFamiliesSummary;
import org.rssb.phonetree.domain.DashboardTeamLeadsSummary;
import org.rssb.phonetree.domain.FamilyCount;
import org.rssb.phonetree.entity.TeamLead;
import org.rssb.phonetree.services.DashboardService;
import org.rssb.phonetree.services.FamilyService;
import org.rssb.phonetree.services.MemberService;
import org.rssb.phonetree.services.SevadarService;
import org.rssb.phonetree.services.TeamLeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class DashboardServiceImpl implements DashboardService{
    @Autowired
    private FamilyService familyService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private TeamLeadService teamLeadService;

    @Autowired
    private SevadarService sevadarService;

    @Override
    public DashboardFamiliesSummary getDashboardFamiliesSummary() {
        DashboardFamiliesSummary dashboardFamiliesSummary = new DashboardFamiliesSummary();
        dashboardFamiliesSummary.setTotalSangatCount(memberService.getTotalSangatCount());
        dashboardFamiliesSummary.setTotalFamiliesCount(familyService.getTotalFamiliesCount());
        dashboardFamiliesSummary.setOnCallingListFamiliesCount(memberService.getTotalSangatCountOnCallingList());
        dashboardFamiliesSummary.setNotOnCallingListFamiliesCount(memberService.getTotalSangatCountNotOnCallingList());
        return dashboardFamiliesSummary;
    }

    @Override
    public List<DashboardTeamLeadsSummary> getDashboardTeamLeadsSummary() {
        List<DashboardTeamLeadsSummary> dashboardTeamLeadsSummaryList = new ArrayList<>();
        List<TeamLead> teamLeadList = teamLeadService.findAllTeamLeads();
        for(TeamLead teamLead: teamLeadList){
            DashboardTeamLeadsSummary dashboardTeamLeadsSummary = new DashboardTeamLeadsSummary();
            dashboardTeamLeadsSummary.setTeamLeadId(teamLead.getTeamLeadId());
            dashboardTeamLeadsSummary.setFirstName(teamLead.getMember().getFirstName());
            dashboardTeamLeadsSummary.setLastName(teamLead.getMember().getLastName());
            List<FamilyCount> familyCountList = sevadarService.getSevadarsCallingFamilyCountByTeamLeadId(teamLead.getTeamLeadId());
            dashboardTeamLeadsSummary.setFamilyCountList(familyCountList);
            dashboardTeamLeadsSummary.setTotalFamilies(familyService.getTotalFamiliesByTeamLeadId(teamLead.getTeamLeadId()));
            dashboardTeamLeadsSummary.setTotalSevadars(teamLead.getSevadarsList().size());
            dashboardTeamLeadsSummaryList.add(dashboardTeamLeadsSummary);
        }

        return dashboardTeamLeadsSummaryList;
    }


}
