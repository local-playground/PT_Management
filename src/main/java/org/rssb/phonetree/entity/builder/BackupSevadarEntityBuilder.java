package org.rssb.phonetree.entity.builder;

import org.rssb.phonetree.common.Constants;
import org.rssb.phonetree.entity.BackupSevadar;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Component
public class BackupSevadarEntityBuilder implements EntityBuilder {
    private List<BackupSevadar> backupSevadarList = new ArrayList<>();

    @Override
    public void build(List<Map<String, String>> data) {

    }

    @Override
    public String getFileHeaders() {
        return Constants.BACKUP_SEVADARS_IMPORT_CSV_HEADERS;
    }

    @Override
    public String getCsvFileName() {
        return Constants.BACKUP_SEVADARS_TABLE_CSV_FILE_NAME;
    }

    @Override
    public <T> List<T> getData() {
        return (List<T>) backupSevadarList;
    }
}
