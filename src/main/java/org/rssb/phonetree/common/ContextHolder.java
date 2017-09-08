package org.rssb.phonetree.common;

public class ContextHolder<T,R>{
    private T request;
    private R response;

    public ContextHolder(T request,R response){
        this.request = request;
        this.response = response;
    }
    public T getRequest(){
        return request;
    }
    public R getResponse(){
        return response;
    }

    public void setRequest(T request){
        this.request = request;
    }

    public void setResponse(R response){
        this.response = response;
    }
}
