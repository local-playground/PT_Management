package org.rssb.phonetree.controller.familymanagement;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXToggleButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    private VBox homePhoneTextFieldHolder;

    @FXML
    private VBox workPhoneTextFieldHolder;


    @FXML
    private DecoratedTextField cellPhoneTextField;

    @FXML
    private DecoratedTextField cellPhoneCommentTextField;


    @FXML
    private ToggleGroup leaveCellVMGroup;

    @FXML
    private DecoratedTextField homePhoneTextField;

    @FXML
    private DecoratedTextField homePhoneCommentTextField;

    @FXML
    private ToggleGroup leaveHomeVMGroup;

    @FXML
    private DecoratedTextField workPhoneTextField;

    @FXML
    private DecoratedTextField workPhoneExtensionTextField;


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
        if (!validate()) {
            return;
        }
        Member member = collectMemberInformation();
        contextHolder.set("MEMBER_DETAIL", member);
        delegator.delegate(contextHolder);
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
        createAdditionalTextField(cellPhoneTextFieldHolder);
    }

    @FXML
    void addAnotherHomePhoneTextField(MouseEvent event) {
        createAdditionalTextField(homePhoneTextFieldHolder);
    }

    @FXML
    void addAnotherWorkPhoneTextField(MouseEvent event) {
        createAdditionalTextField(workPhoneTextFieldHolder);
    }

    @FXML
    void deleteAdditionalCellPhoneTextField(MouseEvent event) {
        deleteAdditionalTextFields(cellPhoneTextFieldHolder);
    }

    @FXML
    void deleteAdditionalHomePhoneTextField(MouseEvent event) {
        deleteAdditionalTextFields(homePhoneTextFieldHolder);
    }

    @FXML
    void deleteAdditionalWorkPhoneTextField(MouseEvent event) {
        deleteAdditionalTextFields(workPhoneTextFieldHolder);
    }

    private void deleteAdditionalTextFields(VBox parent) {
        List<Node> nodeList = parent.getChildren();
        int size = nodeList.size();
        for (int index = size - 1; index > 0; index--) {
            Node node = nodeList.get(index);
            if (node instanceof HBox) {
                List<Node> hboxChildrensList = ((HBox) node).getChildren();
                if (hboxChildrensList.size() > 0) {
                    Node hboxChildNode = hboxChildrensList.get(0);
                    if (hboxChildNode instanceof DecoratedTextField) {
                        DecoratedTextField decoratedTextField = (DecoratedTextField) hboxChildNode;
                        if (CommonUtil.isEmptyOrNull(decoratedTextField.getText())) {
                            nodeList.remove(index);
                            return;
                        }
                    }
                }
            }
        }
    }


    private void createAdditionalTextField(VBox parent) {
        List<Node> nodeList = parent.getChildren();
        int size = nodeList.size();
        System.out.println("Size = " + size);
        for (int i = 0; i < size; i++) {
            Node node = nodeList.get(i);
            if (node instanceof HBox) {
                HBox originalHbox = (HBox) node;
                HBox hbox = new HBox();
                hbox.setSpacing(originalHbox.getSpacing());
                parent.getChildren().add(hbox);
                List<Node> hboxChildrensList = ((HBox) node).getChildren();
                System.out.println("creating deco text = " + hboxChildrensList.size());
                for (int index = 0; index < hboxChildrensList.size(); index++) {
                    Node hboxChildNode = hboxChildrensList.get(index);
                    if (hboxChildNode instanceof DecoratedTextField) {
                        DecoratedTextField decoratedTextField = createDecoratedTextField((DecoratedTextField) hboxChildNode);
                        hbox.getChildren().add(decoratedTextField);
                    }
                }
                return;
            }

        }
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

        StringBuilder phoneNumbers = new StringBuilder();
        StringBuilder phoneComments = new StringBuilder();
        capturePhoneNumbers(cellPhoneTextFieldHolder, phoneNumbers, phoneComments);
        member.setCellPhone(getData(phoneNumbers));
        member.setCellPhoneComments(getData(phoneComments));
        member.setCellNoVM(YesNo.fromDatabaseName(((JFXToggleButton) leaveCellVMGroup.getSelectedToggle()).getText()));
        phoneNumbers.delete(0, phoneNumbers.length());
        phoneComments.delete(0, phoneNumbers.length());

        capturePhoneNumbers(homePhoneTextFieldHolder, phoneNumbers, phoneComments);
        member.setHomePhone(getData(phoneNumbers));
        member.setHomePhoneComments(getData(phoneComments));
        member.setHomeNoVM(YesNo.fromDatabaseName(((JFXToggleButton) leaveHomeVMGroup.getSelectedToggle()).getText()));
        phoneNumbers.delete(0, phoneNumbers.length());
        phoneComments.delete(0, phoneNumbers.length());

        capturePhoneNumbers(workPhoneTextFieldHolder, phoneNumbers, phoneComments);
        member.setWorkPhone(getData(phoneNumbers));
        member.setWorkPhoneComments(getData(phoneComments));
        member.setWorkNoVM(YesNo.fromDatabaseName(((JFXToggleButton) leaveWorkVMGroup.getSelectedToggle()).getText()));
        phoneNumbers.delete(0, phoneNumbers.length());
        phoneComments.delete(0, phoneNumbers.length());

        member.setPreferredPhoneType(PreferredPhoneType.fromDatabaseName(((JFXToggleButton) preferredPhoneGroup.getSelectedToggle()).getText()));
        member.setOnCallingList(YesNo.fromDatabaseName(((JFXToggleButton) onCallingListGroup.getSelectedToggle()).getText()));
        member.setPriority(callPriorityComboBox.getSelectionModel().getSelectedItem());
        return member;
    }

    private void capturePhoneNumbers(VBox parent, StringBuilder phoneNumbers, StringBuilder phoneComments) {
        List<Node> nodeList = parent.getChildren();
        int size = nodeList.size();
        for (int index = 0; index < size; index++) {
            Node node = nodeList.get(index);
            if (node instanceof HBox) {
                List<Node> hboxChildrensList = ((HBox) node).getChildren();
                if (hboxChildrensList.size() == 2) {
                    Node phoneNumberNode = hboxChildrensList.get(0);
                    Node phoneCommentsNode = hboxChildrensList.get(1);
                    if (phoneNumberNode instanceof DecoratedTextField) {
                        String text = ((DecoratedTextField) phoneNumberNode).getText();
                        if (CommonUtil.isEmptyOrNull(text)) {
                            continue;
                        }
                        phoneNumbers.append(text).append(",");

                        if (phoneCommentsNode instanceof DecoratedTextField) {
                            String comments = ((DecoratedTextField) phoneCommentsNode).getText();
                            if (CommonUtil.isEmptyOrNull(comments)) {
                                comments = "";
                            }
                            phoneComments.append(comments).append(",");
                        }
                    }

                }
            }
        }
    }

    private void displayPhoneNumbers(VBox parent, String[] phoneNumbers, String[] phoneComments) {
        List<Node> nodeList = parent.getChildren();
        int size = nodeList.size();
        for (int index = 0; index < size; index++) {
            Node node = nodeList.get(index);
            if (node instanceof HBox) {
                List<Node> hboxChildrensList = ((HBox) node).getChildren();
                if (hboxChildrensList.size() == 2) {
                    Node phoneNumberNode = hboxChildrensList.get(0);
                    Node phoneCommentsNode = hboxChildrensList.get(1);
                    if (phoneNumberNode instanceof DecoratedTextField) {
                        ((DecoratedTextField) phoneNumberNode).setText(phoneNumbers[index]);
                        if (phoneCommentsNode instanceof DecoratedTextField) {
                            if (index <= phoneComments.length) {
                                ((DecoratedTextField) phoneCommentsNode).setText(phoneComments[index]);
                            }
                        }
                    }
                }
            }
        }
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
        if (CommonUtil.isIndexOfKeyPresent(cellPhone, ",")) {
            displayPhone(cellPhone, cellPhoneComments, cellPhoneTextFieldHolder);
        } else {
            cellPhoneTextField.setText(cellPhone);
            cellPhoneCommentTextField.setText(cellPhoneComments);
        }
        for (Toggle toggle : leaveCellVMGroup.getToggles()) {
            JFXToggleButton button = (JFXToggleButton) toggle;
            if (button.getText().equalsIgnoreCase(leaveCellVM.getDatabaseName())) {
                leaveCellVMGroup.selectToggle(toggle);
            }
        }
        if (CommonUtil.isIndexOfKeyPresent(homePhone, ",")) {

        } else {
            homePhoneTextField.setText(homePhone);
            homePhoneCommentTextField.setText(homePhoneComments);
        }
        for (Toggle toggle : leaveHomeVMGroup.getToggles()) {
            JFXToggleButton button = (JFXToggleButton) toggle;
            if (button.getText().equalsIgnoreCase(leaveHomeVM.getDatabaseName())) {
                leaveHomeVMGroup.selectToggle(toggle);
            }
        }
        if (CommonUtil.isIndexOfKeyPresent(workPhone, ",")) {

        } else {
            workPhoneTextField.setText(workPhone);
            workPhoneExtensionTextField.setText(workPhoneExtension);
        }
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

    private void displayPhone(String phone, String phoneComments, VBox parent) {
        String[] phoneArray = phone.split(",");
        String[] phoneCommentsArray = phoneComments.split(",");

        System.out.println("parent spacing is " + parent.getSpacing());

        if (phoneArray.length > 1) {
            for (int i = 1; i < phoneArray.length; i++) {
                createAdditionalTextField(parent);
            }
        }

        //displayPhoneNumbers(parent,phoneArray,phoneCommentsArray);

    }

    private DecoratedTextField createDecoratedTextField(DecoratedTextField original) {
        System.out.println("Now creating one from original " + original);
        DecoratedTextField decoratedTextField = new DecoratedTextField();
        decoratedTextField.setPromptText(original.getPromptText());
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

        System.out.println("orignial height="+original.getHeight()+
        " width = "+original.getWidth());
        decoratedTextField.setPrefHeight(original.getHeight());
        decoratedTextField.setPrefWidth(original.getWidth());
        decoratedTextField.setMinHeight(original.getHeight());
        decoratedTextField.setMaxHeight(original.getHeight());
        return decoratedTextField;
    }

    private boolean validate() {
        if (CommonUtil.isEmptyOrNull(firstNameTextField.getText())) {
            firstNameTextField.showPopOver(firstNameTextField.getErrorMessage());
            return false;
        }

        boolean cellPhoneEmtpy = CommonUtil.isEmptyOrNull(cellPhoneTextField.getText());
        boolean homePhoneEmpty = CommonUtil.isEmptyOrNull(homePhoneTextField.getText());
        boolean workPhoneEmpty = CommonUtil.isEmptyOrNull(workPhoneTextField.getText());
        if (cellPhoneEmtpy && homePhoneEmpty && workPhoneEmpty) {
            cellPhoneTextField.showPopOver("Please provide atleast one valid contact number");
            homePhoneTextField.showPopOver("Please provide atleast one valid contact number");
            workPhoneTextField.showPopOver("Please provide atleast one valid contact number");
            return false;
        }
        return true;
    }

}
