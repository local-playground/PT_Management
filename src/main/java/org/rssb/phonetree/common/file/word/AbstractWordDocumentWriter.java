package org.rssb.phonetree.common.file.word;


import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDocument1;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.rssb.phonetree.common.file.DocumentTableColumn;
import org.rssb.phonetree.common.file.DocumentWriter;
import org.rssb.phonetree.common.file.ReportFormat;
import org.rssb.phonetree.common.file.ReportName;
import org.rssb.phonetree.domain.SevadarPhoneTreeList;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractWordDocumentWriter implements DocumentWriter{
    protected ReportName reportName;
    protected List<DocumentTableColumn> documentTableColumns = Arrays.asList(
            DocumentTableColumn.SEQ_NO,
            DocumentTableColumn.FAMILY_INFORMATION,
            DocumentTableColumn.TIME_OF_CALL,
            DocumentTableColumn.TIME_OF_VM,
            DocumentTableColumn.COMMENTS
    );


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

    protected void addHeaderAndFooter(XWPFDocument document) {
        setPageSize(document);
        XWPFHeader hdr = document.createHeader(HeaderFooterType.DEFAULT);
        XWPFTable headerTable = hdr.createTable(1, 3);

        //remove boder around table (outer, inner)
        setTableBorderType(STBorder.NONE,headerTable);
        setTableWidthToFullPage(headerTable);

        // Add paragraphs to the cells
        XWPFTableRow row = headerTable.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        XWPFParagraph p = cell.getParagraphArray(0);
        p.setAlignment(ParagraphAlignment.LEFT);
        createParagraphRunWithText(p,"Ravi Gulati - (646) 236 - 2940");

        cell = row.getCell(1);
        p = cell.getParagraphArray(0);
        p.setAlignment(ParagraphAlignment.CENTER);
        createParagraphRunWithText(p,"RSSB – PISCATAWAY CENTER - NJ");

        cell = row.getCell(2);
        p = cell.getParagraphArray(0);
        p.setAlignment(ParagraphAlignment.RIGHT);
        createParagraphRunWithText(p,"Sanjay Phanda – (917) 750 - 7921");

        // Create a footer with a Paragraph
        XWPFFooter footer = document.createFooter(HeaderFooterType.DEFAULT);
        p = footer.createParagraph();
        createParagraphRunWithText(p,"Updated on - "+ LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
    }

    protected void setTableBorderType(STBorder.Enum border, XWPFTable table){
        CTTbl ctTbl = table.getCTTbl();
        CTTblPr tblpro = ctTbl.getTblPr();
        CTTblBorders borders = tblpro.addNewTblBorders();
        borders.addNewBottom().setVal(border);
        borders.addNewLeft().setVal(border);
        borders.addNewRight().setVal(border);
        borders.addNewTop().setVal(border);

        borders.addNewInsideH().setVal(border);
        borders.addNewInsideV().setVal(border);

    }
    protected void setTableWidthToFullPage(XWPFTable table){
        CTTbl cttTable = table.getCTTbl();
        CTTblPr pr = cttTable.getTblPr();
        CTTblWidth tblW = pr.getTblW();
        tblW.setW(BigInteger.valueOf(5000));
        tblW.setType(STTblWidth.PCT);
        pr.setTblW(tblW);
        cttTable.setTblPr(pr);
    }

    protected void createParagraphRunWithText(XWPFParagraph p,String text) {
        XWPFRun r = p.createRun();
        r.setText(text);
        r.setBold(true);
        r.setFontFamily("Calibri");
        r.setFontSize(14);
    }

    protected void setPageSize(XWPFDocument document){
        CTDocument1 document1 = document.getDocument();
        CTBody body = document1.getBody();

        if (!body.isSetSectPr()) {
            body.addNewSectPr();
        }
        CTSectPr section = body.getSectPr();

        if (!section.isSetPgSz()) {
            section.addNewPgSz();
        }
        CTPageSz pageSize = section.getPgSz();

        pageSize.setOrient(STPageOrientation.LANDSCAPE);
        pageSize.setW(BigInteger.valueOf(15840));
        pageSize.setH(BigInteger.valueOf(12240));

        CTPageMar pageMar = section.getPgMar();
        if(pageMar==null){
            pageMar = section.addNewPgMar();
        }
        pageMar.setLeft(BigInteger.valueOf(720L));
        pageMar.setTop(BigInteger.valueOf(0L));
        pageMar.setRight(BigInteger.valueOf(720L));
        pageMar.setBottom(BigInteger.valueOf(0L));
    }



     /*// Set the padding around text in the cells to 1/10th of an inch
        int pad = (int) (.1 * 1440);
        headerTable.setCellMargins(pad, pad, pad, pad);*/

    // Set table width to 6.5 inches in 1440ths of a point
        /*headerTable.setWidth((int) (6.5 * 1440));*/
}
