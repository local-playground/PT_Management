package org.rssb.phonetree.controller.familymanagement;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
import org.rssb.phonetree.controller.AbstractController;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class MemberInformationController extends AbstractController{
    @FXML
    private JFXTextField memberId;

    @FXML
    private JFXTextField familyId;

    @FXML
    private JFXTextField firstName;

    @FXML
    private JFXTextField lastName;

    @FXML
    private JFXTextArea cellPhone;

    @FXML
    private ToggleGroup LeaveCellVM;

    @FXML
    private JFXTextArea homePhone;

    @FXML
    private ToggleGroup LeaveHomeVM;

    @FXML
    private JFXTextArea workPhone;

    @FXML
    private ToggleGroup LeaveWorkVM;

    @FXML
    private ToggleGroup preferredPhoneGroup;

    @FXML
    private ToggleGroup onCallingListGroup;

    @FXML
    private JFXComboBox<?> callPriority;

    @FXML
    private JFXButton addMember;

    @FXML
    private JFXButton cancel;

    @FXML
    void addMember(ActionEvent event) {

    }

    @FXML
    void cancel(ActionEvent event) {

    }

}
