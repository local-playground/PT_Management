package org.rssb.phonetree.common;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import org.rssb.phonetree.entity.Member;
import org.rssb.phonetree.status.ActionAlertType;
import org.rssb.phonetree.status.ActionResponseType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CommonUtil {
    private CommonUtil() {

    }

    public static boolean isTrue(String value) {
        if (isEmptyOrNull(value)) {
            return false;
        }

        return "Y".equalsIgnoreCase(value) || "YES".equalsIgnoreCase(value);
    }

    public static boolean isEmptyOrNull(String str) {
        if (str == null || str.trim().length() == 0)
            return true;

        return false;
    }

    public static boolean isNotEmptyOrNull(String str) {
        if (str == null || str.trim().length() == 0)
            return false;

        return true;
    }

    public static String ifEmptyOrNullReturnDefault(String str,String defaultValue) {
        if (str == null || str.trim().length() == 0)
            return defaultValue;

        return str;
    }

    public static boolean isCollectionEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }


    public static String strigyfy(String[] str, String delimiter) {
        if (str == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (String s : str) {
            sb.append(s).append(delimiter);
        }

        return sb.toString();
    }

    public static List<String> convertStringToList(String str, String delimiter) {
        if (CommonUtil.isEmptyOrNull(str)) {
            return new ArrayList<>();
        }

        return Arrays.asList(str.split(delimiter));
    }

    public static Response createResponse(ActionResponseType actionResponseType, Object[] messageParams, ActionAlertType actionAlertType){
        Response response = new Response();
        response.setActionResponseType(actionResponseType);
        response.setMessageParams(messageParams);
        response.setActionAlertType(actionAlertType);

        return response;
    }

    /*public static BackupSevadar createBackupSevadar(int memberId,int familyId,String sevadarName){
        BackupSevadar backupSevadar = new BackupSevadar();
        backupSevadar.setMemberId(memberId);
        backupSevadar.setFamilyId(familyId);
        backupSevadar.setSevadarName(sevadarName);
        return backupSevadar;
    }*/

    public static Alert getAlert(Response response){
        Alert alert=null;
        if(response.getActionAlertType()==ActionAlertType.ERROR){
            alert = new Alert(Alert.AlertType.ERROR,response.getMessage(), ButtonType.OK);
            alert.setTitle("Error!!");
        }else if(response.getActionAlertType()==ActionAlertType.WARNING){
            alert = new Alert(Alert.AlertType.WARNING,response.getMessage(), ButtonType.OK);
            alert.setTitle("Warning!!");
        }else if(response.getActionAlertType()==ActionAlertType.INFORMATION){
            alert = new Alert(Alert.AlertType.INFORMATION,response.getMessage(), ButtonType.OK);
            alert.setTitle("Information!!");
        }else{
            alert = new Alert(Alert.AlertType.CONFIRMATION,response.getMessage(), ButtonType.YES,ButtonType.NO);
            alert.setTitle("Confirmation!!");
        }
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(CommonUtil.class.getResource("/styles/alert-box-style.css").toExternalForm());
        dialogPane.getStyleClass().add("alert-box");

        return alert;
    }

    public static Alert getAlert(String message,ActionAlertType actionAlertType){
        Alert alert=null;
        if(actionAlertType==ActionAlertType.ERROR){
            alert = new Alert(Alert.AlertType.ERROR,message, ButtonType.OK);
            alert.setTitle("Error!!");
        }else if(actionAlertType==ActionAlertType.WARNING){
            alert = new Alert(Alert.AlertType.WARNING,message, ButtonType.OK);
            alert.setTitle("Warning!!");
        }else if(actionAlertType==ActionAlertType.INFORMATION){
            alert = new Alert(Alert.AlertType.INFORMATION,message, ButtonType.OK);
            alert.setTitle("Information!!");
        }else{
            alert = new Alert(Alert.AlertType.CONFIRMATION,message, ButtonType.YES,ButtonType.NO);
            alert.setTitle("Confirmation!!");
        }
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(CommonUtil.class.getResource("/styles/alert-box-style.css").toExternalForm());
        dialogPane.getStyleClass().add("alert-box");

        return alert;
    }

    public static String getFullName(Member member){
        return member.getFirstName() + " " + member.getLastName();
    }
}
