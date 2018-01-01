package org.rssb.phonetree.common.file;

import org.rssb.phonetree.domain.SevadarPhoneTreeList;

import java.util.List;

public interface DocumentWriter {
    public void writeToFile(SevadarPhoneTreeList sevadarPhoneTreeList);
    public void addColumnsToDocument(List<DocumentTableColumn> documentTableColumnList);
    public ReportFormat supportsReportFormat();
    public ReportName supportsReportName();
}
