package org.rssb.phonetree.controller;

import javafx.animation.FadeTransition;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.rssb.phonetree.common.CommonUtil;
import org.rssb.phonetree.common.Constants;
import org.rssb.phonetree.common.ContextHolder;
import org.rssb.phonetree.common.Delegator;
import org.rssb.phonetree.common.PostLoader;
import org.rssb.phonetree.common.Refreshable;
import org.rssb.phonetree.common.RootPanel;
import org.rssb.phonetree.common.Selection;
import org.rssb.phonetree.common.Validator;
import org.rssb.phonetree.services.UtilityService;
import org.rssb.phonetree.ui.view.StageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public abstract class AbstractController implements
        Refreshable,Initializable,PostLoader,Selection,RootPanel,Validator {

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
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void refresh(){

    }

    @Override
    public boolean validate(){
        return true;
    }

    @Override
    public Parent getRootPanel(){
        return null;
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
        if(CommonUtil.isNotEmptyOrNull(key) && value !=null) {
            contextHolder.set(key, value);
        }
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
            if(CommonUtil.isNotEmptyOrNull(key) && value !=null) {
                contextHolder.set(key, value);
            }
        }
        if(parentPane!=null){
            contextHolder.set(Constants.PARENT_PANE,parentPane);
        }
        return contextHolder;
    }

    protected void closeScreen(Event event,ContextHolder contextHolder){
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FadeTransition ft = new FadeTransition(Duration.seconds(1),getRootPanel());
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.play();
        ft.setOnFinished(event1 -> System.out.println("parent calling on fade transition ended"));

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
