package org.rssb.phonetree.status;


public enum SevadarActionResponse implements ActionResponseType{
    SEVADAR_ALREADY_EXISTS,
    FAMILY_MEMBER_EXISTS_AS_SEVADAR,
    SEVADAR_SUCCESSFULLY_ADDED,
    FAMILY_MEMBER_ALREADY_ASSIGNED_SEVADAR_SEVA,
    SEVADAR_HAS_SEVADARS_ASSIGNED,
    SEVADAR_SUCCESSFULLY_DELETED,
    SEVADAR_SUCCESSFULLY_REPLACED,
    SEVADAR_HAS_BEEN_SWAPPED, SEVADAR_SUCCESSFULLY_MOVED_UNDER_OTHER_TEAM_LEAD;

    /*@Override
    public String getMessage() {
        return ResourceBundle.getBundle("messages").getString(this.toString().toLowerCase());
    }*/
}
