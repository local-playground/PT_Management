package org.rssb.phonetree.controller.dashboard;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import org.rssb.phonetree.common.ContextHolder;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.domain.DashboardTeamLeadsSummary;
import org.rssb.phonetree.services.DashboardService;
import org.rssb.phonetree.spring.config.SpringFXMLLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.IOException;
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
    private SpringFXMLLoader springFXMLLoader;

    @Autowired
    private DashboardService dashboardService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Parent parent = loadFxml("/fxml/dashboard/families-statistics.fxml", null);
        familiesSummaryPane.getChildren().add(parent);
        List<DashboardTeamLeadsSummary> dashboardTeamLeadsSummaryList = dashboardService.getDashboardTeamLeadsSummary();
        int cssId = 1;
        for (DashboardTeamLeadsSummary dashboardTeamLeadsSummary : dashboardTeamLeadsSummaryList) {
            ContextHolder contextHolder =
                    createContextHolder(new String[]{"DASHBOARD_TEAMLEAD_SUMMARY", "CSS_ID"},
                            new Object[]{dashboardTeamLeadsSummary, cssId}, null);
            Parent teamLeadSummaryParent = loadFxml("/fxml/dashboard/teamlead-statistics.fxml", contextHolder);
            teamLeadSummaryPane.getChildren().add(teamLeadSummaryParent);
            cssId++;
            if(cssId>6){
                cssId=1;
            }
        }
    }


    private Parent loadFxml(String fxmlFile, ContextHolder contextHolder) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent parent = null;
        try {
            parent = springFXMLLoader.loadAndInvokePostProcess(fxmlFile, contextHolder);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        return parent;
    }
}
