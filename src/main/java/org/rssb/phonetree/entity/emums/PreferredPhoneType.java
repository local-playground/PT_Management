package org.rssb.phonetree.entity.emums;

public enum PreferredPhoneType {
    CELL("Cell"),
    HOME("Home"),
    WORK("Work");

    private String databaseName;

    PreferredPhoneType(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getDatabaseName() {
        return this.databaseName;
    }

    public static PreferredPhoneType fromDatabaseName(String databaseName) {
        switch (databaseName) {
            case "Cell":
                return PreferredPhoneType.CELL;
            case "Work":
                return PreferredPhoneType.WORK;
            case "Home":
                return PreferredPhoneType.HOME;
            default:
                throw new IllegalArgumentException("Unknown : " + databaseName);

        }
    }
}
