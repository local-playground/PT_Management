package org.rssb.phonetree.entity.converters;

import org.rssb.phonetree.entity.emums.CallStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = false)
public class CallStatusConverter implements AttributeConverter<CallStatus,String>{
    @Override
    public String convertToDatabaseColumn(CallStatus callStatus) {
        return callStatus.getDatabaseName();
    }

    @Override
    public CallStatus convertToEntityAttribute(String databaseName) {
        return CallStatus.fromDatabaseName(databaseName);
    }
}
