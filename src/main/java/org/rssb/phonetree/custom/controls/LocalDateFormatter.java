package org.rssb.phonetree.custom.controls;


import javafx.util.StringConverter;
import org.rssb.phonetree.common.CommonUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateFormatter extends StringConverter<LocalDate>{
    private static final String pattern = "yyyy-MM-dd";
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

    @Override
    public String toString(LocalDate date) {
        if (date != null) {
            return dateFormatter.format(date);
        } else {
            return "";
        }
    }

    @Override
    public LocalDate fromString(String value) {
        if (CommonUtil.isNotEmptyOrNull(value)) {
            return LocalDate.parse(value, dateFormatter);
        } else {
            return null;
        }
    }

}
