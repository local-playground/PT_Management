package org.rssb.phonetree.controller.search;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.rssb.phonetree.common.CommonUtil;
import org.rssb.phonetree.common.SearchCriteria;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.domain.SearchResult;
import org.rssb.phonetree.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class SearchController extends AbstractController  {
    @Autowired
    private SearchService searchService;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label searchByLabel;

    @FXML
    private JFXComboBox<String> searchComboBox;

    @FXML
    private JFXTextField searchTextField;

    @FXML
    private JFXButton searchButton;

    @FXML
    private TableView<SearchResult> tableView;

    @FXML
    private TableColumn<SearchResult, String> teamLeadColumn;

    @FXML
    private TableColumn<SearchResult, String> sevadarColumn;

    @FXML
    private TableColumn<SearchResult, String> firstNameColumn;

    @FXML
    private TableColumn<SearchResult, String> lastNameColumn;

    @FXML
    private TableColumn<SearchResult, String> cellPhoneColumn;

    @FXML
    private TableColumn<SearchResult, String> homePhoneColumn;

    @FXML
    private TableColumn<SearchResult, String> zipCodeColumn;

    @FXML
    private TableColumn<SearchResult, String> townColumn;

    @FXML
    private TableColumn<SearchResult, String> phoneStatusColumn;

    @FXML
    private Label closeButton;

    @FXML
    private Label recordsLabel;

    @FXML
    void close(MouseEvent event) {
        closeScreen(event);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        teamLeadColumn.setCellValueFactory(new PropertyValueFactory<>("teamLeadName"));
        sevadarColumn.setCellValueFactory(new PropertyValueFactory<>("sevadarName"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        cellPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("cellPhone"));
        homePhoneColumn.setCellValueFactory(new PropertyValueFactory<>("homePhone"));
        zipCodeColumn.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
        townColumn.setCellValueFactory(new PropertyValueFactory<>("town"));
        phoneStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        /*
        Initialize combo box
         */

        Arrays.stream(SearchCriteria.values()).
                forEach(searchCriteria -> searchComboBox.getItems().add(searchCriteria.getType()));
        searchComboBox.getSelectionModel().select(0);

        tableView.setOnMousePressed(event -> {
            if(event.isPrimaryButtonDown() && event.getClickCount()==2){
                this.contextHolder.setResponse(tableView.getSelectionModel().getSelectedItem());
                closeScreen(event);
                this.delegator.delegate(this.contextHolder);
                this.contextHolder = null;
                this.delegator = null;
            }
        });
    }

    @FXML
    void searchResults(Event event) {
        refresh();
    }

    @Override
    public void refresh() {
        String searchText = searchTextField.getText();
        if (CommonUtil.isEmptyOrNull(searchText)) {
            return;
        }

        SearchCriteria searchCriteria = Arrays.stream(SearchCriteria.values()).filter(criteria ->
                criteria.getType().equals(searchComboBox.getSelectionModel().getSelectedItem())
        ).findFirst().orElse(null);


        List<SearchResult> searchResultsFound = new ArrayList<>();

        switch (searchCriteria) {
            case FIRST_NAME:
                searchResultsFound = searchService.findFamiliesByFirstName(searchText);
                break;
            case LAST_NAME:
                searchResultsFound = searchService.findFamiliesByLastName(searchText);
                break;
            case TEAM_LEAD:
                searchResultsFound = searchService.findFamiliesByTeamLeadName(searchText);
                break;
            case SEVADAR:
                searchResultsFound = searchService.findFamiliesBySevadarName(searchText);
                break;
            case PHONE_NUMBER:
                searchResultsFound = searchService.findFamiliesByPhoneNumber(searchText);
                break;
            case PHONE_STATUS:
                searchResultsFound = searchService.findFamiliesByPhoneNumber(searchText);
                break;
            case TOWN:
                searchResultsFound = searchService.findFamiliesByTown(searchText);
                break;
            case ZIP_CODE:
                searchResultsFound = searchService.findFamiliesByZipCode(searchText);
                break;
        }

        ObservableList<SearchResult> searchResultsList = FXCollections.observableList(searchResultsFound);
        recordsLabel.setText("" + searchResultsFound.size());
        tableView.setItems(searchResultsList);
    }
}
