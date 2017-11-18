package org.rssb.phonetree.spring.config;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.rssb.phonetree.common.ContextHolder;
import org.rssb.phonetree.common.Delegator;
import org.rssb.phonetree.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ResourceBundle;

@Component
public class SpringFXMLLoader {
    @Autowired
    private  ResourceBundle resourceBundle;
    @Autowired
    private  ApplicationContext context;

    public Parent loadAndInvokePostProcess(String fxmlPath,ContextHolder contextHolder) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(context::getBean);
        loader.setResources(resourceBundle);
        loader.setLocation(getClass().getResource(fxmlPath));
        Parent parent = loader.load();
        Object controller = loader.getController();
        if(controller!=null && controller instanceof AbstractController) {
            ((AbstractController)loader.getController()).setContextHolder(contextHolder);
            ((AbstractController) loader.getController()).postProcess();
        }
        return parent;
    }
    
    public Parent load(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(context::getBean);
        loader.setResources(resourceBundle);
        loader.setLocation(getClass().getResource(fxmlPath));
        Parent parent = loader.load();
        /*Object conroller = loader.getController();
        if(conroller!=null && conroller instanceof AbstractController) {
            ((AbstractController) loader.getController()).postProcess();
        }*/
        return parent;
    }

    public Parent load(String fxmlPath, Delegator delegator, ContextHolder contextHolder) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(context::getBean);
        loader.setResources(resourceBundle);
        loader.setLocation(getClass().getResource(fxmlPath));
        Parent parent = loader.load();
        ((AbstractController)loader.getController()).setDelegator(delegator,contextHolder);
        ((AbstractController)loader.getController()).postProcess();
        return parent;
    }
}
