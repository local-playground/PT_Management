package org.rssb.phonetree.custom.controls;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CustomControlTester extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent parent = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/custom/controls/vbox.fxml"));
            parent = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
/*
        CustomLabelAndTextField customLabelAndTextField = new CustomLabelAndTextField();
        customLabelAndTextField.setLabelText("Zip Code");
        customLabelAndTextField.setText("Hello");*/


        stage.setScene(new Scene(parent));
        stage.setTitle("Custom Control");
        stage.setWidth(300);
        stage.setHeight(200);
        stage.show();
    }
}
