package org.rssb.phonetree.status;


public enum BackupSevadarActionResponse implements ActionResponseType{
    BACKUP_SEVADAR_SUCCESSFULLY_ADDED,
    BACKUP_SEVADAR_SUCCESSFULLY_DELETED,
    BACKUP_SEVADAR_NOT_FOUND,
    BACKUP_SEVADAR_ALREADY_EXISTS,
    BACKUP_SEVADAR_SUCCESSFULLY_ASSIGNED_TO_TEAM_LEAD;

    /*@Override
    public String getMessage() {
        return ResourceBundle.getBundle("messages").getString(this.toString().toLowerCase());
    }*/
}
