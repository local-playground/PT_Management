package org.rssb.phonetree.common;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.rssb.phonetree.entity.Member;
import org.rssb.phonetree.status.ActionAlertType;
import org.rssb.phonetree.status.ActionResponseType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CommonUtil {
    private static final List<ActionAlertType> NO_ACTION_NEEDED_ALERT_TYPES=
            Arrays.asList(ActionAlertType.INFORMATION,ActionAlertType.ERROR,ActionAlertType.WARNING);
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

    public static boolean isIndexOfKeyPresent(String value,String lookup){
        if(isEmptyOrNull(value)){
            return false;
        }

        return value.indexOf(lookup)==-1?false:true;
    }

    public static int convertStringToInt(String value,int defaultValue){
        if(CommonUtil.isEmptyOrNull(value)){
            return defaultValue;
        }

        try{
            return Integer.parseInt(value);
        }catch (NumberFormatException ex){
            return defaultValue;
        }
    }

    public static String convertIntToString(int value,String defaultValue){
        try{
            return String.valueOf(value);
        }catch (NumberFormatException ex){
            return defaultValue;
        }
    }

    public static List<String> convertStringToList(String str, String delimiter) {
        if (CommonUtil.isEmptyOrNull(str)) {
            return new ArrayList<>();
        }

        return Arrays.asList(str.split(delimiter));
    }

    public static List<Integer> convertStringToIntegerList(String value, String delimiter) {
        List<Integer> intList = new ArrayList<>();

        if (CommonUtil.isEmptyOrNull(value)) {
            return intList;
        }

        String str[] = value.split(delimiter);
        for (String st : str) {
            int val = Integer.parseInt(st);
            intList.add(val);
        }

        return intList;
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

    /*public static Alert getAlert(Response response){
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
    }*/

    /*public static Alert getAlert(String message,ActionAlertType actionAlertType){
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
    }*/

    private static JFXDialogLayout createJFXDialogLayout(Response response){
        JFXDialogLayout jfxDialogLayout = new JFXDialogLayout();
        Text text = new Text(response.getMessage());
        text.setStyle("-fx-font-family: \"Calibri\";-fx-font-size: 20px;-fx-font-weight: bold;-fx-fill: white;");
        jfxDialogLayout.setBody(text);
        return jfxDialogLayout;
    }

    public static void showNoActionNeededJFXDialog(RootPanel rootPanel,
                                                   Object[] messageParams,
                                                   ActionResponseType actionResponseType){
        Response response = createResponse(actionResponseType,messageParams,null);
        showNoActionNeededJFXDialog(rootPanel,response);
    }

    public static void showConfirmationJFXDialog(RootPanel rootPanel,
                                                 Object[] messageParams,
                                                 ActionResponseType actionResponseType,
                                                 ContextHolder contextHolder,
                                                 ResponseHandler responseHandler){
        Response response = createResponse(actionResponseType,messageParams,null);
        showConfirmationJFXDialog(rootPanel,response,contextHolder,responseHandler);
    }

    private static void showNoActionNeededJFXDialog(RootPanel rootPanel,Response response){
        JFXDialogLayout jfxDialogLayout = createJFXDialogLayout(response);
        JFXDialog jfxDialog = new JFXDialog((StackPane) rootPanel.getRootPanel(),jfxDialogLayout, JFXDialog.DialogTransition.TOP);
        JFXButton okayButton = new JFXButton("Ok");
        okayButton.setOnAction(event -> {
            jfxDialog.close();
        });
        jfxDialogLayout.setActions(okayButton);
        jfxDialog.show();
    }

    public static void showConfirmationJFXDialog(RootPanel rootPanel,
                                                 Response response,
                                                 ContextHolder contextHolder,
                                                 ResponseHandler responseHandler){

        JFXDialogLayout jfxDialogLayout = createJFXDialogLayout(response);
        JFXDialog jfxDialog = new JFXDialog((StackPane) rootPanel.getRootPanel(),jfxDialogLayout, JFXDialog.DialogTransition.TOP);
        JFXButton yesButton = new JFXButton("Yes");
        yesButton.setOnAction(event -> {
            jfxDialog.close();
            if(responseHandler!=null) {
                Response resp = responseHandler.handlerResponse(contextHolder);
                handleResponse(rootPanel, resp, contextHolder, responseHandler);
            }
        });
        JFXButton noButton = new JFXButton("No");
        noButton.setOnAction(event -> {
            jfxDialog.close();
        });

        jfxDialogLayout.setActions(yesButton,noButton);
        jfxDialog.show();
    }


    public static void handleResponse(RootPanel rootPanel,Response response,
                                      ContextHolder contextHolder,
                                      ResponseHandler responseHandler) {
        if (NO_ACTION_NEEDED_ALERT_TYPES.contains(response.getActionAlertType())) {
            showNoActionNeededJFXDialog(rootPanel,response);
            return;
        }

        if (response.getActionAlertType() == ActionAlertType.NONE) {
            if(responseHandler!=null) {
                Response resp = responseHandler.handlerResponse(contextHolder);
                handleResponse(rootPanel,resp, null, null);
                return;
            }
        }

        if (response.getActionAlertType() == ActionAlertType.CONFIRMATION) {
            showConfirmationJFXDialog(rootPanel,response,contextHolder,responseHandler);
        }
    }
    public static String getFullName(Member member){
        return member.getFirstName() + " " + member.getLastName();
    }
}
