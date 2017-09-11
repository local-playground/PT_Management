package org.rssb.phonetree.entity.emums;

public enum BusRide {
    YES("Yes"),
    NO("No"),
    MAYBE("May Be");

    private String shortName;

    BusRide(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return this.shortName;
    }

    public static BusRide fromShortName(String shortName) {
        switch (shortName) {
            case "Yes":
                return BusRide.YES;
            case "No":
                return BusRide.NO;
            case "May Be":
                return BusRide.MAYBE;
            default:
                throw new IllegalArgumentException("Unknow: " + shortName);
        }
    }
}
