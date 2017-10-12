package org.rssb.phonetree.common.file;

public enum ReportName {
    NORMAL_REPORT("Normal Phone Tree List"),
    ZIP_CODE_REPORT("Phone Tree With ZipCode"),
    BUS_RIDE_REPORT("Phone Tree With Bus Ride");

    private String type;

    ReportName(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public ReportName fromValue(String value){
        return valueOf(value);
    }
}
