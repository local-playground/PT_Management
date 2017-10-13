package org.rssb.phonetree.controller.familymanagement;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.controls.JFXToggleButton;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import org.rssb.phonetree.common.CommonUtil;
import org.rssb.phonetree.common.Constants;
import org.rssb.phonetree.common.ContextHolder;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.domain.SearchResult;
import org.rssb.phonetree.entity.Family;
import org.rssb.phonetree.entity.Member;
import org.rssb.phonetree.entity.Sevadar;
import org.rssb.phonetree.entity.TeamLead;
import org.rssb.phonetree.entity.emums.BusRide;
import org.rssb.phonetree.entity.emums.CallStatus;
import org.rssb.phonetree.entity.emums.YesNo;
import org.rssb.phonetree.services.FamilyService;
import org.rssb.phonetree.services.SevadarService;
import org.rssb.phonetree.spring.config.SpringFXMLLoader;
import org.rssb.phonetree.ui.view.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

@Component
@Lazy
public class FamilyManagementController extends AbstractController {
    @Autowired
    private SpringFXMLLoader springFXMLLoader;

    @FXML
    private AnchorPane familyMgmtRootPane;

    @FXML
    private Label teamLeadNameLabel;

    @FXML
    private JFXComboBox<Sevadar> sevadarNameComboBox;

    @FXML
    private JFXButton saveFamily;

    @FXML
    private JFXButton deleteFamily;

    @FXML
    private JFXButton resetForm;

    @FXML
    private JFXTextField zipCodeTextField;

    @FXML
    private JFXTextField noOfAdultsAttendsSNVTextField;

    @FXML
    private JFXTextField noOfChildrenAttendsSNVTextField;

    @FXML
    private JFXTextField townTextField;

    @FXML
    private ToggleGroup SNVGuidelineGroup;

    @FXML
    private ToggleGroup needBusRideGroup;

    @FXML
    private JFXTextField noOfPassengersTextField;

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
        ContextHolder contextHolder = createContextHolder("", null, getRootPanel());
        //setOpacity(Constants.LOW_OPACITY, contextHolder);
        try {
            Parent parent = springFXMLLoader.load(FxmlView.ADD_MEMBER_INFORMATION.getFxmlFile(),this::addMember,contextHolder);
            jfxDrawer.setSidePane(parent);
            jfxDrawer.setOverLayVisible(false);
            jfxDrawer.open();
            jfxDrawer.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
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

    private void addMember(ContextHolder contextHolder){

    }

    private void showDetails(ContextHolder contextHolder) {
        SearchResult selectedResult = (SearchResult) contextHolder.get(Constants.RESPONSE_OBJ);
        Family family = familyService.findByFamilyId(selectedResult.getFamilyId()).get();
        setFamilyData(family);
    }

    @Override
    public Parent getRootPanel() {
        return familyMgmtRootPane;
    }


    private Family extractAndBuildFamily() {
        Family family = new Family();

        return family;
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

        teamLeadNameLabel.setText(teamLead.getTeamLeadName());
        for(int index=0;index<sevadarObservableList.size();index++){
            Sevadar comboBoxSevadar = sevadarObservableList.get(index);
            if(comboBoxSevadar.getSevadarName().equalsIgnoreCase(sevadar.getSevadarName())){
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
                if(button.getText().equalsIgnoreCase("No")){
                    if(CommonUtil.isNotEmptyOrNull(callSpecificTime) || !callSpecificTime.equalsIgnoreCase("No")) {
                        String fromTime = callSpecificTime.substring(0, callSpecificTime.indexOf('-'));
                        String toTime = callSpecificTime.substring(callSpecificTime.indexOf('-') + 1);
                        int fromTimeHours ;
                        int fromTimeIndex=(fromTime.indexOf("AM")!=-1?fromTime.indexOf("AM"):-1);
                        if(fromTimeIndex==-1){
                            fromTimeIndex=(fromTime.indexOf("PM")!=-1? fromTime.indexOf("PM"):-1);
                            fromTimeHours = 12+Integer.parseInt(fromTime.substring(0,fromTimeIndex));
                        }else{
                            fromTimeHours=Integer.parseInt(fromTime.substring(0,fromTimeIndex));
                        }

                        int toTimeHours;
                        int toTimeIndex=(toTime.indexOf("AM")!=-1?toTime.indexOf("AM"):-1);

                        if(toTimeIndex==-1){
                            toTimeIndex = (toTime.indexOf("PM")!=-1? toTime.indexOf("PM"):-1);
                            toTimeHours = 12+Integer.parseInt(toTime.substring(0,fromTimeIndex));
                        }else{
                            toTimeHours=Integer.parseInt(toTime.substring(0,toTimeIndex));
                        }

                        fromTimePicker.setValue(LocalTime.of(fromTimeHours,0));
                        toTimePicker.setValue(LocalTime.of(toTimeHours,0));
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
                        .findFirst().get();

            }
        });

        familyIdTableColumn.setCellValueFactory(param -> new SimpleStringProperty(String.valueOf(param.getValue().getFamily().getFamilyId())));
        teamLeadNameLabel.setText("");
    }
}
