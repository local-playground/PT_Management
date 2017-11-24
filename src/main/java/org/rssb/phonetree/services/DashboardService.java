package org.rssb.phonetree.services;

import org.rssb.phonetree.domain.DashboardBusRideSummary;
import org.rssb.phonetree.domain.DashboardFamiliesSummary;
import org.rssb.phonetree.domain.DashboardNameValueBasedSummary;
import org.rssb.phonetree.domain.DashboardPhoneStatusSummary;
import org.rssb.phonetree.domain.DashboardTeamLeadsSummary;

import java.util.List;

public interface DashboardService {
    DashboardFamiliesSummary getDashboardFamiliesSummary();
    List<DashboardTeamLeadsSummary> getDashboardTeamLeadsSummary();
    List<DashboardNameValueBasedSummary> getZipCodeCollectionSummary();
    List<DashboardNameValueBasedSummary> getTotalAdultsAndChildrenAttendSNVSummary();
    List<DashboardPhoneStatusSummary> getPhoneStatusSummary();
    List<DashboardBusRideSummary> getBusRideNeededSummary();
}
