package org.rssb.phonetree.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.rssb.phonetree.ui.view.FxmlView;
import org.rssb.phonetree.ui.view.StageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class TestController extends AbstractController implements Initializable{
    @FXML
    private JFXButton testButton;

    @Autowired @Lazy
    private StageManager stageManager;

    public TestController(){
        System.out.println("Creating Test Controller..");
    }

    void testMe() {
        System.out.println("Test Me is called..");
        stageManager.switchScene(FxmlView.PHONE_TREE_MANAGEMENT_MAIN,null,null,true);
        System.out.println("done calling test me..");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        testButton.setOnAction(event -> {
            testMe();
            System.out.println("closing window now....");
            closeScreen(event,null);
            System.out.println("done closing window now....");
        });
    }

    @Override
    public void refresh() {

    }
}
