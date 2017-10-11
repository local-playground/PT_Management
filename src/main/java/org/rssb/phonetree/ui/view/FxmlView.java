package org.rssb.phonetree.ui.view;

import java.util.ResourceBundle;

public enum FxmlView {

    SEARCH {
        @Override
        public String getFxmlFile() {
            return "/fxml/search/search.fxml";
        }

        @Override
        public boolean isFullScreen() {
            return false;
        }

        @Override
        public boolean isAlwaysOnTop() {
            return true;
        }
    },
    LOGIN {
        @Override
        public String getFxmlFile() {
            return "/fxml/login/login.fxml";
        }

        @Override
        public boolean isFullScreen() {
            return false;
        }
        @Override
        public boolean isAlwaysOnTop() {
            return true;
        }
    },
    TEAM_LEAD_MANAGEMENT{
        @Override
        public String getFxmlFile() {
            return "/fxml/teammanagement/team-management.fxml";
        }

        @Override
        public boolean isFullScreen() {
            return true;
        }
        @Override
        public boolean isAlwaysOnTop() {
            return true;
        }
    },
    SWAP_TEAM_LEAD{
        @Override
        public String getFxmlFile() {
            return "/fxml/teamlead/swap-team-lead.fxml";
        }

        @Override
        public boolean isFullScreen() {
            return false;
        }
        @Override
        public boolean isAlwaysOnTop() {
            return true;
        }
    }, SWAP_SEVADAR{
        @Override
        public String getFxmlFile() {
            return "/fxml/sevadar/swap-sevadar.fxml";
        }

        @Override
        public boolean isFullScreen() {
            return false;
        }
        @Override
        public boolean isAlwaysOnTop() {
            return true;
        }
    },
    MOVE_SEVADAR{
        @Override
        public String getFxmlFile() {
            return "/fxml/sevadar/move-sevadar.fxml";
        }

        @Override
        public boolean isFullScreen() {
            return false;
        }
        @Override
        public boolean isAlwaysOnTop() {
            return true;
        }
    },
    PHONE_TREE_MANAGEMENT_MAIN{
        @Override
        public String getFxmlFile() {
            return "/fxml/phonetree-management/phonetree-management.fxml";
        }
        @Override
        public boolean isFullScreen() {
            return false;
        }
        @Override
        public boolean isAlwaysOnTop() {
            return true;
        }
    },
    FAMILY_MANAGEMENT{
        @Override
        public String getFxmlFile() {
            return "/fxml/family-management/family-management.fxml";
        }
        @Override
        public boolean isFullScreen() {
            return false;
        }
        @Override
        public boolean isAlwaysOnTop() {
            return true;
        }
    },
    ADD_MEMBER_INFORMATION{
        @Override
        public String getFxmlFile() {
            return "/fxml/family-management/add-member.fxml";
        }
        @Override
        public boolean isFullScreen() {
            return false;
        }
        @Override
        public boolean isAlwaysOnTop() {
            return true;
        }
    },
    SEVADARS_LIST{
        @Override
        public String getFxmlFile() {
            return "/fxml/sevadars-list/sevadars-list.fxml";
        }
        @Override
        public boolean isFullScreen() {
            return false;
        }
        @Override
        public boolean isAlwaysOnTop() {
            return true;
        }
    },
    APPLICATION_MANAGEMENT_SCREEN{
        @Override
        public String getFxmlFile() {
            return "/fxml/application/application.fxml";
        }
        @Override
        public boolean isFullScreen() {
            return false;
        }
        @Override
        public boolean isAlwaysOnTop() {
            return true;
        }
    };
    
    public abstract String getFxmlFile();
    public abstract boolean isFullScreen();
    public abstract boolean isAlwaysOnTop();
    
    String getStringFromResourceBundle(String key){
        return ResourceBundle.getBundle("messages").getString(key);
    }

}
