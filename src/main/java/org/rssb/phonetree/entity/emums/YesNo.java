package org.rssb.phonetree.entity.emums;

public enum YesNo {
    YES("Yes"),
    NO("No");

    private String databaseName;

    YesNo(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getDatabaseName() {
        return this.databaseName;
    }

    public static YesNo fromDatabaseName(String databaseName) {
        switch (databaseName) {
            case "Yes":
                return YesNo.YES;
            case "No":
                return YesNo.NO;
            default:
                throw new IllegalArgumentException("Unknow: " + databaseName);
        }
    }
}
