package org.rssb.phonetree.controller.familymanagement;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawersStack;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ToggleGroup;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.spring.config.SpringFXMLLoader;
import org.rssb.phonetree.ui.view.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
@Lazy
public class FamilyManagementController extends AbstractController {
    @Autowired
    private SpringFXMLLoader springFXMLLoader;

    @FXML
    private JFXButton saveFamily;

    @FXML
    private JFXButton deleteFamily;

    @FXML
    private JFXButton resetForm;

    @FXML
    private JFXTextField zipCode;

    @FXML
    private JFXTextField noOfAdultsAttendsSNV;

    @FXML
    private JFXTextField noOfChildrenAttendsSNV;

    @FXML
    private JFXTextField town;

    @FXML
    private ToggleGroup SNVGuidelineGroup;

    @FXML
    private ToggleGroup NeedBusRideGroup;

    @FXML
    private JFXTextField noOfPassengers;

    @FXML
    private ToggleGroup CanCallAnytimeGroup;

    @FXML
    private JFXTimePicker fromTime;

    @FXML
    private JFXTimePicker toTime;

    @FXML
    private ToggleGroup PhoneStatusGroup;

    @FXML
    private ToggleGroup familyActiveGroup;

    @FXML
    private JFXTextArea internalComments;

    @FXML
    private JFXTextArea comments;

    @FXML
    private JFXButton addMember;

    @FXML
    private JFXButton deleteMember;

    @FXML
    private JFXButton moveUnderOtherFamily;

    @FXML
    private JFXButton moveAsSeparateFamily;

    @FXML
    private JFXButton addSelectedMembersToBus;

    @FXML
    private TableColumn<?, ?> firstNameTableColumn;

    @FXML
    private TableColumn<?, ?> lastNameTableColumn;

    @FXML
    private TableColumn<?, ?> cellPhoneTableColumn;

    @FXML
    private TableColumn<?, ?> leaveCellVMTableColumn;

    @FXML
    private TableColumn<?, ?> homePhoneTableColumn;

    @FXML
    private TableColumn<?, ?> leaveHomeVMTableColumn;

    @FXML
    private TableColumn<?, ?> workPhoneTableColumn;

    @FXML
    private TableColumn<?, ?> leaveWorkVMTableColumn;

    @FXML
    private TableColumn<?, ?> preferredPhoneTableColumn;

    @FXML
    private TableColumn<?, ?> onSevadarsCallingListTableColumn;

    @FXML
    private TableColumn<?, ?> callPriority;

    @FXML
    private TableColumn<?, ?> memberIdTableColumn;

    @FXML
    private TableColumn<?, ?> familyIdTableColumn;

    @FXML
    private JFXDrawer jfxDrawer;

    private JFXDrawersStack drawersStack = new JFXDrawersStack();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Parent parent = springFXMLLoader.load(FxmlView.ADD_MEMBER_INFORMATION.getFxmlFile());
            jfxDrawer.setSidePane(parent);
            jfxDrawer.setOverLayVisible(false);

            System.out.println("initialize jfxDrawer.isShowing - "+jfxDrawer.isShowing());
            System.out.println("initialize jfxDrawer.isHidden - "+jfxDrawer.isHidden());
            System.out.println("initialize jfxDrawer.isHiding - "+jfxDrawer.isHiding());
            System.out.println("Initiaize  jfxDrawer.isShown - "+jfxDrawer.isShown());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addMember(ActionEvent event) {
        System.out.println("jfxDrawer.isShowing - "+jfxDrawer.isShowing());
        System.out.println("jfxDrawer.isHidden - "+jfxDrawer.isHidden());
        System.out.println("jfxDrawer.isHiding - "+jfxDrawer.isHiding());
        System.out.println("jfxDrawer.isShown - "+jfxDrawer.isShown());
        if (jfxDrawer.isHidden()) {
            jfxDrawer.setVisible(true);
            jfxDrawer.open();
        } else {
            jfxDrawer.setVisible(false);
            jfxDrawer.close();
        }

    }

    @FXML
    void addSelectedMembersToBus(ActionEvent event) {

    }

    @FXML
    void deleteFamily(ActionEvent event) {

    }

    @FXML
    void deleteMember(ActionEvent event) {

    }

    @FXML
    void moveAsSeparateFamily(ActionEvent event) {

    }

    @FXML
    void moveUnderOtherFamily(ActionEvent event) {

    }

    @FXML
    void resetForm(ActionEvent event) {

    }

    @FXML
    void saveFamily(ActionEvent event) {

    }


}
