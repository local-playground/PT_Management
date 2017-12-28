package org.rssb.phonetree.common.file.word;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.rssb.phonetree.common.CommonUtil;
import org.rssb.phonetree.common.file.DocumentTableColumn;
import org.rssb.phonetree.common.file.ReportName;
import org.rssb.phonetree.domain.CalledFamilyDetails;
import org.rssb.phonetree.domain.SevadarPersonalInformation;
import org.rssb.phonetree.domain.SevadarPhoneTreeList;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.List;

@Component
public class NormalPhoneTreeDocumentWriter extends AbstractWordDocumentWriter {

    public NormalPhoneTreeDocumentWriter() {
        this.reportName = ReportName.NORMAL_REPORT;
    }

    @Override
    public void writeToFile(SevadarPhoneTreeList sevadarPhoneTreeList) {
        XWPFDocument document = new XWPFDocument();
        addHeaderAndFooter(document);
        createTeamLeadAndSevadarTable(sevadarPhoneTreeList, document);
        document.createParagraph().createRun().addBreak();
        createFamilyInformationTable(sevadarPhoneTreeList, document);
        document.createParagraph().createRun().addBreak();
        document.createParagraph().createRun().addBreak();
        createFeedbackInformationTable(document);
        //Write the Document in file system
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File("C:\\Rajesh\\Sample\\createdocument.docx"));
            document.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void createFeedbackInformationTable(XWPFDocument document) {
        XWPFRun run = document.createParagraph().createRun();
        run.setFontSize(16);
        run.setBold(true);
        run.setFontFamily("Calibri");
        run.setText("Your feedback / experience or suggestions will help us to do better in future (take 2-3 days to get back).");
        XWPFTable table = document.createTable(1, 1);
        setTableWidthToFullPage(table);
        table.getRow(0).setHeight(3200);
    }

    private void createFamilyInformationTable(SevadarPhoneTreeList sevadarPhoneTreeList, XWPFDocument document) {
        int size = sevadarPhoneTreeList.getCalledFamilyDetailsList().size();
        int numberOfColumns = documentTableColumns.size();
        int numberOfRows = size + 1;
        XWPFTable table = document.createTable(numberOfRows, numberOfColumns);
        setTableWidthToFullPage(table);

        List<CalledFamilyDetails> calledFamilyDetailsList = sevadarPhoneTreeList.getCalledFamilyDetailsList();

        for (int index = 0; index < numberOfRows; index++) {
            XWPFTableRow tableRow = table.getRow(index);
            tableRow.setRepeatHeader(true);
            tableRow.setCantSplitRow(true);
            if (index == 0) {
                populateColumnsData(tableRow, index, null);
                continue;
            }
            CalledFamilyDetails calledFamilyDetails = calledFamilyDetailsList.get(index - 1);
            populateColumnsData(tableRow, index, calledFamilyDetails);
        }
    }

    private void populateColumnsData(XWPFTableRow row, int index, CalledFamilyDetails calledFamilyDetails) {
        if (index == 0) {
            for (int columns = 0; columns < documentTableColumns.size(); columns++) {
                DocumentTableColumn documentTableColumn = documentTableColumns.get(columns);
                XWPFTableCell cell = row.getCell(columns);
                writeTextToCell(cell, ParagraphAlignment.CENTER, documentTableColumn.getColumnName(), 12);
            }
            return;
        }
        for (int columns = 0; columns < documentTableColumns.size(); columns++) {
            DocumentTableColumn documentTableColumn = documentTableColumns.get(columns);
            XWPFTableCell cell = row.getCell(columns);
            writeFamilyInformation(cell, calledFamilyDetails,documentTableColumn);
        }
    }

