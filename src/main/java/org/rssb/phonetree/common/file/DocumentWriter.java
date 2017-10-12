package org.rssb.phonetree.common.file;

import org.rssb.phonetree.domain.SevadarPhoneTreeList;

public interface DocumentWriter {
    public void writeToFile(SevadarPhoneTreeList sevadarPhoneTreeList);
    public ReportFormat supportsReportFormat();
    public ReportName supportsReportName();
}
