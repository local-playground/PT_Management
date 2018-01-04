package org.rssb.phonetree.common.file;

import java.util.List;

public interface DocumentWriter<T> {
    void writeToFile(T dataList);

    void addColumnsToDocument(List<ColumnInformation> tableColumnInformationsList);

    ReportFormat supportsReportFormat();

    ReportName supportsReportName();
}
