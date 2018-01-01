package org.rssb.phonetree.controller.generate.phonetreelist;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
import org.controlsfx.control.CheckComboBox;
import org.rssb.phonetree.common.file.DocumentTableColumn;
import org.rssb.phonetree.common.file.DocumentWriter;
import org.rssb.phonetree.common.file.DocumentWriterFactory;
import org.rssb.phonetree.common.file.ReportFormat;
import org.rssb.phonetree.common.file.ReportType;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.custom.controls.SevadarComboBoxFormatter;
import org.rssb.phonetree.custom.controls.TeamLeadComboBoxFormatter;
import org.rssb.phonetree.domain.SevadarPhoneTreeList;
import org.rssb.phonetree.entity.Sevadar;
import org.rssb.phonetree.entity.TeamLead;
import org.rssb.phonetree.services.FamilyService;
import org.rssb.phonetree.services.SevadarService;
import org.rssb.phonetree.services.TeamLeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
@Lazy
public class GeneratePhoneTreeListController extends AbstractController {

    @Autowired
    private TeamLeadService teamLeadService;

    @Autowired
    private SevadarService sevadarService;

    @Autowired
    private FamilyService familyService;

    @FXML
    private CheckComboBox<String> documentColumnsCheckComboBox;

    @FXML
    private ToggleGroup reportByGroup;

    @FXML
    private ToggleGroup reportFormatGroup;

    @FXML
    private CheckComboBox<TeamLead> teamLeadCheckComboBox;

    @FXML
    private CheckComboBox<Sevadar> sevadarCheckComboBox;

    @FXML
    private JFXButton createReportButton;

    @Autowired
    private DocumentWriterFactory documentWriterFactory;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Arrays.stream(DocumentTableColumn.values()).
                forEach(documentTableColumn -> documentColumnsCheckComboBox.getItems().add(documentTableColumn.getType()));

        for(String item:documentColumnsCheckComboBox.getItems()){
            DocumentTableColumn documentTableColumn = DocumentTableColumn.fromString(item);
            if(documentTableColumn.isSelected()){
                documentColumnsCheckComboBox.getCheckModel().check(item);
            }
        }
        reportByGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            String value = ((JFXToggleButton) newValue).getText();

            if (value.equalsIgnoreCase("Team Lead")) {
                teamLeadCheckComboBox.setDisable(false);
                sevadarCheckComboBox.setDisable(true);
            }
            if (value.equalsIgnoreCase("Sevadar")) {
                teamLeadCheckComboBox.setDisable(true);
                sevadarCheckComboBox.setDisable(false);
            }
            if (value.equalsIgnoreCase("All Sevadars")) {
                teamLeadCheckComboBox.setDisable(true);
                sevadarCheckComboBox.setDisable(true);
            }

        });
        ObservableList<TeamLead> teamLeadObservableList =
                FXCollections.observableArrayList(teamLeadService.findAllTeamLeads());
        teamLeadCheckComboBox.getItems().addAll(teamLeadObservableList);
        teamLeadCheckComboBox.setConverter(new TeamLeadComboBoxFormatter(teamLeadObservableList));

        teamLeadCheckComboBox.getCheckModel().getCheckedItems().addListener(new ListChangeListener<TeamLead>() {
            public void onChanged(ListChangeListener.Change<? extends TeamLead> c) {
                System.out.println(teamLeadCheckComboBox.getCheckModel().getCheckedItems());
            }
        });

        ObservableList<Sevadar> sevadarObservableList =
                FXCollections.observableArrayList(sevadarService.findAllSevadars());
        sevadarCheckComboBox.getItems().addAll(sevadarObservableList);
        sevadarCheckComboBox.setConverter(new SevadarComboBoxFormatter(sevadarObservableList));

        sevadarCheckComboBox.getCheckModel().getCheckedItems().addListener(new ListChangeListener<Sevadar>() {
            public void onChanged(ListChangeListener.Change<? extends Sevadar> c) {
                System.out.println(sevadarCheckComboBox.getCheckModel().getCheckedItems());
            }
        });
    }

    @FXML
    void createReport(ActionEvent event) {

        List<DocumentTableColumn> useColumnsToDocument =
                documentColumnsCheckComboBox.getCheckModel().getCheckedItems()
                .stream()
                .map(s -> DocumentTableColumn.fromString(s))
                .collect(Collectors.toList());

        ReportType reportType = new ReportType();
        String value = getSelectedValueFromGroup(reportFormatGroup);
        reportType.setReportFormat(ReportFormat.fromValue(value));
        reportType.setDocumentTableColumnList(useColumnsToDocument);



        if(getSelectedValueFromGroup(reportByGroup).equalsIgnoreCase("Team Lead")) {
            List<TeamLead> selectedTeamLeads =
                    teamLeadCheckComboBox.getCheckModel().getCheckedItems();
            processTeamLeadsDocuments(selectedTeamLeads,reportType);
        }
        if(getSelectedValueFromGroup(reportByGroup).equalsIgnoreCase("Sevadar")){
            List<Sevadar> selectedSevadars =
                    sevadarCheckComboBox.getCheckModel().getCheckedItems();
            processSevadarsDocuments(selectedSevadars,reportType);
        }
        if(getSelectedValueFromGroup(reportByGroup).equalsIgnoreCase("All Sevadars")){
            List<Sevadar> allSevadarsList = sevadarService.findAllSevadars();
            processSevadarsDocuments(allSevadarsList,reportType);
        }
    }

    private void processTeamLeadsDocuments(List<TeamLead> teamLeadList,ReportType reportType){
        for(TeamLead teamLead:teamLeadList){
            String teamLeadName = teamLead.getTeamLeadName();
            List<Sevadar> sevadarsList = teamLead.getSevadarsList();
            for(Sevadar sevadar:sevadarsList){
                String sevadarName = sevadar.getSevadarName();
                generateDocuments(teamLeadName,sevadarName,reportType);
            }
        }
    }

    private void processSevadarsDocuments(List<Sevadar> sevadarList,ReportType reportType){
        for(Sevadar sevadar:sevadarList){
            String sevadarName = sevadar.getSevadarName();
            String teamLeadName = sevadar.getTeamLead().getTeamLeadName();
            generateDocuments(teamLeadName,sevadarName,reportType);
        }
    }

    private void generateDocuments(String teamLeadName,String sevadarName,ReportType reportType){
        Optional<DocumentWriter> documentWriterOptional = documentWriterFactory.getDocumentWriter(reportType);
        if(documentWriterOptional.isPresent()){
            SevadarPhoneTreeList sevadarPhoneTreeList =
                    familyService.getSevadarPhoneTreeListByTeamLeadAndSevadarName(teamLeadName, sevadarName);

            documentWriterOptional.get().writeToFile(sevadarPhoneTreeList);
        }


    }
    private String getSelectedValueFromGroup(ToggleGroup group){
        return ((JFXToggleButton) group.getSelectedToggle()).getText();
    }
}
