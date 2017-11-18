package org.rssb.phonetree.services;

import org.rssb.phonetree.domain.DashboardFamiliesSummary;
import org.rssb.phonetree.domain.DashboardTeamLeadsSummary;

import java.util.List;

public interface DashboardService {
    DashboardFamiliesSummary getDashboardFamiliesSummary();
    List<DashboardTeamLeadsSummary> getDashboardTeamLeadsSummary();
}
