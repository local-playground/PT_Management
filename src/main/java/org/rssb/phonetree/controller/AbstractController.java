package org.rssb.phonetree.controller;

import org.rssb.phonetree.common.ContextHolder;
import org.rssb.phonetree.common.Delegator;
import org.rssb.phonetree.common.Refreshable;
import org.rssb.phonetree.services.UtilityService;
import org.rssb.phonetree.ui.view.StageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractController implements Delegator,Refreshable{

    protected Delegator delegator;
    protected ContextHolder contextHolder;

    @Autowired
    @Lazy
    protected StageManager stageManager;

    @Autowired
    @Lazy
    protected UtilityService utilityService;


    public void setDelegator(Delegator delegator, ContextHolder<?,?> contextHolder){
        this.delegator = delegator;
        this.contextHolder = contextHolder;
        //System.out.println("setting delegator "+this.getClass().getSimpleName());
    }

    protected <T,R> ContextHolder<T,R> createContextHolder(T request,R response){
        return new ContextHolder<>(request,response);
    }
}
