package org.rssb.phonetree.controller;

import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.rssb.phonetree.common.ContextHolder;
import org.rssb.phonetree.common.Delegator;
import org.rssb.phonetree.common.Refreshable;
import org.rssb.phonetree.services.UtilityService;
import org.rssb.phonetree.ui.view.StageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public abstract class AbstractController implements Delegator,Refreshable,Initializable{

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

    protected <T,R> ContextHolder<T,R> createContextHolder(T request, R response){
        return new ContextHolder<>(request,response);
    }

    protected void closeScreen(Event event){
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

}
