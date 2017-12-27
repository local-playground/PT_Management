package org.rssb.phonetree.common.file.word;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.rssb.phonetree.common.file.ReportName;
import org.rssb.phonetree.domain.SevadarPhoneTreeList;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;

@Component
public class NormalPhoneTreeDocumentWriter extends AbstractWordDocumentWriter {

    public NormalPhoneTreeDocumentWriter() {
        this.reportName = ReportName.NORMAL_REPORT;
    }

    @Override
    public void writeToFile(SevadarPhoneTreeList sevadarPhoneTreeList) {
        XWPFDocument document = new XWPFDocument();
        addHeaderAndFooter(document);

        //create table
        XWPFTable table = document.createTable(2, 3);
        setTableWidthToFullPage(table);

        //create first row
        XWPFTableRow tableRowOne = table.getRow(0);
        XWPFParagraph p = tableRowOne.getCell(0).getParagraphArray(0);
        p.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun run = p.createRun();
        run.setBold(true);
        run.setFontFamily("Calibri");
        run.setFontSize(11);
        run.setText("Team Lead: "+sevadarPhoneTreeList.getTeamLeadPersonalInformation().getName());
        run.addBreak();
        run.setText("Cell: "+sevadarPhoneTreeList.getTeamLeadPersonalInformation().getCellPhone());
        run.addBreak();
        run.setText("Home: "+sevadarPhoneTreeList.getTeamLeadPersonalInformation().getHomePhone());
        run.addBreak();
        run.addBreak();

        run.setText("Backup Team Lead: "+sevadarPhoneTreeList.getBackupTeamLeadPersonalInformation().getName());
        run.addBreak();
        run.setText("Cell: "+sevadarPhoneTreeList.getBackupTeamLeadPersonalInformation().getCellPhone());
        run.addBreak();
        run.setText("Home: "+sevadarPhoneTreeList.getBackupTeamLeadPersonalInformation().getHomePhone());

        tableRowOne.getCell(0).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(5040));

        p = tableRowOne.getCell(1).getParagraphArray(0);
        p.setAlignment(ParagraphAlignment.CENTER);
        run = p.createRun();
        run.setText("MASTER TREE");
        run.setBold(true);
        run.setFontFamily("Calibri");
        run.setFontSize(18);

        tableRowOne.getCell(1).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(5040));

        p = tableRowOne.getCell(2).getParagraphArray(0);
        p.setAlignment(ParagraphAlignment.LEFT);
        run = p.createRun();
        run.setBold(true);
        run.setFontFamily("Calibri");
        run.setFontSize(11);
        run.setText("Sevadar: "+sevadarPhoneTreeList.getSevadarPersonalInformation().getName());
        run.addBreak();
        run.setText("Cell: "+sevadarPhoneTreeList.getSevadarPersonalInformation().getCellPhone());
        run.addBreak();
        run.setText("Home: "+sevadarPhoneTreeList.getSevadarPersonalInformation().getHomePhone());
        run.addBreak();
        run.addBreak();
        tableRowOne.getCell(2).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(5040));

        //create second row
        XWPFTableRow tableRowTwo = table.getRow(1);
        //tableRowTwo.getCell(0).setText("Time Notified: ");

        p = tableRowTwo.getCell(0).getParagraphArray(0);
        p.setAlignment(ParagraphAlignment.LEFT);
        run = p.createRun();
        run.setBold(true);
        run.setFontFamily("Calibri");
        run.setFontSize(11);
        run.setText("Time Notified: ");


        //tableRowTwo.getCell(1).setText("No. of families to call " + sevadarPhoneTreeList.getTotalFamiliesToCall());

        p = tableRowTwo.getCell(1).getParagraphArray(0);
        p.setAlignment(ParagraphAlignment.CENTER);
        run = p.createRun();
        run.setBold(true);
        run.setFontFamily("Calibri");
        run.setFontSize(14);
        run.setText("No. of families to call " + sevadarPhoneTreeList.getTotalFamiliesToCall());

        //tableRowTwo.getCell(2).setText("Feedback Time:");
        p = tableRowTwo.getCell(2).getParagraphArray(0);
        p.setAlignment(ParagraphAlignment.LEFT);
        run = p.createRun();
        run.setBold(true);
        run.setFontFamily("Calibri");
        run.setFontSize(11);
        run.setText("Feedback Time: ");


        //Write the Document in file system
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File("C:\\Rajesh\\Sample\\createdocument.docx"));
            document.write(out);
            out.close();
            System.out.println("createdocument.docx written successully");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}



