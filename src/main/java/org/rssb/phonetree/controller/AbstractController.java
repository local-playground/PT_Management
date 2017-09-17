package org.rssb.phonetree.controller;

import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.rssb.phonetree.common.*;
import org.rssb.phonetree.services.UtilityService;
import org.rssb.phonetree.ui.view.StageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public abstract class AbstractController implements Delegator,Refreshable,Initializable,PostLoader,Selection {

    protected Delegator delegator;
    protected ContextHolder contextHolder;

    @Autowired
    @Lazy
    protected StageManager stageManager;

    @Autowired
    @Lazy
    protected UtilityService utilityService;

    public void setDelegator(Delegator delegator, ContextHolder contextHolder){
        this.delegator = delegator;
        this.contextHolder = contextHolder;
    }

    public void setContextHolder(ContextHolder contextHolder){
        this.contextHolder = contextHolder;
    }

    @Override
    public void delegate(ContextHolder contextHolder) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void refresh(){

    }

    @Override
    public <T> T getSelected(){
        return null;
    }

    @Override
    public void postProcess(){
        System.out.println("abstract post process called "+this.getClass().getSimpleName());
    }

    protected ContextHolder createContextHolder(String key,Object value,Parent parentPane){
        ContextHolder contextHolder = new ContextHolder();
        contextHolder.set(key,value);
        if(parentPane!=null){
            contextHolder.set(Constants.PARENT_PANE,parentPane);
        }
        return contextHolder;
    }

    protected  ContextHolder createContextHolder(String[] keys,Object[] values,Parent parentPane){
        ContextHolder contextHolder = new ContextHolder();
        for(int index=0;index<keys.length;index++){
            String key = keys[index];
            Object value = values[index];
            contextHolder.set(key,value);
        }
        if(parentPane!=null){
            contextHolder.set(Constants.PARENT_PANE,parentPane);
        }
        return contextHolder;
    }

    protected void closeScreen(Event event,ContextHolder contextHolder){
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        setOpacity(Constants.FULL_OPACITY,contextHolder);
    }

    protected void setOpacity(double value,ContextHolder contextHolder){
        if(contextHolder!=null) {
            Parent parentPane = (Parent) contextHolder.get(Constants.PARENT_PANE);
            if (parentPane != null) {
                parentPane.setOpacity(value);
            }
        }
    }

}
