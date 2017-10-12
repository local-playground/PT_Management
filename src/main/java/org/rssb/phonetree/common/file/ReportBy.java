package org.rssb.phonetree.common.file;

public enum ReportBy {
    TEAM_LEAD("Team Lead"),
    SEVADAR("Sevadar"),
    ALL_SEVADAR("All Sevadars");

    private String type;

    private ReportBy(String type) {
        this.type = type;
    }

    public String getType(){
        return type;
    }

    public ReportBy fromValue(String value){
        return valueOf(value);
    }
}
