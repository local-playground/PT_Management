package org.rssb.phonetree.controller.familymanagement;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXToggleButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.rssb.phonetree.common.CommonUtil;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.custom.controls.DecoratedTextField;
import org.rssb.phonetree.entity.Family;
import org.rssb.phonetree.entity.Member;
import org.rssb.phonetree.entity.emums.PreferredPhoneType;
import org.rssb.phonetree.entity.emums.YesNo;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Lazy
public class MemberInformationController extends AbstractController {
    private List<PhoneNumberController> cellPhoneNumberControllerList = new ArrayList<>();
    private List<PhoneNumberController> homePhoneNumberControllerList = new ArrayList<>();
    private List<PhoneNumberController> workPhoneNumberControllerList = new ArrayList<>();

    @FXML
    private DecoratedTextField memberIdTextField;

    @FXML
    private DecoratedTextField familyIdTextField;

    @FXML
    private DecoratedTextField firstNameTextField;

    @FXML
    private DecoratedTextField lastNameTextField;

    @FXML
    private VBox cellPhoneTextFieldHolder;

    @FXML
    private VBox homePhoneTextFieldHolder;

    @FXML
    private VBox workPhoneTextFieldHolder;

    @FXML
    private ToggleGroup leaveCellVMGroup;

    @FXML
    private ToggleGroup leaveHomeVMGroup;

    @FXML
    private ToggleGroup leaveWorkVMGroup;

    @FXML
    private ToggleGroup preferredPhoneGroup;

    @FXML
    private ToggleGroup onCallingListGroup;

    @FXML
    private JFXComboBox<Integer> callPriorityComboBox;

    @FXML
    private JFXButton addMember;

    @FXML
    private JFXButton cancel;

