package org.rssb.phonetree.status;


import java.util.ResourceBundle;

public interface ActionResponseType {
    default String getMessage(){
        return ResourceBundle.getBundle("messages").getString(this.toString().toLowerCase());
    }
}
