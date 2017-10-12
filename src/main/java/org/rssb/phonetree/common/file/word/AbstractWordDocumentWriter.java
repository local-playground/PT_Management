package org.rssb.phonetree.common.file.word;


import org.rssb.phonetree.common.file.DocumentWriter;
import org.rssb.phonetree.common.file.ReportFormat;
import org.rssb.phonetree.common.file.ReportName;
import org.rssb.phonetree.domain.SevadarPhoneTreeList;

public abstract class AbstractWordDocumentWriter implements DocumentWriter{
    protected ReportName reportName;

    @Override
    public ReportFormat supportsReportFormat() {
        return ReportFormat.WORD;
    }
    @Override
    public ReportName supportsReportName() {
        return reportName;
    }

    protected void populateTeamLeadAndSevadarsInformation(SevadarPhoneTreeList sevadarPhoneTreeList){

    }
}
