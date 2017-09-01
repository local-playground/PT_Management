
package org.rssb.phonetree.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Reports")
public class Report implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ReportId")
    private int reportId;
    @Column(name = "ReportName")
    private String reportName;
    @Column(name = "ReportCaption")
    private String reportCaption;
    @Column(name = "NumberOfColumnsInReport")
    private Integer numberOfColumnsInReport;
    @Column(name = "ColumnsWidth")
    private String columnsWidth;
    @Column(name = "TemplateName")
    private String templateName;
    @Column(name = "Source")
    private String source;

    public Report() {
    }

    

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportCaption() {
        return reportCaption;
    }

    public void setReportCaption(String reportCaption) {
        this.reportCaption = reportCaption;
    }

    public Integer getNumberOfColumnsInReport() {
        return numberOfColumnsInReport;
    }

    public void setNumberOfColumnsInReport(Integer numberOfColumnsInReport) {
        this.numberOfColumnsInReport = numberOfColumnsInReport;
    }

    public String getColumnsWidth() {
        return columnsWidth;
    }

    public void setColumnsWidth(String columnsWidth) {
        this.columnsWidth = columnsWidth;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Report{");
        sb.append("reportId=").append(reportId);
        sb.append(", reportName='").append(reportName).append('\'');
        sb.append(", reportCaption='").append(reportCaption).append('\'');
        sb.append(", numberOfColumnsInReport=").append(numberOfColumnsInReport);
        sb.append(", columnsWidth='").append(columnsWidth).append('\'');
        sb.append(", templateName='").append(templateName).append('\'');
        sb.append(", source='").append(source).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
