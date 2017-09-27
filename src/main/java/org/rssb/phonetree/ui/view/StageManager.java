package org.rssb.phonetree.ui.view;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.rssb.phonetree.common.ContextHolder;
import org.rssb.phonetree.common.Delegator;
import org.rssb.phonetree.spring.config.SpringFXMLLoader;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class StageManager {

    private static final Logger LOG = getLogger(StageManager.class);
    private final Stage primaryStage;
    private final SpringFXMLLoader springFXMLLoader;
    private double xOffset;
    private double yOffset;

    public StageManager(SpringFXMLLoader springFXMLLoader, Stage stage) {
        this.springFXMLLoader = springFXMLLoader;
        this.primaryStage = stage;
        this.primaryStage.setOnHidden(event -> System.out.println("primary stage on hidden"));
        this.primaryStage.setOnCloseRequest(event -> System.out.println("primary stage on close"));
    }

    public void switchScene(final FxmlView view, Delegator delegator,
                            ContextHolder contextHolder, boolean popUpNewScreen) {

        Parent rootNode = loadFxmlFileFromClasspath(view.getFxmlFile(),delegator,contextHolder);
        Stage stage = primaryStage;
        if (popUpNewScreen) {
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOnCloseRequest(event -> System.out.println("Closing child stage.."));
            stage.setOnHidden(event -> System.out.println("hiding child stage.."));
        }

        rootNode.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        final Stage stageToUse = stage;
        rootNode.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stageToUse.setX(event.getScreenX() - xOffset);
                stageToUse.setY(event.getScreenY() - yOffset);
            }
        });
        show(rootNode, view, stageToUse);
    }

    private void show(final Parent rootNode, FxmlView view, Stage stage) {
        Scene scene = prepareScene(rootNode, stage);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.setFullScreen(view.isFullScreen());
        stage.setAlwaysOnTop(view.isAlwaysOnTop());
        //stage.sizeToScene();
        stage.centerOnScreen();

        try {
            stage.show();
        } catch (Exception exception) {
            logAndExit("Unable to show scene ", exception);
        }
    }

    private Scene prepareScene(Parent rootNode, Stage stage) {
        Scene scene = stage.getScene();
        /*NotificationPane notificationPane = new NotificationPane(rootNode);
        if (scene == null) {
            scene = new Scene(notificationPane);
        }*/
        if (scene == null) {
            scene = new Scene(rootNode);
        }
        //scene.setRoot(notificationPane);
        scene.setRoot(rootNode);
        /*notificationPane.setShowFromTop(true);
        notificationPane.getStyleClass().add(NotificationPane.STYLE_CLASS_DARK);
        notificationPane.setCloseButtonVisible(true);*/
        return scene;
    }

    private Parent loadFxmlFileFromClasspath(String fxmlFilePath,Delegator delegator,ContextHolder contextHolder) {
        Parent rootNode = null;
        try {
            rootNode = springFXMLLoader.load(fxmlFilePath,delegator,contextHolder);
            Objects.requireNonNull(rootNode, "A Root FXML node must not be null");
        } catch (Exception exception) {
            exception.printStackTrace();
            logAndExit("Unable to load FXML view" + fxmlFilePath, exception);
        }
        return rootNode;
    }


    private void logAndExit(String errorMsg, Exception exception) {
        LOG.error(errorMsg, exception, exception.getCause());
        Platform.exit();
    }

}
