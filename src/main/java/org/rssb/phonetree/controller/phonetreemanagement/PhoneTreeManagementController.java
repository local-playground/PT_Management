package org.rssb.phonetree.controller.phonetreemanagement;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import org.rssb.phonetree.controller.AbstractController;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class PhoneTreeManagementController extends AbstractController{

    @FXML
    private BorderPane phoneTreeBorderPane;

    @Override
    public Parent getRootPanel() {
        return phoneTreeBorderPane;
    }
}
