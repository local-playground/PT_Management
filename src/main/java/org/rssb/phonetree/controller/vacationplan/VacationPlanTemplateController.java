package org.rssb.phonetree.controller.vacationplan;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import org.rssb.phonetree.entity.SevadarVacation;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
@Lazy
public class VacationPlanTemplateController extends AbstractController {
    private List<VacationDatesController> vacationDatesControllerList = new ArrayList<>();

    @FXML
    private Label firstNameLabel;

    @FXML
    private VBox vacationDatesTemplateHolder;

    @FXML
    private FontAwesomeIconView addMoreVacationDatePicker;

    @FXML
    private FontAwesomeIconView deleteEmptyVacationDatePicker;

    @Override
    public void postProcess() {
        SevadarVacation sevadarVacation = (SevadarVacation) contextHolder.get(Constants.REQUEST_OBJ);
        String sevadarName = (String) contextHolder.get("SEVADAR_NAME");
        if(CommonUtil.isNotEmptyOrNull(sevadarName)){
            firstNameLabel.setText(sevadarName);
        }

        if (sevadarVacation!=null) {
            /*List<String> emailIdList = Arrays.asList(emailId.split(","));
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
            }*/
        }
    }

    public SevadarVacation getRequest() {
        SevadarVacation sevadarVacation = (SevadarVacation) contextHolder.get(Constants.REQUEST_OBJ);
        StringBuilder sb = new StringBuilder();
        for (int index = 0; index < vacationDatesTemplateHolder.getChildren().size(); index++) {
            VacationDatesController vacationDatesController = vacationDatesControllerList.size() > index ?
                    vacationDatesControllerList.get(index) : null;

            /*if (vacationDatesController != null &&
                    CommonUtil.isNotEmptyOrNull(textFieldController.getText())) {
                sb.append(textFieldController.getText()).append(",");
            }*/
        }
        /*if (type instanceof TeamLead) {
            ((TeamLead) type).setEmailId(CommonUtil.extractDataButLastCharacter(sb));
        } else if (type instanceof Sevadar) {
            ((Sevadar) type).setEmailId(CommonUtil.extractDataButLastCharacter(sb));
        }*/

        return sevadarVacation;
    }


    @FXML
    void addMoreEmailIdTextField(MouseEvent event) {
        vacationDatesTemplateHolder.getChildren().add(getHBox());
    }

    @FXML
    void deleteEmptyEmailTextField(MouseEvent event) {
        List<Node> nodeList = vacationDatesTemplateHolder.getChildren();
        int size = nodeList.size();
        for (int index = size - 1; index > 0; index--) {
            VacationDatesController vacationDatesController = vacationDatesControllerList.get(index);
            if (vacationDatesController.isEmpty()) {
                vacationDatesTemplateHolder.getChildren().remove(index);
                vacationDatesControllerList.remove(index);
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
            VacationDatesController vacationDatesController = fxmlLoader.getController();
            vacationDatesControllerList.add(vacationDatesController);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        return parent;
    }
}
