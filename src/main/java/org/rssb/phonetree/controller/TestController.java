package org.rssb.phonetree.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.aspectj.weaver.ast.Test;
import org.rssb.phonetree.repository.TeamLeadJpaRepository;
import org.rssb.phonetree.services.SevadarService;
import org.rssb.phonetree.services.TeamLeadService;
import org.rssb.phonetree.ui.view.FxmlView;
import org.rssb.phonetree.ui.view.StageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
//@Scope("prototype")
public class TestController implements Initializable{
    @FXML
    private JFXButton testButton;

    @Autowired @Lazy
    private StageManager stageManager;

    public TestController(){
        System.out.println("Creating Test Controller..");
    }

    void testMe() {
        System.out.println("Test Me is called..");
        stageManager.switchScene(FxmlView.SEARCH,true);
        System.out.println("done calling test me..");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        testButton.setOnAction(event -> testMe());
    }
}
