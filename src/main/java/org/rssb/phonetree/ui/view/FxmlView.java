package org.rssb.phonetree.ui.view;

import java.util.ResourceBundle;

public enum FxmlView {

    SEARCH {
        @Override
        String getTitle() {
            return getStringFromResourceBundle("main.app.title");
        }
        @Override
        String getFxmlFile() {
            return "/fxml/search/search.fxml";
        }
    }, LOGIN {
        @Override
        String getTitle() {
            return getStringFromResourceBundle("login.title");
        }

        @Override
        String getFxmlFile() {
            return "/fxml/login/login.fxml";
        }
    };
    
    abstract String getTitle();
    abstract String getFxmlFile();
    
    String getStringFromResourceBundle(String key){
        return ResourceBundle.getBundle("messages").getString(key);
    }

}
