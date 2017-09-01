package org.rssb.phonetree.common;

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

    public static String getFullName(Member member){
        return member.getFirstName() + " " + member.getLastName();
    }
}
