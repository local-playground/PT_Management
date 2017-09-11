package org.rssb.phonetree.entity.converters;

import org.rssb.phonetree.entity.emums.PreferredPhoneType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = false)
public class PreferredPhoneTypeConverter implements AttributeConverter<PreferredPhoneType,String>{
    @Override
    public String convertToDatabaseColumn(PreferredPhoneType preferredPhoneType) {
        return preferredPhoneType.getDatabaseName();
    }

    @Override
    public PreferredPhoneType convertToEntityAttribute(String databaseName) {
        return PreferredPhoneType.fromDatabaseName(databaseName);
    }
}
