package org.rssb.phonetree.controller.sevadar;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.util.StringConverter;
import org.rssb.phonetree.common.CommonUtil;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.entity.Sevadar;
import org.rssb.phonetree.services.SevadarService;
import org.rssb.phonetree.status.SevadarActionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
@Lazy
@SuppressWarnings("unused")
public class SwapSevadarController extends AbstractController{

    @FXML
    private StackPane swapSevadarsRootPane;


    @Autowired
    private SevadarService sevadarService;

    @FXML
    private JFXComboBox<Sevadar> swapSevadarComboBox;

    @FXML
    private JFXComboBox<Sevadar> swapSevadarWithComboBox;

    @FXML
    private JFXButton swapSevadarsButton;

    @FXML
    private Label closeButton;

    private ObservableList<Sevadar> swapSevadarsList;
    private ObservableList<Sevadar> swapSevadarsWithList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Sevadar> sevadarList = sevadarService.findAllSevadars();
        swapSevadarsList = FXCollections.observableArrayList(sevadarList);
        swapSevadarsWithList = FXCollections.observableArrayList(sevadarList);

        swapSevadarComboBox.getItems().addAll(swapSevadarsList);
        swapSevadarWithComboBox.getItems().addAll(swapSevadarsWithList);

        swapSevadarComboBox.setConverter(new StringConverter<Sevadar>() {
            @Override
            public String toString(Sevadar sevadar) {
                return sevadar.getSevadarName();
            }

            @Override
            public Sevadar fromString(String sevadarName) {
                return swapSevadarsList
                        .stream()
                        .filter(sevadar -> sevadar.getSevadarName().equalsIgnoreCase(sevadarName))
                        .findFirst().get();

            }
        });

        swapSevadarWithComboBox.setConverter(new StringConverter<Sevadar>() {
            @Override
            public String toString(Sevadar sevadar) {
                return sevadar.getSevadarName();
            }

            @Override
            public Sevadar fromString(String sevadarName) {
                return swapSevadarsList
                        .stream()
                        .filter(sevadar -> sevadar.getSevadarName().equalsIgnoreCase(sevadarName))
                        .findFirst().get();

            }
        });
    }


    @FXML
    void closeButton(MouseEvent event) {
        closeScreen(event,this.contextHolder);
    }

    @FXML
    void swapSevadars(ActionEvent event) {
        Sevadar swapSevadar = swapSevadarComboBox.getSelectionModel().getSelectedItem();
        Sevadar swapSevadarWith = swapSevadarWithComboBox.getSelectionModel().getSelectedItem();
        if(swapSevadar.getSevadarsId() == swapSevadarWith.getSevadarsId()){
           /* Alert alert = CommonUtil.getAlert("Please choose different Sevadars to Swap.", ActionAlertType.ERROR);
            Stage stage = (Stage)alert.getDialogPane().getScene().getWindow();
            stage.setAlwaysOnTop(true);
            alert.showAndWait();*/
            CommonUtil.showNoActionNeededJFXDialog(this::getRootPanel,
                    null,
                    SevadarActionResponse.SEVADAR_SELECT_DIFFERENT_SEVADARS_TO_SWAP);
            return;
        }

        if(swapSevadar.getTeamLead().getTeamLeadId() == swapSevadarWith.getTeamLead().getTeamLeadId()){
            CommonUtil.showNoActionNeededJFXDialog(this::getRootPanel,
                    null,
                    SevadarActionResponse.SEVADAR_CANNOT_SWAP_SEVADARS_UNDER_SAME_TEAM_LEAD);
            return;
        }


        this.contextHolder.set(
                new String[]{"SWAP_SEVADAR","SWAP_SEVADAR_WITH"},
                new Object[]{swapSevadar,swapSevadarWith});
        closeScreen(event,contextHolder);
        this.delegator.delegate(this.contextHolder);
        this.contextHolder = null;
        this.delegator = null;
    }

    @Override
    public Parent getRootPanel(){
        return swapSevadarsRootPane;
    }
}
