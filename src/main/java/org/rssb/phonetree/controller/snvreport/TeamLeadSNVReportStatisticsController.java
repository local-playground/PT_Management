package org.rssb.phonetree.controller.snvreport;

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
import org.rssb.phonetree.common.Constants;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.domain.PhoneTreeActivationSevadarSummary;
import org.rssb.phonetree.domain.PhoneTreeActivationTeamLeadSummary;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@Lazy
@Scope("prototype")
public class TeamLeadSNVReportStatisticsController extends AbstractController {

    @FXML
    private Parent teamLeadContainer;

    @FXML
    private Label teamLeadFirstNameLabel;

    @FXML
    private Label teamLeadLastNameLabel;

    @FXML
    private Label teamLeadFamiliesCountLabel;

    @FXML
    private Label teamLeadVMLeftCount;

    @FXML
    private Label teamLeadNotReachedCount;

    @FXML
    private Label showDetails;

    private PopOver popOver = new PopOver();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void postProcess() {
        PhoneTreeActivationTeamLeadSummary phoneTreeActivationTeamLeadSummary =
                (PhoneTreeActivationTeamLeadSummary) contextHolder.get(Constants.REQUEST_OBJ);
        teamLeadFirstNameLabel.setText(phoneTreeActivationTeamLeadSummary.getFirstName());
        teamLeadLastNameLabel.setText(phoneTreeActivationTeamLeadSummary.getLastName());
        teamLeadVMLeftCount.setText(String.valueOf(phoneTreeActivationTeamLeadSummary.getTotalVMLeft()));
        teamLeadNotReachedCount.setText(String.valueOf(phoneTreeActivationTeamLeadSummary.getTotalNotReachedFamilies()));
        teamLeadFamiliesCountLabel.setText(String.valueOf(phoneTreeActivationTeamLeadSummary.getTotalFamilies()));
    }

    @FXML
    void showDetails(MouseEvent event) {
        PhoneTreeActivationTeamLeadSummary phoneTreeActivationTeamLeadSummary =
                (PhoneTreeActivationTeamLeadSummary) contextHolder.get(Constants.REQUEST_OBJ);
        TableView<PhoneTreeActivationSevadarSummary> sevadarsSNVStatisticsReportTable =
                (TableView<PhoneTreeActivationSevadarSummary>) loadFxml("/fxml/snv-report/sevadars-snv-report-statistics.fxml", null, null);
        ObservableList<PhoneTreeActivationSevadarSummary> familyCountObservableList =
                FXCollections.observableArrayList(phoneTreeActivationTeamLeadSummary.getPhoneTreeActivationSevadarSummaryList());
        sevadarsSNVStatisticsReportTable.setItems(familyCountObservableList);
        showPopover(sevadarsSNVStatisticsReportTable);
    }

    private void showPopover(TableView<PhoneTreeActivationSevadarSummary> sevadarsCountTableView) {
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
