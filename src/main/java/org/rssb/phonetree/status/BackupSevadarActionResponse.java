package org.rssb.phonetree.status;


public enum BackupSevadarActionResponse implements ActionResponseType{
    BACKUP_SEVADAR_SUCCESSFULLY_ADDED,
    BACKUP_SEVADAR_SUCCESSFULLY_DELETED,
    BACKUP_SEVADAR_NOT_FOUND,
    BACKUP_SEVADAR_ALREADY_EXISTS;

    /*@Override
    public String getMessage() {
        return ResourceBundle.getBundle("messages").getString(this.toString().toLowerCase());
    }*/
}
