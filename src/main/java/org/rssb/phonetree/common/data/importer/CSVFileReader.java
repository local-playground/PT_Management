package org.rssb.phonetree.common.data.importer;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface CSVFileReader {
    List<Map<String,String>> readCsvFile(InputStream inputStream,String fileHeadersList) throws IOException;
}
