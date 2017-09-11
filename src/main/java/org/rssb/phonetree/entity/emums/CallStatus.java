package org.rssb.phonetree.entity.emums;

public enum CallStatus {
    OK("Ok"),
    NOT_PICKED("Not Picked"),
    MOVED("Moved"),
    REMOVE_REQUEST("Remove Request"),
    DISCONNECTED("Disconnected"),
    WRONG_NUMBER("Wrong Number");

    private String databaseName;

    CallStatus(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getDatabaseName() {
        return this.databaseName;
    }

    public static CallStatus fromDatabaseName(String databaseName) {
        switch (databaseName) {
            case "Ok":
                return OK;
            case "Not Picked":
                return NOT_PICKED;
            case "Moved":
                return MOVED;
            case "Remove Request":
                return REMOVE_REQUEST;
            case "Disconnected":
                return DISCONNECTED;
            case "Wrong Number":
                return WRONG_NUMBER;
            default:
                throw new IllegalArgumentException("Unknown: " + databaseName);
        }
    }

}
