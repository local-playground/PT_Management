package org.rssb.phonetree.controller.dashboard;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.domain.DashboardFamiliesSummary;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class FamiliesStatisticsController extends AbstractController {
    @FXML
    private Label totalSangatLabel;

    @FXML
    private Label totalFamiliesLabel;

    @FXML
    private Label totalFamiliesToCallLabel;

    @FXML
    private Label totalFamiliesNotOnCallLabel;

    @Override
    public void postProcess() {
        DashboardFamiliesSummary dashboardFamiliesSummary = (DashboardFamiliesSummary) contextHolder.get("DASHBOARD_FAMILIES_SUMMARY");
        totalSangatLabel.setText(String.valueOf(dashboardFamiliesSummary.getTotalSangatCount()));
        totalFamiliesLabel.setText(String.valueOf(dashboardFamiliesSummary.getTotalFamiliesCount()));
        totalFamiliesToCallLabel.setText(String.valueOf(dashboardFamiliesSummary.getOnCallingListFamiliesCount()));
        totalFamiliesNotOnCallLabel.setText(String.valueOf(dashboardFamiliesSummary.getNotOnCallingListFamiliesCount()));
    }
}