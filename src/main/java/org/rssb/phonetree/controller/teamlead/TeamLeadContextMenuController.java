package org.rssb.phonetree.controller.teamlead;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.rssb.phonetree.common.Constants;
import org.rssb.phonetree.common.ContextHolder;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.controller.EmailTemplate;
import org.rssb.phonetree.entity.TeamLead;
import org.rssb.phonetree.services.TeamLeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
@Lazy
public class TeamLeadContextMenuController extends AbstractController {

    private List<EmailTemplate<TeamLead>> emailTemplatesList = new ArrayList<>();

    @Autowired
    private TeamLeadService teamLeadService;

    @FXML
    private VBox teamLeadsEmailTemplateHolder;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("initializing...");
    }

    @Override
    public void postProcess() {
        List<TeamLead> teamLeadList = (List<TeamLead>) contextHolder.get(Constants.REQUEST_OBJ);
        for (TeamLead teamLead : teamLeadList) {
            teamLeadsEmailTemplateHolder.getChildren().add(getHBox(teamLead));
        }
    }

    private HBox getHBox(TeamLead teamLead) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/fxml/email-template.fxml"));
        HBox parent;
        try {
            parent = fxmlLoader.load();
            EmailTemplate emailTemplate = fxmlLoader.getController();
            ContextHolder ctxHolder = createContextHolder(
                    new String[]{Constants.REQUEST_OBJ},
                    new Object[]{teamLead}, null);

            emailTemplate.setContextHolder(ctxHolder);
            emailTemplate.postProcess();
            emailTemplatesList.add(emailTemplate);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        return parent;
    }

    @FXML
    void addEmailId(ActionEvent event) {
        for(EmailTemplate emailTemplate : emailTemplatesList){
            TeamLead teamLead = (TeamLead) emailTemplate.getRequest();
            teamLeadService.save(teamLead);
        }
        closeScreen(event, contextHolder);
    }

    @FXML
    void cancel(ActionEvent event) {
        closeScreen(event, contextHolder);
    }

}
