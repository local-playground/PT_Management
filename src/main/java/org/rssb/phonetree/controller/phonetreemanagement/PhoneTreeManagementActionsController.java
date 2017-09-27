package org.rssb.phonetree.controller.phonetreemanagement;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class PhoneTreeManagementActionsController {
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
    private JFXButton administrationButton;

    @FXML
    void showAdminScreen(ActionEvent event) {

    }

    @FXML
    void showDashboard(ActionEvent event) {

    }

    @FXML
    void showFamilyManagement(ActionEvent event) {

    }

    @FXML
    void showReportsManagement(ActionEvent event) {

    }

    @FXML
    void showSevadarsPhoneTreeLists(ActionEvent event) {

    }

    @FXML
    void showTeamManagement(ActionEvent event) {

    }
}
