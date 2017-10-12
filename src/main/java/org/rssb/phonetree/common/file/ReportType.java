package org.rssb.phonetree.common.file;

import org.rssb.phonetree.entity.Sevadar;
import org.rssb.phonetree.entity.TeamLead;

public class ReportType {
    private ReportName reportName;
    private ReportFormat reportFormat;
    private ReportBy reportBy;
    private TeamLead teamLead;
    private Sevadar sevadar;

    public ReportName getReportName() {
        return reportName;
    }

    public void setReportName(ReportName reportName) {
        this.reportName = reportName;
    }

    public ReportFormat getReportFormat() {
        return reportFormat;
    }

    public void setReportFormat(ReportFormat reportFormat) {
        this.reportFormat = reportFormat;
    }

    public ReportBy getReportBy() {
        return reportBy;
    }

    public void setReportBy(ReportBy reportBy) {
        this.reportBy = reportBy;
    }

    public TeamLead getTeamLead() {
        return teamLead;
    }

    public void setTeamLead(TeamLead teamLead) {
        this.teamLead = teamLead;
    }

    public Sevadar getSevadar() {
        return sevadar;
    }

    public void setSevadar(Sevadar sevadar) {
        this.sevadar = sevadar;
    }
}
