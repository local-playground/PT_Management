package org.rssb.phonetree.entity.builder;


import java.util.List;
import java.util.Map;

public interface EntityBuilder {
    void build(List<Map<String,String>> data);
    String getFileHeaders();
    String getCsvFileName();
    <T> List<T> getData();
}
