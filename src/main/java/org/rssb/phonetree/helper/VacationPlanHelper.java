package org.rssb.phonetree.helper;

import org.rssb.phonetree.common.CommonUtil;
import org.rssb.phonetree.domain.SevadarsMonthlyAvailability;
import org.rssb.phonetree.domain.VacationDate;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class VacationPlanHelper {
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public static String convertToDatabaseColumn(List<VacationDate> vacationDateList) {
        if (CommonUtil.isCollectionEmpty(vacationDateList))
            return "";

        return vacationDateList
                .stream()
                .map(VacationDate::toString)
                .collect(Collectors.joining(","));
    }

    public static List<VacationDate> convertToEntityAttribute(String s) {
        if (CommonUtil.isEmptyOrNull(s)) {
            return new ArrayList<VacationDate>();
        }
        List<VacationDate> vacationDateList = new ArrayList<>();
        String[] vacationDatesArray = s.split(",");
        for (String offDate : vacationDatesArray) {
            String[] fromToDates = offDate.split(":");
            String fromDate = fromToDates[0];
            String toDate = fromToDates[1];

            VacationDate vacationDate = new VacationDate();
            vacationDate.setFromDate(LocalDate.parse(fromDate, DateTimeFormatter.ofPattern(DATE_FORMAT)));
            vacationDate.setToDate(LocalDate.parse(toDate, DateTimeFormatter.ofPattern(DATE_FORMAT)));
            vacationDateList.add(vacationDate);
        }

        return vacationDateList;
    }

    public static Set<String> convertVacatationDatesByDays(List<VacationDate> vacationDateList) {
        Set<String> vacationDatesSet = new TreeSet<>();
        if (vacationDateList.isEmpty()) {
            return vacationDatesSet;
        }

        for (VacationDate vacationDate : vacationDateList) {
            LocalDate fromDate = vacationDate.getFromDate();
            LocalDate endDate = vacationDate.getToDate();
            long daysOff = ChronoUnit.DAYS.between(vacationDate.getFromDate(), vacationDate.getToDate());
            for (int index = 0; index <= daysOff; index++) {
                vacationDatesSet.add(fromDate.plusDays(index).format(DateTimeFormatter.ofPattern(DATE_FORMAT)));
            }
        }
        return vacationDatesSet;
    }

    public static List<SevadarsMonthlyAvailability> getSevadarAvailabilityDetails(String sevadarName,
                                                                                  List<VacationDate> vacationDateList,
                                                                                  LocalDate start,LocalDate end) {
        List<SevadarsMonthlyAvailability> sevadarsMonthlyAvailabilityList = new ArrayList<>();
        int startMonth = start.getMonth().getValue();
        int endMonth = end.getMonth().getValue();
        if (CommonUtil.isCollectionEmpty(vacationDateList)) {
            while(true){
                Month month = Month.of(start.getMonth().getValue());
                sevadarsMonthlyAvailabilityList.add(getAvailabilityDetails(month.name(), sevadarName, null, null, null));
                if(start.getMonth().getValue()==end.getMonth().getValue()){
                    break;
                }
                start = start.plusMonths(1);
            }
            return sevadarsMonthlyAvailabilityList;
        }

        Set<String> vacationSetByDays = convertVacatationDatesByDays(vacationDateList);
        while(true) {
            Month month = Month.of(start.getMonth().getValue());
            StringBuilder inDetails = new StringBuilder();
            StringBuilder outDetails = new StringBuilder();
            StringBuilder daysOut = new StringBuilder();
            List<String> inList = new ArrayList<>();
            List<String> outList = new ArrayList<>();
            LocalDate localDate = LocalDate.of(2017, month, 1);
            boolean isPreviousDayOut = false;
            for (int daysInMonth = 0; daysInMonth < month.maxLength(); daysInMonth++) {
                String nextDate = localDate.plusDays(daysInMonth).format(DateTimeFormatter.ofPattern(DATE_FORMAT));
                if (vacationSetByDays.contains(nextDate)) {
                    if (!isPreviousDayOut) {
                        strigifyDates(inDetails, inList, null);
                    }
                    outList.add(nextDate);
                    isPreviousDayOut = true;
                } else {
                    if (isPreviousDayOut) {
                        strigifyDates(outDetails, outList, daysOut);
                    }
                    inList.add(nextDate);
                    isPreviousDayOut = false;
                }
            }

            strigifyDates(inDetails, inList, null);
            strigifyDates(outDetails, outList, daysOut);

            sevadarsMonthlyAvailabilityList.add(
                    getAvailabilityDetails(month.name(), sevadarName, inDetails.toString(), outDetails.toString(), daysOut.toString())
            );
            if(start.getMonth().getValue()==end.getMonth().getValue()){
                break;
            }
            start = start.plusMonths(1);
        }
        return sevadarsMonthlyAvailabilityList;
    }

    private static void strigifyDates(StringBuilder sb, List<String> list, StringBuilder daysOut) {
        if (CommonUtil.isCollectionEmpty(list)) {
            return;
        }

        if (sb.length() != 0) {
            sb.append(",");
        }
        if (list.size() == 1) {
            sb.append(String.join("-", list));
            if (daysOut != null) {
                countDaysOut(daysOut, 1);
            }
        } else {
            String fromDate = list.get(0);
            String toDate = list.get(list.size() - 1);
            if (daysOut != null) {
                long days = ChronoUnit.DAYS.between(LocalDate.parse(fromDate), LocalDate.parse(toDate));
                countDaysOut(daysOut, days + 1);
            }
            fromDate = LocalDate.parse(fromDate).format(DateTimeFormatter.ofPattern("MM/dd"));
            toDate = LocalDate.parse(toDate).format(DateTimeFormatter.ofPattern("MM/dd"));
            sb.append(String.join(" - ", fromDate, toDate));
        }
        list.clear();
    }


    private static void countDaysOut(StringBuilder daysOut, long days) {
        long outTime = CommonUtil.convertStringToInt(daysOut.toString(), 0);
        daysOut.setLength(0);
        daysOut.append(outTime + days);
    }

    private static SevadarsMonthlyAvailability getAvailabilityDetails(String monthName, String sevadarName,
                                                                      String inDetails, String outDetails,
                                                                      String daysOut) {
        SevadarsMonthlyAvailability sevadarsMonthlyAvailability = new SevadarsMonthlyAvailability();
        if (CommonUtil.isEmptyOrNull(inDetails)) {
            inDetails = "YES";
        }
        if (CommonUtil.isEmptyOrNull(outDetails)) {
            outDetails = "NO";
        }
        if (CommonUtil.isEmptyOrNull(daysOut)) {
            daysOut = "0";
        }
        sevadarsMonthlyAvailability.setMonthName(monthName);
        sevadarsMonthlyAvailability.setSevadarName(sevadarName);
        sevadarsMonthlyAvailability.setAvailableDates(inDetails);
        sevadarsMonthlyAvailability.setOutDates(outDetails);
        sevadarsMonthlyAvailability.setTotalDaysOut(CommonUtil.convertStringToInt(daysOut, 0));

        return sevadarsMonthlyAvailability;
    }
}
