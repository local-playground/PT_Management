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
