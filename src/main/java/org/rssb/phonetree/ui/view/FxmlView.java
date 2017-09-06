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

        @Override
        boolean isFullScreen() {
            return false;
        }

        @Override
        boolean isAlwaysOnTop() {
            return true;
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

        @Override
        boolean isFullScreen() {
            return false;
        }
        @Override
        boolean isAlwaysOnTop() {
            return true;
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

        @Override
        boolean isFullScreen() {
            return true;
        }
        @Override
        boolean isAlwaysOnTop() {
            return true;
        }
    };
    
    abstract String getTitle();
    abstract String getFxmlFile();
    abstract boolean isFullScreen();
    abstract boolean isAlwaysOnTop();
    
    String getStringFromResourceBundle(String key){
        return ResourceBundle.getBundle("messages").getString(key);
    }

}
