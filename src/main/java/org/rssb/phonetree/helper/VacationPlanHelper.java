package org.rssb.phonetree.helper;

import org.rssb.phonetree.common.CommonUtil;
import org.rssb.phonetree.domain.VacationDate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VacationPlanHelper {
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public static String convertToDatabaseColumn(List<VacationDate> vacationDateList) {
        if(CommonUtil.isCollectionEmpty(vacationDateList))
            return "";

        String str = vacationDateList
                .stream()
                .map(VacationDate::toString)
                .collect(Collectors.joining(","));

        System.out.println("converting list to String "+str);
        return str;
    }

    public static List<VacationDate>  convertToEntityAttribute(String s) {
        if(CommonUtil.isEmptyOrNull(s)){
            return new ArrayList<VacationDate>();
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
            vacationDate.setFromDate(LocalDate.parse(fromDate, DateTimeFormatter.ofPattern(DATE_FORMAT)));
            vacationDate.setToDate(LocalDate.parse(toDate,DateTimeFormatter.ofPattern(DATE_FORMAT)));
            vacationDateList.add(vacationDate);
        }

        return vacationDateList;
    }
}
