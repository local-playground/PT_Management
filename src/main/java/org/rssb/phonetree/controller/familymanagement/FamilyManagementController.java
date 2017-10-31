package org.rssb.phonetree.controller.familymanagement;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.controls.JFXToggleButton;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.StringConverter;
import org.rssb.phonetree.common.CommonUtil;
import org.rssb.phonetree.common.Constants;
import org.rssb.phonetree.common.ContextHolder;
import org.rssb.phonetree.common.Response;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.custom.controls.DecoratedTextField;
import org.rssb.phonetree.domain.SearchResult;
import org.rssb.phonetree.entity.Family;
import org.rssb.phonetree.entity.FamilyBuilder;
import org.rssb.phonetree.entity.Member;
import org.rssb.phonetree.entity.Sevadar;
import org.rssb.phonetree.entity.TeamLead;
import org.rssb.phonetree.entity.emums.BusRide;
import org.rssb.phonetree.entity.emums.CallStatus;
import org.rssb.phonetree.entity.emums.YesNo;
import org.rssb.phonetree.services.FamilyService;
import org.rssb.phonetree.services.SevadarService;
import org.rssb.phonetree.spring.config.SpringFXMLLoader;
import org.rssb.phonetree.status.FamilyActionResponse;
import org.rssb.phonetree.ui.view.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

@Component
@Lazy
public class FamilyManagementController extends AbstractController {
    @Autowired
    private SpringFXMLLoader springFXMLLoader;

    @FXML
    private StackPane familyManagementRootPane;

    @FXML
    private TitledPane sevadarsFeedbackTitledPane;

    @FXML
    private TitledPane familyInformationTitledPane;

    @FXML
    private Accordion familyMgmtAccordion;

    @FXML
    private AnchorPane familyMgmtRootPane;

    @FXML
    private Label teamLeadNameLabel;

    @FXML
    private DecoratedTextField familyIdTextField;

    @FXML
    private JFXComboBox<Sevadar> sevadarNameComboBox;

    @FXML
    private JFXButton saveFamily;

    @FXML
    private JFXButton deleteFamily;

    @FXML
    private JFXButton resetForm;

    @FXML
    private DecoratedTextField zipCodeTextField;

    @FXML
    private DecoratedTextField noOfAdultsAttendsSNVTextField;

    @FXML
    private DecoratedTextField noOfChildrenAttendsSNVTextField;

    @FXML
    private DecoratedTextField townTextField;

    @FXML
    private ToggleGroup SNVGuidelineGroup;

    @FXML
    private ToggleGroup needBusRideGroup;

    @FXML
    private DecoratedTextField noOfPassengersTextField;

    @FXML
    private ToggleGroup canCallAnytimeGroup;

    @FXML
    private JFXTimePicker fromTimePicker;

    @FXML
    private JFXTimePicker toTimePicker;

    @FXML
    private ToggleGroup phoneStatusGroup;

    @FXML
    private ToggleGroup familyActiveGroup;

    @FXML
    private JFXTextArea internalCommentsTextArea;

    @FXML
    private JFXTextArea commentsTextArea;

    @FXML
    private JFXButton addMemberButton;

    @FXML
    private JFXButton deleteMemberButton;

    @FXML
    private JFXButton moveUnderOtherFamilyButton;

    @FXML
    private JFXButton moveAsSeparateFamilyButton;

    @FXML
    private JFXButton addSelectedMembersToBusButton;

    @FXML
    private TableView<Member> membersTableView;

    @FXML
    private TableColumn<Member, String> familyIdTableColumn;

    @FXML
    private JFXDrawer jfxDrawer;


    @Autowired
    private MemberInformationController memberInformationController;

    @Autowired
    private FamilyService familyService;

    @Autowired
    private SevadarService sevadarService;

    private ObservableList<Sevadar> sevadarObservableList;


    @FXML
    void searchFamily(MouseEvent event) {
        ContextHolder contextHolder = createContextHolder("", null, getRootPanel());
        setOpacity(Constants.LOW_OPACITY, contextHolder);
        stageManager.switchScene(FxmlView.SEARCH, this::showDetails, contextHolder, true);
    }

    @FXML
    void addMember(ActionEvent event) {
        ContextHolder contextHolder = createContextHolder(new String[]{"JFX_DRAWER"},
                new Object[]{jfxDrawer}, getRootPanel());
        openDrawer(contextHolder);
    }

