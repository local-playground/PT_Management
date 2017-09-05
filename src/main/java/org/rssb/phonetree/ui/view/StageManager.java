package org.rssb.phonetree.ui.view;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.rssb.phonetree.repository.FamilyJpaRepository;
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

    public StageManager(SpringFXMLLoader springFXMLLoader, Stage stage) {
        this.springFXMLLoader = springFXMLLoader;
        this.primaryStage = stage;
        this.primaryStage.setOnHidden(event -> System.out.println("primary stage on hidden"));
        this.primaryStage.setOnCloseRequest(event -> System.out.println("primary stage on close"));
    }

    public void switchScene(final FxmlView view) {
        Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.getFxmlFile());
        show(viewRootNodeHierarchy, view.getTitle(),primaryStage,false);
    }

    public void switchScene(final FxmlView view,boolean popUpNewScreen){
        Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.getFxmlFile());
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setOnCloseRequest(event -> System.out.println("Closing child stage.."));
        stage.setOnHidden(event -> System.out.println("hiding child stage.."));
        show(viewRootNodeHierarchy,view.getTitle(),stage,true);
    }
    
   /* private void show(final Parent rootNode, String title) {
        show(rootNode,title,primaryStage);
    }
*/
    private void show(final Parent rootNode, String title,Stage stage,boolean showAndWait) {
        Scene scene = prepareScene(rootNode,stage);
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.centerOnScreen();

        try {
            if(showAndWait) {
                System.out.println("showing and waiting...");
                stage.showAndWait();
                System.out.println("out of wait stage...");
            }else{
                stage.show();
            }
        } catch (Exception exception) {
            logAndExit ("Unable to show scene for title" + title,  exception);
        }
    }
    
    public Scene prepareScene(Parent rootNode,Stage stage){
        Scene scene = stage.getScene();

        if (scene == null) {
            scene = new Scene(rootNode);
        }
        scene.setRoot(rootNode);
        return scene;
    }

    public Parent loadViewNodeHierarchy(String fxmlFilePath) {
        Parent rootNode = null;
        try {
            rootNode = springFXMLLoader.load(fxmlFilePath);
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
