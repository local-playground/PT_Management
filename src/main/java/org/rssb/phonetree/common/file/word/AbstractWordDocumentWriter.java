package org.rssb.phonetree.common.file.word;


import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
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
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.rssb.phonetree.common.CommonUtil;
import org.rssb.phonetree.common.file.ColumnInformation;
import org.rssb.phonetree.common.file.DocumentWriter;
import org.rssb.phonetree.common.file.ReportFormat;
import org.rssb.phonetree.common.file.ReportName;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

@Component
public abstract class AbstractWordDocumentWriter<T> implements DocumentWriter<T> {
    protected ReportName reportName;
    protected List<ColumnInformation> documentTableColumns = new ArrayList<>();

    @Value("${org.rssb.phonetree.file-password-protection}")
    private String usePassword;

    @Value("${org.rssb.phonetree.list-output-directory}")
    protected String listOutputDirectory;


    @Override
    public ReportFormat supportsReportFormat() {
        return ReportFormat.WORD;
    }

    @Override
    public ReportName supportsReportName() {
        return reportName;
    }

    @Override
    public void addColumnsToDocument(List<ColumnInformation> documentTableColumnList) {
        this.documentTableColumns = documentTableColumnList;
    }

    protected void addPasswordProtection(String filePath) {
        //Add password protection and encrypt the file
        POIFSFileSystem fs = new POIFSFileSystem();
        EncryptionInfo info = null;
        try {
            info = new EncryptionInfo(EncryptionMode.agile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("password to use " + usePassword);

        Encryptor enc = info.getEncryptor();
        enc.confirmPassword(usePassword);

        OPCPackage opc = null;
        try {
            opc = OPCPackage.open(new File(filePath), PackageAccess.READ_WRITE);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

        try {
            OutputStream os = enc.getDataStream(fs);
            opc.save(os);
            opc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (FileOutputStream fos = new FileOutputStream(new File(filePath))) {
            fs.writeFilesystem(fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void addHeaderAndFooter(XWPFDocument document) {
        setPageSize(document);
        XWPFHeader hdr = document.createHeader(HeaderFooterType.DEFAULT);
        XWPFTable headerTable = hdr.createTable(1, 3);

        //remove boder around table (outer, inner)
        setTableBorderType(STBorder.NONE, headerTable);
        setTableWidthToFullPage(headerTable);

        // Add paragraphs to the cells
        XWPFTableRow row = headerTable.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        XWPFParagraph p = cell.getParagraphArray(0);
        p.setAlignment(ParagraphAlignment.LEFT);
        writeTextToParagraph(p, "Ravi Gulati - (646) 236 - 2940",true,null,14);

        cell = row.getCell(1);
        p = cell.getParagraphArray(0);
        p.setAlignment(ParagraphAlignment.CENTER);
        writeTextToParagraph(p, "RSSB – PISCATAWAY CENTER - NJ",true,null,14);

        cell = row.getCell(2);
        p = cell.getParagraphArray(0);
        p.setAlignment(ParagraphAlignment.RIGHT);
        writeTextToParagraph(p, "Sanjay Phanda – (917) 750 - 7921",true,null,14);

        // Create a footer with a Paragraph
        XWPFFooter footer = document.createFooter(HeaderFooterType.DEFAULT);
        p = footer.createParagraph();
        writeTextToParagraph(p, "Updated on - " +
                LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)),
                true,null,12);
    }

    protected void setTableBorderType(STBorder.Enum border, XWPFTable table) {
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

    protected void setTableWidthToFullPage(XWPFTable table) {
        CTTbl cttTable = table.getCTTbl();
        CTTblPr pr = cttTable.getTblPr();
        CTTblWidth tblW = pr.getTblW();
        tblW.setW(BigInteger.valueOf(5000));
        tblW.setType(STTblWidth.PCT);
        pr.setTblW(tblW);
        cttTable.setTblPr(pr);
    }

    protected void createParagraphRunWithTex(XWPFParagraph p, String text) {
        XWPFRun r = p.createRun();
        r.setText(text);
        r.setBold(true);
        r.setFontFamily("Calibri");
        r.setFontSize(14);
    }

    protected void setPageSize(XWPFDocument document) {
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
        if (pageMar == null) {
            pageMar = section.addNewPgMar();
        }
        pageMar.setLeft(BigInteger.valueOf(720L));
        pageMar.setTop(BigInteger.valueOf(0L));
        pageMar.setRight(BigInteger.valueOf(720L));
        pageMar.setBottom(BigInteger.valueOf(0L));
    }

    protected void writeTextToParagraph(XWPFParagraph paragraph,String text){
        writeTextToParagraph(paragraph,text,false);
    }

    protected void writeTextToParagraph(XWPFParagraph paragraph,String text,boolean isBold){
        writeTextToParagraph(paragraph,text,isBold,null);
    }

    protected void writeTextToParagraph(XWPFParagraph paragraph,String text,boolean isBold,
                                        ParagraphAlignment paragraphAlignment){
        writeTextToParagraph(paragraph,text,isBold,paragraphAlignment,-1);
    }

    protected void writeTextToParagraph(XWPFParagraph paragraph,String text,boolean isBold,
                                        ParagraphAlignment paragraphAlignment,int fontSize){

        if(paragraphAlignment!=null){
            paragraph.setAlignment(paragraphAlignment);
        }
        XWPFRun r = paragraph.createRun();
        if(isBold) {
            r.setBold(isBold);
        }
        if(fontSize!=-1){
            r.setFontSize(fontSize);
        }
        r.setText(text);

    }

    protected void writeTextToCell(XWPFTableCell cell, String styleName,String text) {
        writeTextToCell(cell,text,styleName,null,false,-1,false,"");
    }

    protected void writeTextToCell(XWPFTableCell cell,boolean isBold,String text){
        writeTextToCell(cell,isBold,-1,text);
    }

    protected void writeTextToCell(XWPFTableCell cell,boolean isBold,int fontSize,String text){
        writeTextToCell(cell,text,null,null,isBold,fontSize,false,"");
    }

    protected void writeTextToCell(XWPFTableCell cell,boolean isBold,int fontSize,
                               String text,ParagraphAlignment paragraphAlignment){
        writeTextToCell(cell,text,null,paragraphAlignment,isBold,fontSize,false,"");
    }

    protected void writeTextToCell(XWPFTableCell cell,String text,boolean splitToken,String delimiter){
        writeTextToCell(cell,text,null,null,false,-1,splitToken,delimiter);
    }

    protected void writeTextToCell(XWPFTableCell cell,String text,String styleId,ParagraphAlignment paragraphAlignment,
                                 boolean isBold,int fontSize,boolean splitToken,String delimiter){

        XWPFParagraph p = cell.getParagraphArray(0);
        if(paragraphAlignment!=null){
            p.setAlignment(paragraphAlignment);
        }
        if(CommonUtil.isNotEmptyOrNull(styleId)){
            p.setStyle(styleId);
        }
        XWPFRun run = p.createRun();
        if(isBold){
            run.setBold(true);
        }

        if(fontSize!=-1){
            run.setFontSize(fontSize);
        }

        if(splitToken){
            if(CommonUtil.isNotEmptyOrNull(text)) {
                String[] str = text.split(delimiter);
                int counter = 0;
                for (String data : str) {
                    run.setText(data);
                    counter++;
                    if (counter >= str.length) {
                        break;
                    }
                    run.addBreak();
                }
            }else{
                run.setText(text);
            }
        }else{
            run.setText(text);
        }

    }


    protected void writeToFile(String directoryName,String fileName,XWPFDocument document,boolean isPasswordProtected){
        String finalDirectoryName = listOutputDirectory+"\\"+directoryName;
        if(!Files.exists(Paths.get(directoryName))){
            try {
                Files.createDirectories(Paths.get(finalDirectoryName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String path = finalDirectoryName+"\\"+fileName+".docx";

        try (FileOutputStream out = new FileOutputStream(new File(path))){
            document.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(isPasswordProtected) {
            addPasswordProtection(path);
        }

    }

    protected void alignTableToTheCenterOfPage(XWPFTable table){
        table.getCTTbl().getTblPr().addNewJc().setVal(STJc.CENTER);
    }


    protected void addLineBreak(XWPFDocument document, int numberOfLines) {
        for (int index = 0; index < numberOfLines; index++) {
            document.createParagraph().createRun().addBreak();
        }
    }


     /*// Set the padding around text in the cells to 1/10th of an inch
        int pad = (int) (.1 * 1440);
        headerTable.setCellMargins(pad, pad, pad, pad);*/

    // Set table width to 6.5 inches in 1440ths of a point
        /*headerTable.setWidth((int) (6.5 * 1440));*/
}
