package org.rssb.phonetree.common.file.word;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.rssb.phonetree.common.ConfigService;
import org.rssb.phonetree.common.Constants;
import org.rssb.phonetree.common.file.ReportName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class TeamChartDocumentWriter extends AbstractWordDocumentWriter<Map<String, List<String>>> {
    @Autowired
    private ConfigService configService;

    public TeamChartDocumentWriter() {
        this.reportName = ReportName.TEAM_CHART;
    }

    @Override
    public void writeToFile(Map<String, List<String>> dataList) {
        XWPFDocument document = null;
        try {
            document = new XWPFDocument(new FileInputStream("C:\\Rajesh\\Sample\\Template.docx"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setPageSize(document);
        addHeaderAndFooter(document);
        addSecerateryTable(document);
        addLineBreak(document, 1);
        addAdminsTable(document);
        addLineBreak(document, 1);
        addTeamLeadAndSevadarsInformation(document, dataList);
        writeToFile("Team Chart", "Team_Chart", document, false);
    }

    private void addSecerateryTable(XWPFDocument document) {
        String secerateryName = configService.getSecerateryName();
        XWPFTable table = document.createTable(1, 1);
        alignTableToTheCenterOfPage(table);
        addTableStyle(table, Constants.WORD_DOCUMENT_TABLE_STYLE_ID);
        writeTextToCell(table.getRow(0).getCell(0), secerateryName, true, ":");
    }

    private void addAdminsTable(XWPFDocument document) {
        String adminNames = configService.getAdminNames();
        List<String> adminNamesList = Arrays.asList(adminNames.split(","));
        XWPFTable table = document.createTable(1, adminNamesList.size());
        addTableStyle(table, "ListTable5Dark-Accent1");
        setTableCellSpacing(table,120);
        setTableBorderType(STBorder.NONE, table);
        alignTableToTheCenterOfPage(table);
        addTableStyle(table, Constants.WORD_DOCUMENT_TABLE_STYLE_ID);
        int cellCounter = 0;
        for (String eachAdmin : adminNamesList) {
            writeTextToCell(table.getRow(0).getCell(cellCounter++), eachAdmin, true, ":");
        }
    }

    private void addTeamLeadAndSevadarsInformation(XWPFDocument document, Map<String, List<String>> dataList) {
        XWPFTable table = document.createTable(1, dataList.size());
        alignTableToTheCenterOfPage(table);
        addTableStyle(table, "ListTable5Dark-Accent1");
        setTableCellMargin(table, 0, 75);
        setTableCellSpacing(table,120);
        setTableBorderType(STBorder.NONE, table);

        int cellCounter = 0;
        for (Map.Entry<String, List<String>> entry : dataList.entrySet()) {
            String teamLeadName = entry.getKey();
            List<String> sevadarsList = entry.getValue();

            XWPFTableRow tableRow = table.getRow(0);
            XWPFTableCell cell = tableRow.getCell(cellCounter);
            //writeTextToCell(cell, teamLeadName, true, ":");
            writeTextToCell(cell,teamLeadName,"MyStrongStyle",null,false,-1,true,":");

            int rowCounter = 1;
            for (String sevadar : sevadarsList) {
                int totalRowsInTable = table.getNumberOfRows();
                if (totalRowsInTable > rowCounter) {
                    tableRow = table.getRow(rowCounter);
                } else {
                    tableRow = table.createRow();
                }

                cell = tableRow.getCell(cellCounter);
                //writeTextToCell(cell, sevadar, true, ":");
                writeTextToCell(cell,sevadar,"MyStrongStyle",null,false,-1,true,":");
                rowCounter++;
            }
            cellCounter++;
        }
    }



}
