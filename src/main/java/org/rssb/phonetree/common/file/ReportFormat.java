package org.rssb.phonetree.common.file;

public enum ReportFormat {
    PDF("PDF"),
    WORD("Word"),
    EXCEL("Excel");

    private String format;

    private ReportFormat(String format) {
        this.format = format;
    }

    public String getFormat(){
        return this.format;
    }

    public ReportFormat fromValue(String format){
        return valueOf(format);
    }

}