    private ObservableList<Integer> callPriorityObservableList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Integer> list = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).collect(Collectors.toList());
        callPriorityObservableList = FXCollections.observableList(list);
        callPriorityComboBox.setItems(callPriorityObservableList);
        callPriorityComboBox.getSelectionModel().select(0);
        createAdditionalPhoneNumberTextField(cellPhoneTextFieldHolder, cellPhoneNumberControllerList);
        createAdditionalPhoneNumberTextField(homePhoneTextFieldHolder, homePhoneNumberControllerList);
        createAdditionalPhoneNumberTextField(workPhoneTextFieldHolder, workPhoneNumberControllerList);
    }

    @FXML
    void addMember(ActionEvent event) {
        if (!validate()) {
            return;
        }
        Member member = collectMemberInformation();
        contextHolder.set("MEMBER_DETAIL", member);
        delegator.delegate(contextHolder);
        cellPhoneNumberControllerList.clear();
        homePhoneNumberControllerList.clear();
        workPhoneNumberControllerList.clear();
    }

    @FXML
    void cancel(ActionEvent event) {
        JFXDrawer jfxDrawer = (JFXDrawer) this.contextHolder.get("JFX_DRAWER");
        if (jfxDrawer != null) {
            jfxDrawer.close();
            jfxDrawer.setVisible(false);
        }
    }

    @FXML
    void addAnotherCellPhoneTextField(MouseEvent event) {
        createAdditionalPhoneNumberTextField(cellPhoneTextFieldHolder, cellPhoneNumberControllerList);
    }

    @FXML
    void addAnotherHomePhoneTextField(MouseEvent event) {
        createAdditionalPhoneNumberTextField(homePhoneTextFieldHolder, homePhoneNumberControllerList);
    }

    @FXML
    void addAnotherWorkPhoneTextField(MouseEvent event) {
        createAdditionalPhoneNumberTextField(workPhoneTextFieldHolder, workPhoneNumberControllerList);
    }

    @FXML
    void deleteAdditionalCellPhoneTextField(MouseEvent event) {
        deleteAdditionalButEmptyTextFieldsForPhone(cellPhoneTextFieldHolder, cellPhoneNumberControllerList);
    }

    @FXML
    void deleteAdditionalHomePhoneTextField(MouseEvent event) {
        deleteAdditionalButEmptyTextFieldsForPhone(homePhoneTextFieldHolder, homePhoneNumberControllerList);

    }

    @FXML
    void deleteAdditionalWorkPhoneTextField(MouseEvent event) {
        deleteAdditionalButEmptyTextFieldsForPhone(workPhoneTextFieldHolder, workPhoneNumberControllerList);

    }

    private void deleteAdditionalButEmptyTextFieldsForPhone(VBox parent,
                                                            List<PhoneNumberController> phoneNumberControllerList) {
        List<Node> nodeList = parent.getChildren();
        int size = nodeList.size();
        for (int index = size - 1; index > 0; index--) {
            PhoneNumberController phoneNumberController = phoneNumberControllerList.get(index);
            if (phoneNumberController.isPhoneNumberEmpty()) {
                parent.getChildren().remove(index);
                phoneNumberControllerList.remove(index);
                return;
            }
        }
    }

    private void createAdditionalPhoneNumberTextField(VBox parent, List<PhoneNumberController> list) {
        parent.getChildren().add(getHBox(list));
    }

    @Override
    public void refresh() {
        Member member = (Member) contextHolder.get("MEMBER_DETAIL");
        if (member == null) {
            addMember.setText("ADD");
            return;
        }

        populateMemberInformation(member);
    }

    private String getData(StringBuilder sb) {
        if (CommonUtil.isEmptyOrNull(sb.toString())) {
            return "";
        }

        return sb.substring(0, sb.length() - 1);
    }

    private Member collectMemberInformation() {
        Member member = (Member) contextHolder.get("MEMBER_DETAIL");
        if (member == null) {
            member = new Member();
        }
        member.setFirstName(firstNameTextField.getText());
        member.setLastName(lastNameTextField.getText());
        Map<String, String> phoneNumbersDataMap = capturePhoneNumbersAndComments(cellPhoneTextFieldHolder, cellPhoneNumberControllerList);
        member.setCellPhone(phoneNumbersDataMap.get("PHONE_NUMBERS"));
        member.setCellPhoneComments(phoneNumbersDataMap.get("PHONE_COMMENTS"));
        member.setCellNoVM(YesNo.fromDatabaseName(((JFXToggleButton) leaveCellVMGroup.getSelectedToggle()).getText()));
        phoneNumbersDataMap = capturePhoneNumbersAndComments(homePhoneTextFieldHolder, homePhoneNumberControllerList);
        member.setHomePhone(phoneNumbersDataMap.get("PHONE_NUMBERS"));
        member.setHomePhoneComments(phoneNumbersDataMap.get("PHONE_COMMENTS"));
        member.setHomeNoVM(YesNo.fromDatabaseName(((JFXToggleButton) leaveHomeVMGroup.getSelectedToggle()).getText()));
        phoneNumbersDataMap = capturePhoneNumbersAndComments(workPhoneTextFieldHolder, workPhoneNumberControllerList);
        member.setWorkPhone(phoneNumbersDataMap.get("PHONE_NUMBERS"));
        member.setWorkPhoneComments(phoneNumbersDataMap.get("PHONE_COMMENTS"));
        member.setWorkNoVM(YesNo.fromDatabaseName(((JFXToggleButton) leaveWorkVMGroup.getSelectedToggle()).getText()));
        member.setPreferredPhoneType(PreferredPhoneType.fromDatabaseName(((JFXToggleButton) preferredPhoneGroup.getSelectedToggle()).getText()));
        member.setOnCallingList(YesNo.fromDatabaseName(((JFXToggleButton) onCallingListGroup.getSelectedToggle()).getText()));
        member.setPriority(callPriorityComboBox.getSelectionModel().getSelectedItem());
        return member;
    }


    private Map<String, String> capturePhoneNumbersAndComments(VBox phoneTextFieldHolder,
                                                               List<PhoneNumberController> phoneNumberControllerList) {
        Map<String, String> dataMap = new HashMap<>();
        StringBuilder phoneNumbers = new StringBuilder();
        StringBuilder phoneComments = new StringBuilder();

        List<Node> nodeList = phoneTextFieldHolder.getChildren();
        int size = nodeList.size();
        for (int index = 0; index < size; index++) {
            PhoneNumberController phoneNumberController = phoneNumberControllerList.get(index);
            if (phoneNumberController.isPhoneNumberEmpty()) {
                continue;
            }
            phoneNumbers.append(phoneNumberController.getPhoneNumber()).append(",");
            phoneComments.append(phoneNumberController.getPhoneComments()).append(",");
        }
        dataMap.put("PHONE_NUMBERS", getData(phoneNumbers));
        dataMap.put("PHONE_COMMENTS", getData(phoneComments));

        return dataMap;
    }

    private void populateMemberInformation(Member member) {
        int memberId = member.getMemberId();
        Family family = member.getFamily();
        int familyId = 0;
        if (family != null) {
            familyId = member.getFamily().getFamilyId();
        }
        String firstName = member.getFirstName();
        String lastName = member.getLastName();
        String cellPhone = member.getCellPhone();
        String cellPhoneComments = member.getCellPhoneComments();
        YesNo leaveCellVM = member.getCellNoVM();
        String homePhone = member.getHomePhone();
        String homePhoneComments = member.getHomePhoneComments();
        YesNo leaveHomeVM = member.getHomeNoVM();
        String workPhone = member.getWorkPhone();
        String workPhoneExtension = member.getWorkPhoneComments();
        YesNo leaveWorkVM = member.getWorkNoVM();
        PreferredPhoneType phoneType = member.getPreferredPhoneType();
        YesNo onCallingList = member.getOnCallingList();
        int priority = member.getPriority();

        memberIdTextField.setText(CommonUtil.convertIntToString(memberId, ""));
        familyIdTextField.setText(CommonUtil.convertIntToString(familyId, ""));
        firstNameTextField.setText(firstName);
        lastNameTextField.setText(lastName);
        displayPhoneNumbers(cellPhone, cellPhoneComments, cellPhoneTextFieldHolder, cellPhoneNumberControllerList);
        for (Toggle toggle : leaveCellVMGroup.getToggles()) {
            JFXToggleButton button = (JFXToggleButton) toggle;
            if (button.getText().equalsIgnoreCase(leaveCellVM.getDatabaseName())) {
                leaveCellVMGroup.selectToggle(toggle);
            }
        }
        displayPhoneNumbers(homePhone, homePhoneComments, homePhoneTextFieldHolder, homePhoneNumberControllerList);
        for (Toggle toggle : leaveHomeVMGroup.getToggles()) {
            JFXToggleButton button = (JFXToggleButton) toggle;
            if (button.getText().equalsIgnoreCase(leaveHomeVM.getDatabaseName())) {
                leaveHomeVMGroup.selectToggle(toggle);
            }
        }
        displayPhoneNumbers(workPhone, workPhoneExtension, workPhoneTextFieldHolder, workPhoneNumberControllerList);
        for (Toggle toggle : leaveWorkVMGroup.getToggles()) {
            JFXToggleButton button = (JFXToggleButton) toggle;
            if (button.getText().equalsIgnoreCase(leaveWorkVM.getDatabaseName())) {
                leaveWorkVMGroup.selectToggle(toggle);
            }
        }
        for (Toggle toggle : preferredPhoneGroup.getToggles()) {
            JFXToggleButton button = (JFXToggleButton) toggle;
            if (button.getText().equalsIgnoreCase(phoneType.getDatabaseName())) {
                preferredPhoneGroup.selectToggle(toggle);
            }
        }
        for (Toggle toggle : onCallingListGroup.getToggles()) {
            JFXToggleButton button = (JFXToggleButton) toggle;
            if (button.getText().equalsIgnoreCase(onCallingList.getDatabaseName())) {
                onCallingListGroup.selectToggle(toggle);
            }
        }

        if (priority == 0)
            priority = 1;
        for (int index = 0; index < callPriorityObservableList.size(); index++) {
            if (priority == callPriorityObservableList.get(index)) {
                callPriorityComboBox.getSelectionModel().select(index);
            }
        }

        addMember.setText("UPDATE");
    }

    private void displayPhoneNumbers(String phoneNumbers, String phoneComments,
                                     VBox parent, List<PhoneNumberController> phoneNumberControllerList) {
        List<String> phoneNumbersList = Arrays.asList(phoneNumbers.split(","));
        List<String> phoneCommentsList = Arrays.asList(phoneComments.split(","));

        for (int index = 0; index < phoneNumbersList.size(); index++) {
            String phone = phoneNumbersList.get(index);
            String comment = phoneCommentsList.get(index);
            if (parent.getChildren().size() <= index) {
                createAdditionalPhoneNumberTextField(parent, phoneNumberControllerList);
            }
           /* System.out.println("working on index = " + index +
                    " parent size = " + parent.getChildren().size() +
                    " controller size list " + phoneNumberControllerList.size());*/
            PhoneNumberController phoneNumberController = phoneNumberControllerList.get(index);
            phoneNumberController.setPhoneNumber(phone);
            phoneNumberController.setPhoneComments(comment);
        }
    }

    @Override
    public boolean validate() {
        if (CommonUtil.isEmptyOrNull(firstNameTextField.getText())) {
            firstNameTextField.showPopOver(firstNameTextField.getErrorMessage());
            return false;
        }
        Map<String, String> phoneNumbersDataMap = capturePhoneNumbersAndComments(cellPhoneTextFieldHolder, cellPhoneNumberControllerList);
        boolean cellPhoneEmpty = CommonUtil.isEmptyOrNull(phoneNumbersDataMap.get("PHONE_NUMBERS"));

        phoneNumbersDataMap = capturePhoneNumbersAndComments(homePhoneTextFieldHolder, homePhoneNumberControllerList);
        boolean homePhoneEmpty = CommonUtil.isEmptyOrNull(phoneNumbersDataMap.get("PHONE_NUMBERS"));

        phoneNumbersDataMap = capturePhoneNumbersAndComments(workPhoneTextFieldHolder, workPhoneNumberControllerList);
        boolean workPhoneEmpty = CommonUtil.isEmptyOrNull(phoneNumbersDataMap.get("PHONE_NUMBERS"));

        if (cellPhoneEmpty && homePhoneEmpty && workPhoneEmpty) {
            showPhoneErrorMessage(cellPhoneTextFieldHolder, cellPhoneNumberControllerList);
            showPhoneErrorMessage(homePhoneTextFieldHolder, homePhoneNumberControllerList);
            showPhoneErrorMessage(workPhoneTextFieldHolder, workPhoneNumberControllerList);
            return false;
        }

        return validatePhoneNumbers(cellPhoneTextFieldHolder, cellPhoneNumberControllerList) &&
                validatePhoneNumbers(homePhoneTextFieldHolder, homePhoneNumberControllerList) &&
                validatePhoneNumbers(workPhoneTextFieldHolder, workPhoneNumberControllerList);

    }

    private boolean validatePhoneNumbers(VBox phoneTextFieldHolder,
                                         List<PhoneNumberController> phoneNumberControllerList) {
        int size = phoneTextFieldHolder.getChildren().size();
        for (int index = 0; index < size; index++) {
            PhoneNumberController phoneNumberController = phoneNumberControllerList.get(index);
            if (!phoneNumberController.isPhoneNumberEmpty() &&
                    !phoneNumberController.getPhoneNumberTextField().validate()) {
                return false;
            }
        }

        return true;
    }

    private void showPhoneErrorMessage(VBox parent, List<PhoneNumberController> phoneNumberControllerList) {
        for (int index = 0; index < parent.getChildren().size(); index++) {
            PhoneNumberController phoneNumberController = phoneNumberControllerList.get(index);
            phoneNumberController.showErrorMessage("Please provide atleast one valid contact number");
        }
    }

    private HBox getHBox(List<PhoneNumberController> phoneNumberControllerList) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/fxml/family-management/phone-numbers-hbox.fxml"));
        HBox parent;
        try {
            parent = fxmlLoader.load();
            PhoneNumberController phoneNumberController = fxmlLoader.getController();
            phoneNumberControllerList.add(phoneNumberController);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        return parent;
    }


}
