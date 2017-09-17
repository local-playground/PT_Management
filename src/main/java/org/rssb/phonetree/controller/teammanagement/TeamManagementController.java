package org.rssb.phonetree.controller.teammanagement;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.controller.teamlead.TeamLeadController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class TeamManagementController extends AbstractController{

    @Autowired
    @Lazy
    private TeamLeadController teamLeadController;

    @FXML
    private AnchorPane teamManagementRootPane;


    @Override
    public void postProcess(){
        teamLeadController.postProcess();
    }

    public AnchorPane getTeamManagementRootPane() {
        return teamManagementRootPane;
    }

}
