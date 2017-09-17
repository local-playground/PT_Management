package org.rssb.phonetree.common;

import java.util.HashMap;
import java.util.Map;

public class ContextHolder{
    private Map<String,Object> requestMap = new HashMap<>();

    public void set(String key,Object value){
        this.requestMap.put(key,value);
    }

    public void set(String[] keys,Object[] values){
        for(int index=0;index<keys.length;index++){
            String key = keys[index];
            Object value = values[index];
            set(key,value);
        }
    }

    public Object get(String key){
        return this.requestMap.get(key);
    }

}