    private void openDrawer(ContextHolder contextHolder) {
        try {
            Parent parent = springFXMLLoader.load(FxmlView.ADD_MEMBER_INFORMATION.getFxmlFile(), this::addMember, contextHolder);
            jfxDrawer.setSidePane(parent);
            jfxDrawer.setOverLayVisible(false);
            jfxDrawer.open();
            jfxDrawer.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        memberInformationController.refresh();
    }

    @FXML
    void addSelectedMembersToBus(ActionEvent event) {

    }

    @FXML
    void deleteFamily(ActionEvent event) {
        int familyId = CommonUtil.convertStringToInt(familyIdTextField.getText(), 0);
        if (familyId == 0) {
            CommonUtil.showNoActionNeededJFXDialog(this, null, FamilyActionResponse.FAMILY_SELECT_BEFORE_ACTION);
            return;
        }

        CommonUtil.showConfirmationJFXDialog(this,
                new Object[]{familyId},
                FamilyActionResponse.FAMILY_CONFIRM_BEFORE_REMOVE,
                null,
                contextHolder1 -> {
                    Response response = familyService.deleteFamily(familyId);
                    //refresh();
                    return response;
                });

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
    void changeTeamLeadName(ActionEvent event) {
        Sevadar sevadar = sevadarNameComboBox.getSelectionModel().getSelectedItem();
        if (sevadar != null) {
            teamLeadNameLabel.setText(sevadar.getTeamLead().getTeamLeadName());
        }
    }

    @FXML
    void saveFamily(ActionEvent event) {
        if (!validate()) {
            return;
        }

        Family family = extractAndBuildFamily();
        Response respone = familyService.saveToDatabase(family);
        CommonUtil.handleResponse(this, respone, contextHolder, null);
    }

    @Override
    public boolean validate() {
        Sevadar sevadar = sevadarNameComboBox.getSelectionModel().getSelectedItem();
        if (sevadar == null) {
            CommonUtil.showPopOver("Please assign this family to Sevadar", sevadarNameComboBox);
            return false;
        }

        if (membersTableView.getItems().size() == 0) {
            CommonUtil.showPopOver("Please add members to this family", membersTableView);
            return false;
        }

        return true;
    }

    private Predicate<Member> isFirstNameMatched(String firstName) {
        return p -> p.getFirstName().equalsIgnoreCase(firstName);
    }

    private Predicate<Member> isMemberIdMatched(int memberId) {
        return p -> p.getMemberId() == memberId;
    }


    private void matchMember(ObservableList<Member> list, List<Predicate<Member>> predicateList, Member memberToReplace) {
        boolean found = false;
        for (int index = 0; index < list.size(); index++) {
            if (found) {
                break;
            }
            Member member = list.get(index);
            for (Predicate p : predicateList) {
                if (p.test(member)) {
                    list.remove(index);
                    list.add(index, memberToReplace);
                    found = true;
                    break;
                }
            }
        }

        if (!found) {
            list.add(memberToReplace);
        }
        membersTableView.refresh();
        jfxDrawer.close();
        jfxDrawer.setVisible(false);
    }

    private void addMember(ContextHolder contextHolder) {
        Member updatedMember = (Member) contextHolder.get("MEMBER_DETAIL");
        System.out.println("rcvd member = " + updatedMember);
        ObservableList<Member> list = membersTableView.getItems();
        List<Predicate<Member>> predicateList = Arrays.asList(isMemberIdMatched(updatedMember.getMemberId()),
                isFirstNameMatched(updatedMember.getFirstName()));

        matchMember(list, predicateList, updatedMember);
        /*boolean found = true;
        found = matchMember(list, isMemberIdMatched(updatedMember.getMemberId()), updatedMember);
        if (found) {
            membersTableView.refresh();
            return;
        }
        found = matchMember(list, isFirstNameMatched(updatedMember.getFirstName()), updatedMember);
        if (found) {
            membersTableView.refresh();
            return;
        }*/

       /* list.add(updatedMember);

        membersTableView.refresh();

        jfxDrawer.close();
        jfxDrawer.setVisible(false);*/
    }

    private void showDetails(ContextHolder contextHolder) {
        SearchResult selectedResult = (SearchResult) contextHolder.get(Constants.RESPONSE_OBJ);
        Optional<Family> family = familyService.findByFamilyId(selectedResult.getFamilyId());
        if (family.isPresent()) {
            setFamilyData(family.get());
        }
    }

    @Override
    public Parent getRootPanel() {
        return familyManagementRootPane;
    }


    private Family extractAndBuildFamily() {
        Family family = new FamilyBuilder()
                .setZipCode(CommonUtil.ifEmptyOrNullReturnDefault(zipCodeTextField.getText(), ""))
                .setNoOfAdults(CommonUtil.convertStringToInt(noOfAdultsAttendsSNVTextField.getText(), 0))
                .setNoOfChildren(CommonUtil.convertStringToInt(noOfChildrenAttendsSNVTextField.getText(), 0))
                .setTown(CommonUtil.ifEmptyOrNullReturnDefault(townTextField.getText(), ""))
                .setActive(YesNo.fromDatabaseName(getValueFromToggleGroup(familyActiveGroup)))
                .setBusRide(BusRide.fromShortName(getValueFromToggleGroup(needBusRideGroup)))
                .setNoOfPassengers(CommonUtil.convertStringToInt(noOfPassengersTextField.getText(), 0))
                .setCallStatus(CallStatus.fromDatabaseName(getValueFromToggleGroup(phoneStatusGroup)))
                .setCanCallAnytime(YesNo.fromDatabaseName(getValueFromToggleGroup(canCallAnytimeGroup)))
                .setCallSpecificTime(captureSpecificTimePickerValues())
                .setComments(CommonUtil.ifEmptyOrNullReturnDefault(commentsTextArea.getText(), ""))
                .setInternalNote(CommonUtil.ifEmptyOrNullReturnDefault(internalCommentsTextArea.getText(), ""))
                .setSevadar(sevadarNameComboBox.getSelectionModel().getSelectedItem())
                .setTeamLead(sevadarNameComboBox.getSelectionModel().getSelectedItem().getTeamLead())
                .setSNVGuidelines(YesNo.fromDatabaseName(getValueFromToggleGroup(SNVGuidelineGroup)))
                .setMembersList(membersTableView.getItems())
                .build();
        family.setFamilyId(CommonUtil.convertStringToInt(familyIdTextField.getText(), 0));
        System.out.println("Captured family info " + family);
        return family;
    }

    private String captureSpecificTimePickerValues() {
        String toTime = null, fromTime = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        if (fromTimePicker != null) {
            fromTime = fromTimePicker.getValue() == null ? "" : formatter.format(fromTimePicker.getValue());
        }
        if (toTimePicker != null) {
            toTime = toTimePicker.getValue() == null ? "" : formatter.format(toTimePicker.getValue());
        }

        if (CommonUtil.isNotEmptyOrNull(fromTime) && CommonUtil.isNotEmptyOrNull(toTime)) {
            return fromTime + "-" + toTime;
        }

        return "";
    }

    private String getValueFromToggleGroup(ToggleGroup group) {
        return ((JFXToggleButton) group.getSelectedToggle()).getText();
    }

    private void setFamilyData(Family family) {
        TeamLead teamLead = family.getTeamLead();
        Sevadar sevadar = family.getSevadar();

        int familyId = family.getFamilyId();
        String zipCode = family.getZipCode();
        Integer noOfAdults = family.getNoOfAdults();
        Integer noOfChildren = family.getNoOfChildren();
        String town = family.getTown();

        String comments = family.getComments();
        String internalComments = family.getInternalNote();
        BusRide busRide = family.getBusRide();
        Integer noOfPassengers = family.getNoOfPassengers();
        CallStatus callStatus = family.getCallStatus();
        YesNo active = family.getActive();
        YesNo SNVGuideLines = family.getSNVGuidelines();
        YesNo callAnyTime = family.getCanCallAnytime();
        String callSpecificTime = family.getCallSpecificTime();

        List<Member> memberList = family.getMembersList();

        familyIdTextField.setText(String.valueOf(familyId));

        teamLeadNameLabel.setText(teamLead.getTeamLeadName());
        for (int index = 0; index < sevadarObservableList.size(); index++) {
            Sevadar comboBoxSevadar = sevadarObservableList.get(index);
            if (comboBoxSevadar.getSevadarName().equalsIgnoreCase(sevadar.getSevadarName())) {
                sevadarNameComboBox.getSelectionModel().select(index);
            }
        }

        zipCodeTextField.setText(zipCode);
        if (noOfAdults == null) {
            noOfAdults = 0;
        }
        String adultsAttendSNV = noOfAdults == 0 ? "" : String.valueOf(noOfAdults);
        noOfAdultsAttendsSNVTextField.setText(adultsAttendSNV);

        if (noOfChildren == null) {
            noOfChildren = 0;
        }
        String childrenAttendSNV = noOfChildren == 0 ? "" : String.valueOf(noOfChildren);
        noOfChildrenAttendsSNVTextField.setText(childrenAttendSNV);

        townTextField.setText(town);
        commentsTextArea.setText(comments);
        internalCommentsTextArea.setText(internalComments);
        for (Toggle toggle : needBusRideGroup.getToggles()) {
            JFXToggleButton button = (JFXToggleButton) toggle;
            if (button.getText().equalsIgnoreCase(busRide.getShortName())) {
                needBusRideGroup.selectToggle(toggle);
            }
        }
        for (Toggle toggle : canCallAnytimeGroup.getToggles()) {
            JFXToggleButton button = (JFXToggleButton) toggle;
            if (button.getText().equalsIgnoreCase(callAnyTime.getDatabaseName())) {
                canCallAnytimeGroup.selectToggle(toggle);
                if (button.getText().equalsIgnoreCase("No")) {
                    if (CommonUtil.isNotEmptyOrNull(callSpecificTime) || !callSpecificTime.equalsIgnoreCase("No")) {
                        String fromTime = callSpecificTime.substring(0, callSpecificTime.indexOf('-'));
                        String toTime = callSpecificTime.substring(callSpecificTime.indexOf('-') + 1);
                        int fromTimeHours;
                        int fromTimeIndex = (fromTime.indexOf("AM"));
                        if (fromTimeIndex == -1) {
                            fromTimeIndex = (fromTime.indexOf("PM"));
                            fromTimeHours = 12 + Integer.parseInt(fromTime.substring(0, fromTimeIndex));
                        } else {
                            fromTimeHours = Integer.parseInt(fromTime.substring(0, fromTimeIndex));
                        }

                        int toTimeHours;
                        int toTimeIndex = (toTime.indexOf("AM"));

                        if (toTimeIndex == -1) {
                            toTimeIndex = (toTime.indexOf("PM"));
                            toTimeHours = 12 + Integer.parseInt(toTime.substring(0, toTimeIndex));
                        } else {
                            toTimeHours = Integer.parseInt(toTime.substring(0, toTimeIndex));
                        }

                        fromTimePicker.setValue(LocalTime.of(fromTimeHours, 0));
                        toTimePicker.setValue(LocalTime.of(toTimeHours, 0));
                    }
                }
            }
        }

        for (Toggle toggle : phoneStatusGroup.getToggles()) {
            JFXToggleButton button = (JFXToggleButton) toggle;
            if (button.getText().equalsIgnoreCase(callStatus.getDatabaseName())) {
                phoneStatusGroup.selectToggle(toggle);
            }
        }
        if (noOfPassengers == null) {
            noOfPassengers = 0;
        }
        String passengers = noOfPassengers == 0 ? "" : String.valueOf(noOfPassengers);
        noOfPassengersTextField.setText(passengers);

        for (Toggle toggle : familyActiveGroup.getToggles()) {
            JFXToggleButton button = (JFXToggleButton) toggle;
            if (button.getText().equalsIgnoreCase(active.getDatabaseName())) {
                familyActiveGroup.selectToggle(toggle);
            }
        }

        for (Toggle toggle : SNVGuidelineGroup.getToggles()) {
            JFXToggleButton button = (JFXToggleButton) toggle;
            if (button.getText().equalsIgnoreCase(SNVGuideLines.getDatabaseName())) {
                SNVGuidelineGroup.selectToggle(toggle);
            }
        }
        ObservableList<Member> memberObservableList = FXCollections.observableArrayList(memberList);
        membersTableView.setItems(memberObservableList);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Sevadar> sevadarList = sevadarService.findAllSevadars();
        sevadarObservableList = FXCollections.observableArrayList(sevadarList);
        sevadarNameComboBox.getItems().addAll(sevadarObservableList);
        sevadarNameComboBox.setConverter(new StringConverter<Sevadar>() {
            @Override
            public String toString(Sevadar sevadar) {
                return sevadar.getSevadarName();
            }

            @Override
            public Sevadar fromString(String sevadarName) {
                return sevadarObservableList
                        .stream()
                        .filter(sevadar -> sevadar.getSevadarName().equalsIgnoreCase(sevadarName))
                        .findFirst().orElseGet(null);

            }
        });

        familyIdTableColumn.setCellValueFactory(param -> {
            if (param.getValue().getFamily() != null) {
                return new SimpleStringProperty(String.valueOf(param.getValue().getFamily().getFamilyId()));
            }

            return new SimpleStringProperty("");
        });
        teamLeadNameLabel.setText("");


        membersTableView.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                Member member = membersTableView.getSelectionModel().getSelectedItem();
                ContextHolder contextHolder = createContextHolder(new String[]{"MEMBER_DETAIL", "JFX_DRAWER"},
                        new Object[]{member, jfxDrawer}, getRootPanel());
                openDrawer(contextHolder);
            }
        });

        familyMgmtAccordion.setExpandedPane(familyInformationTitledPane);
    }
}
