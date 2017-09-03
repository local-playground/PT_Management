package org.rssb.phonetree.controller.search;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.rssb.phonetree.domain.SearchResult;
import org.rssb.phonetree.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class SearchController implements Initializable{
    @Autowired
    private SearchService searchService;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label searchByLabel;

    @FXML
    private JFXComboBox<?> searchComboBox;

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
    void searchResults(ActionEvent event) {

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
        List<SearchResult> data = searchService.findFamiliesByFirstName("Raj");
        ObservableList<SearchResult> searchResultsList= FXCollections.observableList(data);
        tableView.setItems(searchResultsList);
    }
}
