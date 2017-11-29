package org.rssb.phonetree.entity.converters;

import org.rssb.phonetree.common.CommonUtil;
import org.rssb.phonetree.domain.VacationDate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Converter(autoApply = false)
public class VacationDatesConverter implements AttributeConverter<List<VacationDate>,String>{

    private static final String DATE_FORMAT = "yyyy-MM-dd";

   /* @Override
    public String convertToDatabaseColumn(List<String> vacationDateList) {
        if(CommonUtil.isCollectionEmpty(vacationDateList))
            return "";

        return vacationDateList
                .stream()
                .map(vacationDate -> vacationDate.toString())
                .collect(Collectors.joining(","));
    }

    @Override
    public List<String> convertToEntityAttribute(String s) {
        if(CommonUtil.isEmptyOrNull(s)){
            return new ArrayList<>();
        }
        return new ArrayList<>(Arrays.asList(s.split(",")));
    }*/

    @Override
    public String convertToDatabaseColumn(List<VacationDate> vacationDateList) {
        if(CommonUtil.isCollectionEmpty(vacationDateList))
            return "";

        return vacationDateList
                .stream()
                .map(vacationDate -> vacationDate.toString())
                .collect(Collectors.joining(","));
    }

    @Override
    public List<VacationDate> convertToEntityAttribute(String s) {
        if(CommonUtil.isEmptyOrNull(s)){
            return new ArrayList<>();
        }
        System.out.println("Vacation dates from DB = " + s);
        List<VacationDate> vacationDateList = new ArrayList<>();
        String[] vacationDatesArray = s.split(",");
        for (String offDate : vacationDatesArray) {
            String[] fromToDates = offDate.split(":");
            String fromDate = fromToDates[0];
            String toDate = fromToDates[1];
            System.out.println("working on from date - "+fromDate+ " todate - "+toDate);

            VacationDate vacationDate = new VacationDate();
            /*vacationDate.setFromDate(fromDate);
            vacationDate.setToDate(toDate);*/
            vacationDate.setFromDate(LocalDate.parse(fromDate, DateTimeFormatter.ofPattern(DATE_FORMAT)));
            vacationDate.setToDate(LocalDate.parse(toDate,DateTimeFormatter.ofPattern(DATE_FORMAT)));
            vacationDateList.add(vacationDate);
        }
        return vacationDateList;
    }
}
