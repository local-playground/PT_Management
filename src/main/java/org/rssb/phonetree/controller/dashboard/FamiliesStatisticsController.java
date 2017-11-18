package org.rssb.phonetree.controller.dashboard;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.domain.DashboardFamiliesSummary;
import org.rssb.phonetree.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

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

    @Autowired
    private DashboardService dashboardService;

    public FamiliesStatisticsController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void postProcess() {
        DashboardFamiliesSummary dashboardFamiliesSummary = dashboardService.getDashboardFamiliesSummary();
        totalSangatLabel.setText(String.valueOf(dashboardFamiliesSummary.getTotalSangatCount()));
        totalFamiliesLabel.setText(String.valueOf(dashboardFamiliesSummary.getTotalFamiliesCount()));
        totalFamiliesToCallLabel.setText(String.valueOf(dashboardFamiliesSummary.getOnCallingListFamiliesCount()));
        totalFamiliesNotOnCallLabel.setText(String.valueOf(dashboardFamiliesSummary.getNotOnCallingListFamiliesCount()));
    }
}