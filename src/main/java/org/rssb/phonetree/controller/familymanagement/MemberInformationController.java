package org.rssb.phonetree.controller.familymanagement;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import org.rssb.phonetree.common.CommonUtil;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.entity.Family;
import org.rssb.phonetree.entity.Member;
import org.rssb.phonetree.entity.emums.PreferredPhoneType;
import org.rssb.phonetree.entity.emums.YesNo;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Lazy
public class MemberInformationController extends AbstractController{
    @FXML
    private JFXTextField memberIdTextField;

    @FXML
    private JFXTextField familyIdTextField;

    @FXML
    private JFXTextField firstNameTextField;

    @FXML
    private JFXTextField lastNameTextField;

    @FXML
    private JFXTextArea cellPhoneTextField;

    @FXML
    private ToggleGroup leaveCellVMGroup;

    @FXML
    private JFXTextArea homePhoneTextField;

    @FXML
    private ToggleGroup leaveHomeVMGroup;

    @FXML
    private JFXTextArea workPhoneTextField;

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
    }

    @FXML
    void addMember(ActionEvent event) {
        Member member = collectMemberInformation();
        contextHolder.set("MEMBER_DETAIL",member);
        delegator.delegate(contextHolder);
    }

    @FXML
    void cancel(ActionEvent event) {

    }

    @Override
    public void refresh(){
        Member member = (Member) contextHolder.get("MEMBER_DETAIL");
        if (member == null) {
            return;
        }

        populateMemberInformation(member);
    }

    private Member collectMemberInformation(){
        Member member = (Member) contextHolder.get("MEMBER_DETAIL");
        if(member==null){
            member=new Member();
        }
        member.setFirstName(firstNameTextField.getText());
        member.setLastName(lastNameTextField.getText());
        member.setCellPhone(cellPhoneTextField.getText());
        member.setCellNoVM(YesNo.fromDatabaseName(((JFXToggleButton)leaveCellVMGroup.getSelectedToggle()).getText()));
        member.setHomePhone(homePhoneTextField.getText());
        member.setHomeNoVM(YesNo.fromDatabaseName(((JFXToggleButton)leaveHomeVMGroup.getSelectedToggle()).getText()));
        member.setWorkPhone(workPhoneTextField.getText());
        member.setWorkNoVM(YesNo.fromDatabaseName(((JFXToggleButton)leaveWorkVMGroup.getSelectedToggle()).getText()));
        member.setPreferredPhoneType(PreferredPhoneType.fromDatabaseName(((JFXToggleButton)preferredPhoneGroup.getSelectedToggle()).getText()));
        member.setOnCallingList(YesNo.fromDatabaseName(((JFXToggleButton)onCallingListGroup.getSelectedToggle()).getText()));
        member.setPriority(callPriorityComboBox.getSelectionModel().getSelectedItem());
        return member;
    }

    private void populateMemberInformation(Member member) {
        int memberId = member.getMemberId();
        Family family = member.getFamily();
        int familyId=0;
        if(family!=null) {
            familyId = member.getFamily().getFamilyId();
        }
        String firstName= member.getFirstName();
        String lastName = member.getLastName();
        String cellPhone = member.getCellPhone();
        YesNo leaveCellVM = member.getCellNoVM();
        String homePhone = member.getHomePhone();
        YesNo leaveHomeVM = member.getHomeNoVM();
        String workPhone = member.getWorkPhone();
        YesNo leaveWorkVM = member.getWorkNoVM();
        PreferredPhoneType phoneType = member.getPreferredPhoneType();
        YesNo onCallingList = member.getOnCallingList();
        int priority = member.getPriority();

        memberIdTextField.setText(CommonUtil.convertIntToString(memberId,""));
        familyIdTextField.setText(CommonUtil.convertIntToString(familyId,""));
        firstNameTextField.setText(firstName);
        lastNameTextField.setText(lastName);
        cellPhoneTextField.setText(cellPhone);
        for (Toggle toggle : leaveCellVMGroup.getToggles()) {
            JFXToggleButton button = (JFXToggleButton) toggle;
            if (button.getText().equalsIgnoreCase(leaveCellVM.getDatabaseName())) {
                leaveCellVMGroup.selectToggle(toggle);
            }
        }
        homePhoneTextField.setText(homePhone);
        for (Toggle toggle : leaveHomeVMGroup.getToggles()) {
            JFXToggleButton button = (JFXToggleButton) toggle;
            if (button.getText().equalsIgnoreCase(leaveHomeVM.getDatabaseName())) {
                leaveHomeVMGroup.selectToggle(toggle);
            }
        }
        workPhoneTextField.setText(workPhone);
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

        if(priority==0)
            priority=1;
        for(int index=0;index<callPriorityObservableList.size();index++){
            if(priority == callPriorityObservableList.get(index)){
                callPriorityComboBox.getSelectionModel().select(index);
            }
        }
    }

}
