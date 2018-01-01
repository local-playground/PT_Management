package org.rssb.phonetree.common.file;

import java.util.List;

public class ReportType {
    private ReportFormat reportFormat;
    private List<DocumentTableColumn> documentTableColumnList;

    public List<DocumentTableColumn> getDocumentTableColumnList() {
        return documentTableColumnList;
    }

    public void setDocumentTableColumnList(List<DocumentTableColumn> documentTableColumnList) {
        this.documentTableColumnList = documentTableColumnList;
    }

    public ReportFormat getReportFormat() {
        return reportFormat;
    }
    public void setReportFormat(ReportFormat reportFormat) {
        this.reportFormat = reportFormat;
    }
}
