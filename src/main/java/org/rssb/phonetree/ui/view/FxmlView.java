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
    },
    TEAM_LEAD_MANAGEMENT{
        @Override
        String getTitle() {
            return getStringFromResourceBundle("login.title");
        }

        @Override
        String getFxmlFile() {
            return "/fxml/teammanagement/team-management.fxml";
        }
    };
    
    abstract String getTitle();
    abstract String getFxmlFile();
    
    String getStringFromResourceBundle(String key){
        return ResourceBundle.getBundle("messages").getString(key);
    }

}
