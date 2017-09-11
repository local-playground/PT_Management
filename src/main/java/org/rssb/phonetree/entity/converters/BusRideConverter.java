package org.rssb.phonetree.entity.converters;

import org.rssb.phonetree.entity.emums.BusRide;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = false)
public class BusRideConverter implements AttributeConverter<BusRide,String>{

    @Override
    public String convertToDatabaseColumn(BusRide busRide) {
        return busRide.getShortName();
    }

    @Override
    public BusRide convertToEntityAttribute(String busRide) {
        return BusRide.fromShortName(busRide);
    }
}
