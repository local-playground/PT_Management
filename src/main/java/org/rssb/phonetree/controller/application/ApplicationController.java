package org.rssb.phonetree.controller.application;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.spring.config.SpringFXMLLoader;
import org.rssb.phonetree.ui.view.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
@Lazy
public class ApplicationController extends AbstractController{

    @FXML
    private BorderPane applicationRootPane;

    @Autowired
    private SpringFXMLLoader springFXMLLoader;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        switchScreen(FxmlView.PHONE_TREE_MANAGEMENT_MAIN.getFxmlFile());
    }

    private void switchScreen(String fxmlFile) {
        try {
            Parent parent = springFXMLLoader.load(fxmlFile);
            BorderPane borderPane = (BorderPane) applicationRootPane;
            borderPane.setCenter(parent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
