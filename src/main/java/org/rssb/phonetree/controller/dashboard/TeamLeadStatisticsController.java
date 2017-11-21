package org.rssb.phonetree.controller.dashboard;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.controlsfx.control.PopOver;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.domain.DashboardTeamLeadsSummary;
import org.rssb.phonetree.domain.FamilyCount;
import org.rssb.phonetree.services.SevadarService;
import org.rssb.phonetree.spring.config.SpringFXMLLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component()
@Lazy
@Scope("prototype")
public class TeamLeadStatisticsController extends AbstractController{

    @Autowired
    private SevadarService sevadarService;

    @Autowired
    private SpringFXMLLoader springFXMLLoader;

    @FXML
    private Parent teamLeadContainer;

    @FXML
    private Parent teamLeadNameContainer;

    @FXML
    private Parent teamLeadDetailContainer;

    @FXML
    private Label teamLeadFirstNameLabel;

    @FXML
    private Label teamLeadLastNameLabel;

    @FXML
    private Label teamLeadFamiliesCountLabel;

    @FXML
    private Label teamLeadSevadarsCountLabel;

    @FXML
    private Label showDetailLabel;

    private DashboardTeamLeadsSummary dashboardTeamLeadsSummary;


    private PopOver popOver = new PopOver();

    @Override
    public void postProcess() {
        dashboardTeamLeadsSummary = (DashboardTeamLeadsSummary) contextHolder.get("DASHBOARD_TEAMLEAD_SUMMARY");
        Integer cssId = (Integer) contextHolder.get("CSS_ID");
        teamLeadContainer.getStyleClass().add("style"+cssId);
        teamLeadNameContainer.getStyleClass().add("style"+cssId);
        teamLeadDetailContainer.getStyleClass().add("style"+cssId);

        teamLeadFirstNameLabel.setText(dashboardTeamLeadsSummary.getFirstName());
        teamLeadLastNameLabel.setText(dashboardTeamLeadsSummary.getLastName());
        teamLeadFamiliesCountLabel.setText(String.valueOf(dashboardTeamLeadsSummary.getTotalFamilies()));
        teamLeadSevadarsCountLabel.setText(String.valueOf(dashboardTeamLeadsSummary.getTotalSevadars()));
    }

    @FXML
    void showDetails(MouseEvent event) {
        TableView<FamilyCount> sevadarsCountTableView = null;
        try {
            sevadarsCountTableView = (TableView<FamilyCount>) springFXMLLoader.load("/fxml/dashboard/sevadars-count-details.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //List<FamilyCount> familyCountList = sevadarService.getSevadarsCallingFamilyCountByTeamLeadId(dashboardTeamLeadsSummary.getTeamLeadId());
        ObservableList<FamilyCount> familyCountObservableList = FXCollections.observableArrayList(dashboardTeamLeadsSummary.getFamilyCountList());
        sevadarsCountTableView.setItems(familyCountObservableList);
        showPopover(sevadarsCountTableView);
    }

    private void showPopover(TableView<FamilyCount> sevadarsCountTableView){
        VBox box = new VBox();
        box.getStylesheets().add("/styles/Styles.css");
        box.getStyleClass().add("base");
        box.getStyleClass().add("border");
        box.getStyleClass().add("border-radius");
        box.getStyleClass().add("background-radius");
        box.getChildren().add(sevadarsCountTableView);
        box.setPadding(new Insets(20));
        popOver.setContentNode(box);
        popOver.setAnimated(true);
        popOver.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
        popOver.show(teamLeadContainer);
    }

}
