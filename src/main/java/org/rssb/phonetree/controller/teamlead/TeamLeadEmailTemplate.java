package org.rssb.phonetree.controller.teamlead;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.rssb.phonetree.common.CommonUtil;
import org.rssb.phonetree.common.Constants;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.controller.TextFieldController;
import org.rssb.phonetree.custom.controls.DecoratedTextField;
import org.rssb.phonetree.entity.TeamLead;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

@Component
@Lazy
public class TeamLeadEmailTemplate extends AbstractController {

    private List<TextFieldController> textFieldControllerList = new ArrayList<>();

    @FXML
    private Label firstNameLabel;

    @FXML
    private VBox emailIdTextFieldsHolder;

    @FXML
    private DecoratedTextField emailIdTextField;

    @Override
    public void postProcess() {
        TeamLead teamLead = (TeamLead) contextHolder.get(Constants.REQUEST_OBJ);
        firstNameLabel.setText(teamLead.getTeamLeadName());
        String emailId = teamLead.getEmailId();
        if (CommonUtil.isNotEmptyOrNull(emailId)) {
            List<String> emailIdList = Arrays.asList(emailId.split(","));
            for (int index = 0; index < emailIdList.size(); index++) {
                String email = emailIdList.get(index);
                int size = emailIdTextFieldsHolder.getChildren().size();
                if (size <= index) {
                    addMoreEmailIdTextField(null);
                }
                TextFieldController textFieldController = textFieldControllerList.size() > index ?
                        textFieldControllerList.get(index) : null;

                if (textFieldController != null) {
                    textFieldController.setText(email);
                }
            }
        }
    }

    public TeamLead getTeamLead(){
        TeamLead teamLead = (TeamLead) contextHolder.get(Constants.REQUEST_OBJ);
        StringBuilder sb = new StringBuilder();
        for (int index = 0; index < emailIdTextFieldsHolder.getChildren().size(); index++) {
            TextFieldController textFieldController = textFieldControllerList.size() > index ?
                    textFieldControllerList.get(index) : null;

            if (textFieldController != null) {
                sb.append(textFieldController.getText()).append(",");
            }
        }
        teamLead.setEmailId(CommonUtil.extractDataButLastCharacter(sb));

        return teamLead;
    }

    @FXML
    void addMoreEmailIdTextField(MouseEvent event) {
        emailIdTextFieldsHolder.getChildren().add(getHBox());
    }

    @FXML
    void deleteEmptyEmailTextField(MouseEvent event) {
        List<Node> nodeList = emailIdTextFieldsHolder.getChildren();
        int size = nodeList.size();
        for (int index = size - 1; index > 0; index--) {
            TextFieldController textFieldController = textFieldControllerList.get(index);
            if (textFieldController.isTextFieldEmpty()) {
                emailIdTextFieldsHolder.getChildren().remove(index);
                textFieldControllerList.remove(index);
                return;
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addMoreEmailIdTextField(null);
    }

    private HBox getHBox() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/fxml/email-text-field.fxml"));
        HBox parent;
        try {
            parent = fxmlLoader.load();
            TextFieldController textFieldController = fxmlLoader.getController();
            textFieldControllerList.add(textFieldController);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        return parent;
    }
}
