package org.rssb.phonetree.controller.teamlead;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.rssb.phonetree.common.Constants;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.custom.controls.DecoratedTextField;
import org.rssb.phonetree.entity.TeamLead;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@Lazy
public class TeamLeadEmailTemplate extends AbstractController{

    @FXML
    private Label teamLeadIdLabel;

    @FXML
    private Label firstNameLabel;

    @FXML
    private VBox emailIdTextFieldsHolder;

    @FXML
    private DecoratedTextField emailIdTextField;

    @Override
    public void postProcess(){
        TeamLead teamLead = (TeamLead) contextHolder.get(Constants.REQUEST_OBJ);
        firstNameLabel.setText(teamLead.getTeamLeadName());
        teamLeadIdLabel.setText(String.valueOf(teamLead.getTeamLeadId()));

    }

    @FXML
    void addMoreEmailIdTextField(MouseEvent event) {

    }

    @FXML
    void deleteEmptyEmailTextField(MouseEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
