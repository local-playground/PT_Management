package org.rssb.phonetree.services;


import org.junit.Test;
import org.rssb.phonetree.ApplicationSetup;
import org.rssb.phonetree.domain.DashboardFamiliesSummary;
import org.rssb.phonetree.domain.DashboardTeamLeadsSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = "org.rssb.phonetree")
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class DashboardServiceTest extends ApplicationSetup{

    @Autowired
    private DashboardService dashboardService;

    @Test
    public void getDahsboardFamiliesSummary(){
        DashboardFamiliesSummary dashboardFamiliesSummary = dashboardService.getDashboardFamiliesSummary();
        System.out.println(dashboardFamiliesSummary);
    }

    @Test
    public void getDashboardTeamLeadSummary() {
        List<DashboardTeamLeadsSummary> dashboardTeamLeadsSummary = dashboardService.getDashboardTeamLeadsSummary();
        dashboardTeamLeadsSummary.forEach(dashboardTeamLeadsSummary1 -> System.out.println(dashboardTeamLeadsSummary1));
    }
}
