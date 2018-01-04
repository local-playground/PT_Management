package org.rssb.phonetree.common.file;

public enum ReportName {
    PHONE_TREE_LIST,
    SNV_REPORT,
    TEAM_CHART,
    VACATION_SUMMARY;

    /*private String type;

    ReportName(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }*/

    public ReportName fromValue(String value){
        return valueOf(value);
    }
}
