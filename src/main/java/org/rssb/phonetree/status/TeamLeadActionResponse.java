package org.rssb.phonetree.status;

public enum TeamLeadActionResponse implements ActionResponseType {
    TEAM_LEAD_ALREADY_EXISTS,
    TEAM_LEAD_DOES_NOT_EXISTS,
    TEAM_LEAD_HAS_SEVADARS_ASSIGNED,
    TEAM_LEAD_SUCCESSFULLY_DELETED,
    FAMILY_MEMBER_EXISTS_AS_TEAM_LEAD,
    FAMILY_MEMBER_ALREADY_ASSIGNED_TEAM_LEAD_SEVA,
    TEAM_LEAD_SUCCESSFULLY_ADDED,
    TEAM_LEAD_SUCCESSFULLY_REPLACED,
    TEAM_LEAD_TO_BE_SWAPPED_HAS_TO_BE_DIFFERENT,
    TEAM_LEAD_HAS_BEEN_SWAPPED;

    /*@Override
    public String getMessage() {
        return ResourceBundle.getBundle("messages").getString(this.toString().toLowerCase());
    }*/
}
