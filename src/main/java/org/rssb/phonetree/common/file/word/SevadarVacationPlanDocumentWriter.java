package org.rssb.phonetree.common.file.word;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.rssb.phonetree.common.Constants;
import org.rssb.phonetree.common.file.ReportName;
import org.rssb.phonetree.domain.SevadarVacationPlan;
import org.rssb.phonetree.domain.SevadarsMonthlyAvailability;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class SevadarVacationPlanDocumentWriter
        extends AbstractWordDocumentWriter<Map<String,List<SevadarVacationPlan>>> {

    public SevadarVacationPlanDocumentWriter() {
        this.reportName = ReportName.VACATION_SUMMARY;
    }

    @Override
    public void writeToFile(Map<String, List<SevadarVacationPlan>> dataList) {
        XWPFDocument document = null;
        try {
            document = new XWPFDocument(new FileInputStream("C:\\Rajesh\\Sample\\Template.docx"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setPageSize(document);
        addHeaderAndFooter(document);
        Iterator<String> iterator = dataList.keySet().iterator();
        while(iterator.hasNext()){
            String key = iterator.next();
            List<SevadarVacationPlan> sevadarVacationPlanList = dataList.get(key);
            createVacationSummaryTable(key, sevadarVacationPlanList, document);
        }
        writeToFile("Sevadar Vacation","Vacation_Summary",document,false);
    }

    private void createVacationSummaryTable(String sevadarName,
                                            List<SevadarVacationPlan> vacationPlanList,
                                            XWPFDocument document) {

        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setStyle("Heading1");
        paragraph.createRun().setText(sevadarName);

        XWPFTable table = document.createTable(vacationPlanList.size() + 2, 10);
        addTableStyle(table, Constants.WORD_DOCUMENT_TABLE_STYLE_ID);
        setTableWidthToFullPage(table);
        populateHeaders(table);
        List<String> monthsList = getMonthsName(vacationPlanList.get(0));
        populateMonthsInHeader(monthsList,table);
        int rowCounter=2;
        for(SevadarVacationPlan sevadarVacationPlan:vacationPlanList){
            populateValues(rowCounter, table,sevadarVacationPlan);
            rowCounter++;
        }
    }


    private void populateHeaders(XWPFTable table){
        XWPFTableRow tableRow = table.getRow(1);

        tableRow.getCell(0).setText("Name");
        tableRow.getCell(1).setText("Available");
        tableRow.getCell(2).setText("Out");
        tableRow.getCell(3).setText("Days Out");

        tableRow.getCell(4).setText("Available");
        tableRow.getCell(5).setText("Out");
        tableRow.getCell(6).setText("Days Out");

        tableRow.getCell(7).setText("Available");
        tableRow.getCell(8).setText("Out");
        tableRow.getCell(9).setText("Days Out");


    }

    private void populateValues(int row,XWPFTable table, SevadarVacationPlan sevadarVacationPlan) {
        XWPFTableRow tableRow = table.getRow(row);

        tableRow.getCell(0).setText(sevadarVacationPlan.getSevadarName());

        List<SevadarsMonthlyAvailability> availabilities = sevadarVacationPlan.getSevadarsMonthlyAvailabilities();
        int cellCounter=1;
        for(SevadarsMonthlyAvailability availability:availabilities){
            writeTextToCell(tableRow.getCell(cellCounter++),availability.getAvailableDates(),true,",");
            String outDates = availability.getOutDates().equalsIgnoreCase("NO")?"":availability.getOutDates();
            writeTextToCell(tableRow.getCell(cellCounter++),outDates,true,",");
            String daysOut = String.valueOf(availability.getTotalDaysOut()==0?"":availability.getTotalDaysOut());
            writeTextToCell(tableRow.getCell(cellCounter++),daysOut,false,"");
        }
    }


    private void populateMonthsInHeader(List<String> monthsList,
                                        XWPFTable table){

        XWPFTableRow tableRow = table.getRow(0);
        XWPFTableCell cellRow1 = tableRow.getCell(1);


        cellRow1.getCTTc().addNewTcPr();
        cellRow1.getCTTc().getTcPr().addNewGridSpan();
        cellRow1.getCTTc().getTcPr().getGridSpan().setVal(BigInteger.valueOf(3));

        cellRow1.setText(monthsList.get(0));

        tableRow = table.getRow(0);
        cellRow1 = tableRow.getCell(2);

        cellRow1.getCTTc().addNewTcPr();
        cellRow1.getCTTc().getTcPr().addNewGridSpan();
        cellRow1.getCTTc().getTcPr().getGridSpan().setVal(BigInteger.valueOf(3));

        cellRow1.setText(monthsList.get(1));

        tableRow = table.getRow(0);
        cellRow1 = tableRow.getCell(3);

        cellRow1.getCTTc().addNewTcPr();
        cellRow1.getCTTc().getTcPr().addNewGridSpan();
        cellRow1.getCTTc().getTcPr().getGridSpan().setVal(BigInteger.valueOf(3));

        cellRow1.setText(monthsList.get(2));


        tableRow.getCell(4).getCTTc().newCursor().removeXml();
        tableRow.removeCell(4);

        tableRow.getCell(4).getCTTc().newCursor().removeXml();
        tableRow.removeCell(4);

        tableRow.getCell(4).getCTTc().newCursor().removeXml();
        tableRow.removeCell(4);

        tableRow.getCell(4).getCTTc().newCursor().removeXml();
        tableRow.removeCell(4);

        tableRow.getCell(4).getCTTc().newCursor().removeXml();
        tableRow.removeCell(4);

        tableRow.getCell(4).getCTTc().newCursor().removeXml();
        tableRow.removeCell(4);



    }

    private List<String> getMonthsName(SevadarVacationPlan sevadarVacationPlan){
        List<String> monthsList = new ArrayList<>();
        for(SevadarsMonthlyAvailability sevadarsMonthlyAvailability:
                sevadarVacationPlan.getSevadarsMonthlyAvailabilities()){
            monthsList.add(sevadarsMonthlyAvailability.getMonthName());
        }

        return monthsList;
    }
}
