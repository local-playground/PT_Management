package org.rssb.phonetree.controller.dashboard;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import org.rssb.phonetree.common.ContextHolder;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.domain.DashboardTeamLeadsSummary;
import org.rssb.phonetree.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
@Lazy
public class DashboardController extends AbstractController {
    @FXML
    private AnchorPane familiesSummaryPane;

    @FXML
    private FlowPane teamLeadSummaryPane;

    @Autowired
    private DashboardService dashboardService;

    @FXML
    private FlowPane otherStatisticsHolderPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Parent parent = loadFxml("/fxml/dashboard/families-statistics.fxml", null,null);
        familiesSummaryPane.getChildren().add(parent);
        List<DashboardTeamLeadsSummary> dashboardTeamLeadsSummaryList = dashboardService.getDashboardTeamLeadsSummary();
        int cssId = 1;
        for (DashboardTeamLeadsSummary dashboardTeamLeadsSummary : dashboardTeamLeadsSummaryList) {
            ContextHolder contextHolder =
                    createContextHolder(new String[]{"DASHBOARD_TEAMLEAD_SUMMARY", "CSS_ID"},
                            new Object[]{dashboardTeamLeadsSummary, cssId}, null);
            Parent teamLeadSummaryParent = loadFxml("/fxml/dashboard/teamlead-statistics.fxml", contextHolder,null);
            teamLeadSummaryPane.getChildren().add(teamLeadSummaryParent);
            cssId++;
            if(cssId>6){
                cssId=1;
            }
        }

        parent = loadFxml("/fxml/dashboard/phone-status-statistics.fxml", null,null);
        otherStatisticsHolderPane.getChildren().addAll(parent);
        parent = loadFxml("/fxml/dashboard/bus-ride-statistics.fxml", null,null);
        otherStatisticsHolderPane.getChildren().addAll(parent);
        parent = loadFxml("/fxml/dashboard/zip-code-statistics.fxml", null,null);
        otherStatisticsHolderPane.getChildren().addAll(parent);
        parent = loadFxml("/fxml/dashboard/adults-snv-statistics.fxml", null,null);
        otherStatisticsHolderPane.getChildren().addAll(parent);

    }



}
