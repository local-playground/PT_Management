package org.rssb.phonetree.common.file;

public enum ReportFormat {
    PDF("PDF"),
    WORD("Word"),
    EXCEL("Excel");

    private String format;

    private ReportFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return this.format;
    }

    public static ReportFormat fromValue(String value) {
        for (ReportFormat reportFormat : values()) {
            if (reportFormat.format.equalsIgnoreCase(value))
                return reportFormat;
        }

        return null;
    }

}
