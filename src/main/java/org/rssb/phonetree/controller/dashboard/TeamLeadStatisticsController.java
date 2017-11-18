package org.rssb.phonetree.controller.dashboard;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.controlsfx.control.HyperlinkLabel;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.domain.DashboardTeamLeadsSummary;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class TeamLeadStatisticsController extends AbstractController{
    @FXML
    private Label teamLeadFirstNameLabel;

    @FXML
    private Label teamLeadLastNameLabel;

    @FXML
    private Label teamLeadFamiliesCountLabel;

    @FXML
    private Label teamLeadSevadarsCountLabel;

    @FXML
    private HyperlinkLabel showDetailLabel;

    @Override
    public void postProcess() {
        DashboardTeamLeadsSummary dashboardTeamLeadsSummary = (DashboardTeamLeadsSummary) contextHolder.get("DASHBOARD_TEAMLEAD_SUMMARY");
        teamLeadFirstNameLabel.setText(dashboardTeamLeadsSummary.getFirstName());
        teamLeadLastNameLabel.setText(dashboardTeamLeadsSummary.getLastName());
        teamLeadFamiliesCountLabel.setText(String.valueOf(dashboardTeamLeadsSummary.getTotalFamilies()));
        teamLeadSevadarsCountLabel.setText(String.valueOf(dashboardTeamLeadsSummary.getTotalSevadars()));
    }

    @FXML
    void showDetails(ActionEvent event) {

    }
}
