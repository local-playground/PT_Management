package org.rssb.phonetree.common;


import org.rssb.phonetree.status.ActionAlertType;
import org.rssb.phonetree.status.ActionResponseType;
import org.rssb.phonetree.status.TeamLeadActionResponse;

import java.text.MessageFormat;

public class Response {

    private ActionResponseType actionResponseType;
    private Object[] messageParams;
    private ActionAlertType actionAlertType;

    public ActionAlertType getActionAlertType() {
        return actionAlertType;
    }

    public void setActionAlertType(ActionAlertType actionAlertType) {
        this.actionAlertType = actionAlertType;
    }

    public ActionResponseType getActionResponseType() {
        return actionResponseType;
    }

    public void setActionResponseType(ActionResponseType actionResponseType) {
        this.actionResponseType = actionResponseType;
    }

    public Object[] getMessageParams() {
        return messageParams;
    }

    public void setMessageParams(Object[] messageParams) {
        this.messageParams = messageParams;
    }

    public String getMessage() {
       return MessageFormat.format(actionResponseType.getMessage(),getMessageParams());
    }

    public static void main(String[] args) {
        Response response = new Response();
        response.actionResponseType = TeamLeadActionResponse.TEAM_LEAD_HAS_SEVADARS_ASSIGNED;
        response.setMessageParams(new Object[]{"Naren Shah",16});

        System.out.println(response.getMessage());

    }
}
