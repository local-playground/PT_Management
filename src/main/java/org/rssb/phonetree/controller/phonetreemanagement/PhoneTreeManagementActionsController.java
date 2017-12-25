package org.rssb.phonetree.controller.phonetreemanagement;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.spring.config.SpringFXMLLoader;
import org.rssb.phonetree.ui.view.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Lazy
public class PhoneTreeManagementActionsController extends AbstractController{
    @Autowired
    private SpringFXMLLoader springFXMLLoader;

    @Autowired
    private PhoneTreeManagementController phoneTreeManagementController;

    @FXML
    private AnchorPane phoneTreeManagementActionsRootPane;

    @FXML
    private JFXButton dashboardButton;

    @FXML
    private JFXButton teamManagementButton;

    @FXML
    private JFXButton familyManagementButton;

    @FXML
    private JFXButton sevadarsPhoneTreeListButton;

    @FXML
    private JFXButton reportManagementButton;

    @FXML
    private JFXButton teamChartButton;

    @FXML
    private JFXButton snvReportButton;

    @FXML
    private JFXButton vacationPlanButton;

    @FXML
    private JFXButton administrationButton;

    @FXML
    void createReports(ActionEvent event) {
        switchScreen(FxmlView.PHONE_TREE_LIST.getFxmlFile());
    }

    @FXML
    void createTeamChart(ActionEvent event) {

    }

    @FXML
    void prepareSNVReports(ActionEvent event) {
        //switchScreen(FxmlView.ADD_SNV_REPORT_DETAILS.getFxmlFile());
        switchScreen(FxmlView.VIEW_SNV_REPORT_DETAILS.getFxmlFile());
        snvReportButton.requestFocus();
    }

    @FXML
    void showAdminScreen(ActionEvent event) {

    }

    @FXML
    void showDashboard(ActionEvent event) {
        switchScreen(FxmlView.DASHBOARD.getFxmlFile());
    }

    @FXML
    public void showFamilyManagement(ActionEvent event) {
        switchScreen(FxmlView.FAMILY_MANAGEMENT.getFxmlFile());
        familyManagementButton.requestFocus();
    }

    @FXML
    void showReportsManagement(ActionEvent event) {

    }

    @FXML
    void showSevadarsAvailability(ActionEvent event) {
        //switchScreen(FxmlView.VACATION_PLAN.getFxmlFile());
        switchScreen(FxmlView.ADD_VACATION_PLAN.getFxmlFile());
        vacationPlanButton.requestFocus();
    }

    @FXML
    void showSevadarsPhoneTreeLists(ActionEvent event) {
        switchScreen(FxmlView.SEVADARS_LIST.getFxmlFile());
    }

    @FXML
    void showTeamManagement(ActionEvent event) {
        switchScreen(FxmlView.TEAM_LEAD_MANAGEMENT.getFxmlFile());
    }

    private void switchScreen(String fxmlFile) {
        try {
            Parent parent = springFXMLLoader.load(fxmlFile);
            BorderPane borderPane = (BorderPane) phoneTreeManagementController.getRootPanel();
            borderPane.setCenter(parent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Parent getRootPanel() {
        return phoneTreeManagementActionsRootPane;
    }
}
