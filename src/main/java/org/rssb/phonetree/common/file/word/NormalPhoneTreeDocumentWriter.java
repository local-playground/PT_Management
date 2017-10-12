package org.rssb.phonetree.common.file.word;

import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHdrFtr;
import org.rssb.phonetree.common.file.ReportName;
import org.rssb.phonetree.domain.SevadarPhoneTreeList;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;

@Component
public class NormalPhoneTreeDocumentWriter extends AbstractWordDocumentWriter{

    public NormalPhoneTreeDocumentWriter() {
        this.reportName = ReportName.NORMAL_REPORT;
    }

    @Override
    public void writeToFile(SevadarPhoneTreeList sevadarPhoneTreeList) {
        //Blank Document
        /*XWPFDocument document = new XWPFDocument();

        XWPFHeaderFooterPolicy policy = document.createHeaderFooterPolicy();

        XWPFHeader header = document.createHeader(HeaderFooterType.DEFAULT);


        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText("Sanjay Phanda");
        run.setBold(true);
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        policy.createHeader(XWPFHeaderFooterPolicy.DEFAULT,new XWPFParagraph[]{paragraph});

        document.createFooter(HeaderFooterType.DEFAULT);

        policy.createFooter(XWPFHeaderFooterPolicy.DEFAULT, new XWPFParagraph[]{paragraph});
*/

        XWPFDocument document = new XWPFDocument();
        CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
        XWPFHeaderFooterPolicy headerFooterPolicy = new  XWPFHeaderFooterPolicy( document, sectPr );
        //write header content
        CTP ctpHeader = CTP.Factory.newInstance();
        CTR ctrHeader = ctpHeader.addNewR();
        CTText ctHeader = ctrHeader.addNewT();
        String headerText = "Sanjay Phanda";
        ctHeader.setStringValue(headerText);
        XWPFParagraph headerParagraph = new XWPFParagraph(ctpHeader, document);
        headerParagraph.setAlignment(ParagraphAlignment.LEFT);

        CTText ctHeader1 = ctrHeader.addNewT();
        String headerText1 = "Ravi Gulati";
        ctHeader1.setStringValue(headerText1);
        XWPFParagraph headerParagraph2 = new XWPFParagraph(ctpHeader, document);
        headerParagraph2.setAlignment(ParagraphAlignment.RIGHT);


        XWPFParagraph[] parsHeader = new XWPFParagraph[2];
        parsHeader[0] = headerParagraph;
        parsHeader[1] = headerParagraph2;
        headerFooterPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT, parsHeader);


        CTP ctp = CTP.Factory.newInstance();
        CTR ctr = ctp.addNewR();
        //CTRPr rpr = ctr.addNewRPr();
        CTText textt = ctr.addNewT();
        textt.setStringValue( " Page 1" );
        XWPFParagraph codePara = new XWPFParagraph( ctp, document );
        XWPFParagraph[] newparagraphs = new XWPFParagraph[1];
        newparagraphs[0] = codePara;

        headerFooterPolicy.createFooter( STHdrFtr.DEFAULT, newparagraphs );

        //Write the Document in file system
        FileOutputStream out = null;
        try {
            out = new FileOutputStream( new File("C:\\Rajesh\\Sample\\createdocument.docx"));
            document.write(out);
            out.close();
            System.out.println("createdocument.docx written successully");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
