package org.rssb.phonetree.entity.converters;

import org.rssb.phonetree.common.CommonUtil;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Converter(autoApply = false)
public class PhoneNumberConverter implements AttributeConverter<String, String> {
    private Pattern phoneNumberPattern = Pattern.compile("(\\d{3})(\\d{3})(\\d{4})");
    private Pattern tollPhoneNumberPattern = Pattern.compile("(\\d{1})(\\d{3})(\\d{3})(\\d{4})");

    @Override
    public String convertToDatabaseColumn(String value) {
        String newValue = value.replaceAll("[A-Za-z(\\s)-]", "");
        return newValue;
    }

    @Override
    public String convertToEntityAttribute(String value) {
        if(CommonUtil.isEmptyOrNull(value) || value.trim().length()<10)
            return "";

        StringBuilder sb = new StringBuilder();
        String[] splitWithCommas = value.split(",");
        for(String phone: splitWithCommas){
            Matcher matcher = tollPhoneNumberPattern.matcher(phone);
            if (matcher.matches()) {
                sb.append(matcher.group(1) + " " + "(" + matcher.group(2) + ")-" + matcher.group(3) + "-" + matcher.group(4)).append(",");
                continue;
            }
            matcher = phoneNumberPattern.matcher(phone);
            if (matcher.matches()) {
                sb.append("(" + matcher.group(1) + ")-" + matcher.group(2) + "-" + matcher.group(3)).append(",");
            }
        }

        if (CommonUtil.isEmptyOrNull(sb.toString())) {
            return "";
        }

        return sb.substring(0, sb.length() - 1);

    }

}