    private void createTeamLeadAndSevadarTable(SevadarPhoneTreeList sevadarPhoneTreeList, XWPFDocument document) {
        //create table
        XWPFTable table = document.createTable(2, 3);
        setTableWidthToFullPage(table);

        //create first row
        XWPFTableRow tableRowOne = table.getRow(0);
        XWPFTableCell cell = tableRowOne.getCell(0);
        XWPFRun run = writeSevadarInformation(cell, null, sevadarPhoneTreeList.getTeamLeadPersonalInformation(), "Team Lead: ", 2);
        writeSevadarInformation(cell, run, sevadarPhoneTreeList.getBackupTeamLeadPersonalInformation(), "Backup Team Lead: ", 0);

        cell = tableRowOne.getCell(1);
        writeTextToCell(cell, ParagraphAlignment.CENTER, "MASTER TREE", 18);

        cell = tableRowOne.getCell(2);
        writeSevadarInformation(cell, null, sevadarPhoneTreeList.getSevadarPersonalInformation(), "Sevadar", 0);


        //create second row
        XWPFTableRow tableRowTwo = table.getRow(1);

        cell = tableRowTwo.getCell(0);
        writeTextToCell(cell, ParagraphAlignment.LEFT, "Time Notified: ", 11);

        cell = tableRowTwo.getCell(1);
        writeTextToCell(cell, ParagraphAlignment.CENTER, "No. of families to call " + sevadarPhoneTreeList.getTotalFamiliesToCall(), 14);

        cell = tableRowTwo.getCell(2);
        writeTextToCell(cell, ParagraphAlignment.LEFT, "Feedback Time: ", 11);
    }

    private void writeTextToCell(XWPFTableCell cell, ParagraphAlignment alignment, String text, int fontSize) {
        XWPFParagraph p = cell.getParagraphArray(0);
        p.setAlignment(alignment);
        XWPFRun run = p.createRun();
        run.setBold(true);
        run.setFontFamily("Calibri");
        run.setFontSize(fontSize);
        run.setText(text);
    }

    private XWPFRun writeSevadarInformation(XWPFTableCell cell,
                                            XWPFRun run,
                                            SevadarPersonalInformation sevadarPersonalInformation,
                                            String title, int numberOfBreaks) {
        XWPFParagraph p = null;
        cell.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(5040));
        if (run == null) {
            p = cell.getParagraphArray(0);
            p.setAlignment(ParagraphAlignment.LEFT);
            run = p.createRun();
        }
        run.setBold(true);
        run.setFontFamily("Calibri");
        run.setFontSize(11);
        run.setText(title + sevadarPersonalInformation.getName());
        run.addBreak();
        run.setText("Cell: " + sevadarPersonalInformation.getCellPhone());
        run.addBreak();
        run.setText("Home: " + sevadarPersonalInformation.getHomePhone());
        for (int index = 0; index < numberOfBreaks; index++) {
            run.addBreak();
        }

        return run;
    }

    private void writeFamilyInformation(XWPFTableCell cell,
                                        CalledFamilyDetails calledFamilyDetails,
                                        DocumentTableColumn documentTableColumn) {
        XWPFParagraph p = p = cell.getParagraphArray(0);
        p.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun run = p.createRun();

        run.setFontFamily("Calibri");
        run.setFontSize(11);

        if(documentTableColumn.getColumnWidth()!=null) {
            cell.getCTTc().addNewTcPr().addNewTcW().setW(documentTableColumn.getColumnWidth());
        }

        if (documentTableColumn == DocumentTableColumn.FAMILY_INFORMATION) {
            run.setText((calledFamilyDetails.getFirstName() + " " +calledFamilyDetails.getLastName()).toUpperCase());
            if(CommonUtil.isNotEmptyOrNull(calledFamilyDetails.getCellPhone())) {
                run.addBreak();
                run.setText("C: " + calledFamilyDetails.getCellPhone());
            }
            if (CommonUtil.isNotEmptyOrNull(calledFamilyDetails.getHomePhone())) {
                run.addBreak();
                run.setText("H: " + calledFamilyDetails.getHomePhone());
            }
            if (CommonUtil.isNotEmptyOrNull(calledFamilyDetails.getWorkPhone())) {
                run.addBreak();
                run.setText("W: " + calledFamilyDetails.getWorkPhone());
            }
        }else{
            run.setText(calledFamilyDetails.getColumnValue(documentTableColumn));
        }

    }

}



