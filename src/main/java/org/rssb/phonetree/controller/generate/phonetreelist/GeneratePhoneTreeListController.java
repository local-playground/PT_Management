package org.rssb.phonetree.controller.generate.phonetreelist;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
import org.rssb.phonetree.controller.AbstractController;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class GeneratePhoneTreeListController extends AbstractController{
    @FXML
    private ToggleGroup reportByGroup;

    @FXML
    private ToggleGroup reportFormatGroup;

    @FXML
    private JFXButton createReportButton;

    @FXML
    void createReport(ActionEvent event) {

    }
}
