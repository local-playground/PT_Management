package org.rssb.phonetree.controller.dashboard;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.domain.DashboardNameValueBasedSummary;
import org.rssb.phonetree.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Lazy
public class AdultsChildrenSNVStatisticsController extends AbstractController {
    @FXML
    private Label adultsCount;

    @FXML
    private Label childrenCount;


    @Autowired
    private DashboardService dashboardService;

    @Override
    public void postProcess() {
        List<DashboardNameValueBasedSummary> dashboardNameValueBasedSummaryList = dashboardService.getTotalAdultsAndChildrenAttendSNVSummary();
        dashboardNameValueBasedSummaryList.forEach(dashboardNameValueBasedSummary -> {
            if(dashboardNameValueBasedSummary.getName().equalsIgnoreCase("Adults")){
                adultsCount.setText(String.valueOf(dashboardNameValueBasedSummary.getCount()));
            }
            if(dashboardNameValueBasedSummary.getName().equalsIgnoreCase("Children")){
                childrenCount.setText(String.valueOf(dashboardNameValueBasedSummary.getCount()));
            }
        });
    }

}
