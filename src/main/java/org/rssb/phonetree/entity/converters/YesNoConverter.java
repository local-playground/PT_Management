package org.rssb.phonetree.entity.converters;

import org.rssb.phonetree.entity.emums.YesNo;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = false)
public class YesNoConverter implements AttributeConverter<YesNo,String>{

    @Override
    public String convertToDatabaseColumn(YesNo yesNo) {
        return yesNo.getDatabaseName();
    }

    @Override
    public YesNo convertToEntityAttribute(String databaseName) {
        return YesNo.fromDatabaseName(databaseName);
    }
}
