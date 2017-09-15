package org.rssb.phonetree.entity.converters;

import org.rssb.phonetree.common.CommonUtil;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = false)
public class PhoneNumberConverter implements AttributeConverter<String, String> {
    @Override
    public String convertToDatabaseColumn(String value) {
        String newValue = value.replaceAll("[A-Za-z(\\s)-]", "");
        if (newValue.length() > 10 && newValue.length() < 21) {
            newValue = newValue.substring(0, 10) + "," + newValue.substring(10);
        }
        return newValue;
    }

    @Override
    public String convertToEntityAttribute(String value) {
        if(CommonUtil.isEmptyOrNull(value) || value.trim().length()<10)
            return "";

        if(value.length()==10){
            return format(value);
        }

        if(value.length()>10){
            String data = format(value.substring(0,10));
            if(value.indexOf(',')!=-1){
                data+=","+format(value.substring(value.indexOf(',')+1));
            }

            return data;
        }

        return value;//not sure what format it is, return as is
    }

    private String format(String value){
        return String.format("(%s) %s-%s",value.substring(0,3),value.substring(3,6),value.substring(6,10));
    }
}
