package org.rssb.phonetree.controller.familymanagement;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXToggleButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
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

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Lazy
public class MemberInformationController extends AbstractController {
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
    private DecoratedTextField cellPhoneTextField;

    @FXML
    private ToggleGroup leaveCellVMGroup;

    @FXML
    private DecoratedTextField homePhoneTextField;

    @FXML
    private ToggleGroup leaveHomeVMGroup;

    @FXML
    private DecoratedTextField workPhoneTextField;

    @FXML
    private DecoratedTextField workPhoneExtTextField;


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
        contextHolder.set("MEMBER_DETAIL", member);
        delegator.delegate(contextHolder);
    }

    @FXML
    void cancel(ActionEvent event) {

    }

    @FXML
    void addAnotherCellPhoneTextField(MouseEvent event) {
        createAdditionalTextField(cellPhoneTextFieldHolder);
    }


    @FXML
    void deleteAdditionalCellPhoneTextField(MouseEvent event) {
        List<Node> nodeList = cellPhoneTextFieldHolder.getChildren();
        int size = nodeList.size();
        for (int index = size - 1; index > 0; index--) {
            Node node = nodeList.get(index);
            if (node instanceof DecoratedTextField) {
                DecoratedTextField decoratedTextField = (DecoratedTextField) node;
                if (CommonUtil.isEmptyOrNull(decoratedTextField.getText())) {
                    nodeList.remove(index);
                    return;
                }

            }
        }
    }

    @Override
    public void refresh() {
        Member member = (Member) contextHolder.get("MEMBER_DETAIL");
        if (member == null) {
            return;
        }

        populateMemberInformation(member);
    }

    private Member collectMemberInformation() {
        Member member = (Member) contextHolder.get("MEMBER_DETAIL");
        if (member == null) {
            member = new Member();
        }
        member.setFirstName(firstNameTextField.getText());
        member.setLastName(lastNameTextField.getText());
        member.setCellPhone(cellPhoneTextField.getText());
        member.setCellNoVM(YesNo.fromDatabaseName(((JFXToggleButton) leaveCellVMGroup.getSelectedToggle()).getText()));
        member.setHomePhone(homePhoneTextField.getText());
        member.setHomeNoVM(YesNo.fromDatabaseName(((JFXToggleButton) leaveHomeVMGroup.getSelectedToggle()).getText()));
        member.setWorkPhone(workPhoneTextField.getText());
        member.setWorkNoVM(YesNo.fromDatabaseName(((JFXToggleButton) leaveWorkVMGroup.getSelectedToggle()).getText()));
        member.setPreferredPhoneType(PreferredPhoneType.fromDatabaseName(((JFXToggleButton) preferredPhoneGroup.getSelectedToggle()).getText()));
        member.setOnCallingList(YesNo.fromDatabaseName(((JFXToggleButton) onCallingListGroup.getSelectedToggle()).getText()));
        member.setPriority(callPriorityComboBox.getSelectionModel().getSelectedItem());
        return member;
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
        YesNo leaveCellVM = member.getCellNoVM();
        String homePhone = member.getHomePhone();
        YesNo leaveHomeVM = member.getHomeNoVM();
        String workPhone = member.getWorkPhone();
        YesNo leaveWorkVM = member.getWorkNoVM();
        PreferredPhoneType phoneType = member.getPreferredPhoneType();
        YesNo onCallingList = member.getOnCallingList();
        int priority = member.getPriority();

        memberIdTextField.setText(CommonUtil.convertIntToString(memberId, ""));
        familyIdTextField.setText(CommonUtil.convertIntToString(familyId, ""));
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

        if (priority == 0)
            priority = 1;
        for (int index = 0; index < callPriorityObservableList.size(); index++) {
            if (priority == callPriorityObservableList.get(index)) {
                callPriorityComboBox.getSelectionModel().select(index);
            }
        }
    }

    private void createAdditionalTextField(VBox parent) {
        List<Node> nodeList = parent.getChildren();
        int size = nodeList.size();
        System.out.println("Size = " + size);
        for (int i = 0; i < size; i++) {
            Node node = nodeList.get(i);
            if (node instanceof DecoratedTextField) {
                DecoratedTextField decoratedTextField = createDecoratedTextField((DecoratedTextField) node);
                parent.getChildren().add(decoratedTextField);
                return;
            }
        }
    }

    private DecoratedTextField createDecoratedTextField(DecoratedTextField original) {
        DecoratedTextField decoratedTextField = new DecoratedTextField();
        decoratedTextField.setAcceptedCharactersRegex(original.getAcceptedCharactersRegex());
        decoratedTextField.setLeftGlyphIconLabelHeight(original.getLeftGlyphIconLabelHeight());
        decoratedTextField.setLeftGlyphIconLabelWidth(original.getLeftGlyphIconLabelWidth());
        decoratedTextField.setLeftGlyphIconSize(original.getLeftGlyphIconSize());
        decoratedTextField.setLeftGlyphIconName(original.getLeftGlyphIconName());
        decoratedTextField.setRightGlyphIconName(original.getRightGlyphIconName());
        decoratedTextField.setRightGlyphIconLabelHeight(original.getRightGlyphIconLabelHeight());
        decoratedTextField.setRightGlyphIconLabelWidth(original.getRightGlyphIconLabelWidth());
        decoratedTextField.setRightGlyphIconSize(original.getRightGlyphIconSize());
        decoratedTextField.setMaxLength(original.getMaxLength());
        decoratedTextField.setMinLength(original.getMinLength());
        decoratedTextField.setPhoneNumber(original.isPhoneNumber());
        decoratedTextField.setRequired(original.isRequired());
        decoratedTextField.setErrorMessage(original.getErrorMessage());
        decoratedTextField.setPrefHeight(original.getHeight());
        decoratedTextField.setPrefWidth(original.getWidth());
        decoratedTextField.setMinHeight(original.getHeight());
        decoratedTextField.setMaxHeight(original.getHeight());
        return decoratedTextField;
    }

}
