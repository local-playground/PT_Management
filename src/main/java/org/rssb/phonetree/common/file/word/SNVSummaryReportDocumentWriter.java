package org.rssb.phonetree.common.file.word;


import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.rssb.phonetree.common.Constants;
import org.rssb.phonetree.common.file.ReportName;
import org.rssb.phonetree.domain.PhoneTreeActivationReport;
import org.rssb.phonetree.domain.PhoneTreeActivationSevadarSummary;
import org.rssb.phonetree.domain.PhoneTreeActivationSummary;
import org.rssb.phonetree.domain.PhoneTreeActivationTeamLeadSummary;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

@Component
public class SNVSummaryReportDocumentWriter extends AbstractWordDocumentWriter<PhoneTreeActivationReport> {

    public SNVSummaryReportDocumentWriter() {
        this.reportName = ReportName.SNV_REPORT;
    }

    @Override
    public void writeToFile(PhoneTreeActivationReport phoneTreeActivationReport) {
        //XWPFDocument document = new XWPFDocument();
        XWPFDocument document = null;
        try {
            document = new XWPFDocument(new FileInputStream("C:\\Rajesh\\Sample\\Template.docx"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setPageSize(document);
        addHeaderAndFooter(document);
        addLineBreak(document,2);
        createSummaryTable(phoneTreeActivationReport, document);
        addLineBreak(document,2);
        createTeamLeadSummaryTable(phoneTreeActivationReport,document);
        addLineBreak(document,2);
        createSevadarsSummaryTable(phoneTreeActivationReport,document);
        writeToFile("Activation Report","Activation_Summary",document,false);
    }


    private void createSummaryTable(PhoneTreeActivationReport report, XWPFDocument document) {
        PhoneTreeActivationSummary phoneTreeActivationSummary = report.getPhoneTreeActivationSummary();
        XWPFTable table = document.createTable(1,2);
        //XWPFTable table = document.getTableArray(0);
        table.getCTTbl().getTblPr().addNewTblStyle().setVal("GridTable4-Accent6");
        alignTableToTheCenterOfPage(table);
        fillSummaryTable(0,"Activation Date",phoneTreeActivationSummary.getActivationDate(),table);
        fillSummaryTable(1,"Activation Time",phoneTreeActivationSummary.getActivationTime(),table);
        fillSummaryTable(2,"Total Sangat", String.valueOf(phoneTreeActivationSummary.getTotalSangat()),table);
        fillSummaryTable(3,"Total Families", String.valueOf(phoneTreeActivationSummary.getTotalFamiliesCalled()),table);
        fillSummaryTable(4,"Total VM Left", String.valueOf(phoneTreeActivationSummary.getTotalVMLeft()),table);
        fillSummaryTable(5,"Total Non Reached Families", String.valueOf(phoneTreeActivationSummary.getToalNotReachableFamilies()),table);
        int minTimeTaken = phoneTreeActivationSummary.getMinimumTimeTaken();
        int maxTimeTaken = phoneTreeActivationSummary.getMaximumTimeTaken();
        double averageTimeTaken = phoneTreeActivationSummary.getAverageTimeTaken();
        LocalTime minTime = LocalTime.MIN.plusMinutes(minTimeTaken);
        LocalTime maxTime = LocalTime.MIN.plusMinutes(maxTimeTaken);
        LocalTime avgTime = LocalTime.MIN.plusMinutes((long) averageTimeTaken);

        fillSummaryTable(6,"Minimum Time Taken", minTime.getHour()+" hours "+minTime.getMinute()+" minutes",table);
        fillSummaryTable(7,"Maximum Time Taken", maxTime.getHour()+" hours "+maxTime.getMinute()+" minutes",table);
        fillSummaryTable(8,"Average Time Taken", avgTime.getHour()+" hours "+avgTime.getMinute()+" minutes",table);

        XWPFTableRow tableRow = table.getRow(0);
        XWPFTableCell cellRow1 = tableRow.getCell(0);

        cellRow1.getCTTc().addNewTcPr();
        cellRow1.getCTTc().getTcPr().addNewGridSpan();
        cellRow1.getCTTc().getTcPr().getGridSpan().setVal(BigInteger.valueOf(2));
        writeTextToCell(cellRow1,Constants.WORD_DOCUMENT_HEADING1_STYLE,"Summary Report");
    }

    private void fillSummaryTable(int rowCounter,String leftCellValue,String rightCellValue,XWPFTable table){
        //XWPFTableRow tableRow = table.getRow(rowCounter);
        XWPFTableRow tableRow = table.createRow();
        XWPFTableCell cell = tableRow.getCell(0);
        writeTextToCell(cell, Constants.WORD_DOCUMENT_HEADING1_STYLE, leftCellValue);
        cell = tableRow.getCell(1);
        writeTextToCell(cell, Constants.WORD_DOCUMENT_HEADING1_STYLE,rightCellValue );
    }

    private void createTeamLeadSummaryTable(PhoneTreeActivationReport report,XWPFDocument document){
        List<PhoneTreeActivationTeamLeadSummary> teamLeadSummaries = report.getPhoneTreeActivationTeamLeadSummaryList();
        XWPFTable table = null;
        if(teamLeadSummaries.size()!=0){
            table = document.createTable(1, 4);
            table.getCTTbl().getTblPr().addNewTblStyle().setVal("GridTable4-Accent6");
            alignTableToTheCenterOfPage(table);
        }
        for(PhoneTreeActivationTeamLeadSummary teamLeadSummary:teamLeadSummaries){
            fillTeamLeadSummaryTable(teamLeadSummary,table);
        }

        XWPFTableRow tableRow = table.getRow(0);
        writeTextToCell(tableRow.getCell(0),Constants.WORD_DOCUMENT_HEADING1_STYLE,"Team Lead");
        writeTextToCell(tableRow.getCell(1),Constants.WORD_DOCUMENT_HEADING1_STYLE,"Total Families");
        writeTextToCell(tableRow.getCell(2),Constants.WORD_DOCUMENT_HEADING1_STYLE,"Total Voice Messages");
        writeTextToCell(tableRow.getCell(3),Constants.WORD_DOCUMENT_HEADING1_STYLE,"Total Non Reached");
    }

    private void fillTeamLeadSummaryTable(PhoneTreeActivationTeamLeadSummary summary,XWPFTable table) {
        XWPFTableRow tableRow = table.createRow();
        XWPFTableCell cell = tableRow.getCell(0);
        writeTextToCell(cell, Constants.WORD_DOCUMENT_HEADING1_STYLE, summary.getFirstName()+" "+summary.getLastName());
        cell = tableRow.getCell(1);
        writeTextToCell(cell, Constants.WORD_DOCUMENT_HEADING1_STYLE, String.valueOf(summary.getTotalFamilies()));
        cell = tableRow.getCell(2);
        writeTextToCell(cell, Constants.WORD_DOCUMENT_HEADING1_STYLE, String.valueOf(summary.getTotalVMLeft()));
        cell = tableRow.getCell(3);
        writeTextToCell(cell,Constants.WORD_DOCUMENT_HEADING1_STYLE, String.valueOf(summary.getTotalNotReachedFamilies()));
    }

    private void createSevadarsSummaryTable(PhoneTreeActivationReport phoneTreeActivationReport, XWPFDocument document) {
        List<PhoneTreeActivationTeamLeadSummary> teamLeadSummaries = phoneTreeActivationReport.getPhoneTreeActivationTeamLeadSummaryList();

        for(PhoneTreeActivationTeamLeadSummary teamLeadSummary:teamLeadSummaries){
            fillSevadarSummaryTable(teamLeadSummary,document);
        }


    }

    private void fillSevadarSummaryTable(PhoneTreeActivationTeamLeadSummary teamLeadSummary, XWPFDocument document) {
        List<PhoneTreeActivationSevadarSummary> sevadarSummaryList = teamLeadSummary.getPhoneTreeActivationSevadarSummaryList();
        if(sevadarSummaryList.size()==0){
            return;
        }

        XWPFParagraph p = document.createParagraph();
        p.setStyle(Constants.WORD_DOCUMENT_HEADING1_STYLE);
        p.createRun().setText(teamLeadSummary.getFirstName()+" "+teamLeadSummary.getLastName());
        addLineBreak(document,1);
        XWPFTable table = document.createTable(1, 6);
        table.getCTTbl().getTblPr().addNewTblStyle().setVal("GridTable4-Accent6");
        alignTableToTheCenterOfPage(table);

        for(PhoneTreeActivationSevadarSummary summary:sevadarSummaryList){
            XWPFTableRow tableRow = table.createRow();
            XWPFTableCell cell = tableRow.getCell(0);
            writeTextToCell(cell, Constants.WORD_DOCUMENT_HEADING1_STYLE, summary.getFirstName()+" "+summary.getLastName());
            cell = tableRow.getCell(1);
            writeTextToCell(cell,  Constants.WORD_DOCUMENT_HEADING1_STYLE, String.valueOf(summary.getTotalFamilies()));
            cell = tableRow.getCell(2);
            writeTextToCell(cell,  Constants.WORD_DOCUMENT_HEADING1_STYLE, String.valueOf(summary.getTotalVMLeft()));
            cell = tableRow.getCell(3);
            writeTextToCell(cell,  Constants.WORD_DOCUMENT_HEADING1_STYLE, String.valueOf(summary.getTotalNotReachedFamilies()));
            cell = tableRow.getCell(4);
            writeTextToCell(cell,  Constants.WORD_DOCUMENT_HEADING1_STYLE, String.valueOf(summary.getActivationFinishedTime()));
            cell = tableRow.getCell(5);
            LocalTime timeTaken = LocalTime.MIN.plus(Duration.ofMinutes(summary.getTotalTimeTaken()));
            writeTextToCell(cell,  Constants.WORD_DOCUMENT_HEADING1_STYLE, timeTaken.getHour()+ " hours "+ timeTaken.getMinute()+" minutes");
        }

        XWPFTableRow tableRow = table.getRow(0);
        writeTextToCell(tableRow.getCell(0),Constants.WORD_DOCUMENT_HEADING1_STYLE,"Sevadar Name");
        writeTextToCell(tableRow.getCell(1),Constants.WORD_DOCUMENT_HEADING1_STYLE,"Total Families");
        writeTextToCell(tableRow.getCell(2),Constants.WORD_DOCUMENT_HEADING1_STYLE,"Total Voice Messages");
        writeTextToCell(tableRow.getCell(3),Constants.WORD_DOCUMENT_HEADING1_STYLE,"Total Non Reached");
        writeTextToCell(tableRow.getCell(4),Constants.WORD_DOCUMENT_HEADING1_STYLE,"Finished Time");
        writeTextToCell(tableRow.getCell(5),Constants.WORD_DOCUMENT_HEADING1_STYLE,"Time Taken");
    }
}
