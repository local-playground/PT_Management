package org.rssb.phonetree.spring.config;

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SpringFXMLLoader {
    @Autowired
    private  ResourceBundle resourceBundle;
    @Autowired
    private  ApplicationContext context;
    
//    @Autowired
//    public SpringFXMLLoader(ApplicationContext context, ResourceBundle resourceBundle) {
//        this.resourceBundle = resourceBundle;
//        this.context = context;
//    }

    public Parent load(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(context::getBean);
        loader.setResources(resourceBundle);
        loader.setLocation(getClass().getResource(fxmlPath));
        return loader.load();
    }
}
