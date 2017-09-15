package org.rssb.phonetree.common.data.importer;

import com.opencsv.CSVReader;
import com.opencsv.ICSVParser;
import org.rssb.phonetree.common.CommonUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;

@Service
public class CSVFileReaderImpl implements CSVFileReader{
    @Override
    public List<Map<String, String>> readCsvFile(InputStream inputStream,String fileHeadersList) throws IOException {
        Reader reader = new InputStreamReader(inputStream);
        CSVReader csvReader = null;
        try {
            csvReader = new CSVReader(reader, ICSVParser.DEFAULT_SEPARATOR, ICSVParser.DEFAULT_QUOTE_CHARACTER, 1);
            return processLines(csvReader,fileHeadersList);
        }finally {
            try {
                if(csvReader != null) {
                    csvReader.close();
                }
            } catch (Exception ignored) {
            }
        }
    }

    private List<Map<String, String>> processLines(CSVReader csvReader,String fileHeadersList) throws IOException {
        String parseColumnNamesList = fileHeadersList;
        String parseColumnNumbersList = "";
        String useEmptyLineAsEof = "Y";

        List<String> configuredColumnNames = CommonUtil.convertStringToList(parseColumnNamesList, ",");
        List<Integer> configuredColumnNumbers = CommonUtil.convertStringToIntegerList(parseColumnNumbersList, ",");
        boolean useEmptyLineAsEOF = CommonUtil.isTrue(useEmptyLineAsEof);

        //logData("Column Names in header to parse " + configuredColumnNames);
        //logData("Column numbers to parse " + configuredColumnNumbers);
        List<String> headerNamesList = null;
        List<Map<String, String>> propsList = new ArrayList<>();
        int counter = 1;

        String[] nextRecord;

        while((nextRecord = csvReader.readNext()) !=null){
            if(counter==1){
                //logData("Record to process - ["+CommonUtil.strigyfy(nextRecord, ",")+"]");
                headerNamesList = readHeaderNames(nextRecord, configuredColumnNames,configuredColumnNumbers);
                //logData("Parsed Headers list "+headerNamesList);
                counter++;
                continue;
            }
            //logData("Record to process - ["+CommonUtil.strigyfy(nextRecord, ",")+"]");

            if (nextRecord.length == 0) {
                if (useEmptyLineAsEOF) {
                    //logData("useEmptyLineAsEOF marked as Y - end of line encountered - so stopping.. ");
                    break;
                } else {
                    continue;
                }
            }

            //quick check if line is valid or dummy or disclaimer etc...
            if(headerNamesList.size()!=nextRecord.length){
                //logData("invalid row found , skipping line with value  "+ Arrays.toString(nextRecord));
                continue;
            }

            Map<String, String> prop = processLine(nextRecord, headerNamesList, configuredColumnNumbers);
            propsList.add(prop);
        }



        return propsList;
    }

    private List<String> readHeaderNames(String[] line, List<String> configuredColumnNames,List<Integer> configuredColumnNumbers) {
        return getColumnNamesList(line, configuredColumnNames,configuredColumnNumbers);
    }

    private Map<String, String> processLine(String[] line, List<String> headerNamesList,
                                            List<Integer> configuredColumnNumbers) {
        Map<String, String> prop = new HashMap<>();
        List<String> lineDataList = Arrays.asList(line);
        List<Integer> columnNumbersList = getColumnNumbersList(line, configuredColumnNumbers);
        for (int columnNumber : columnNumbersList) {
            int index = columnNumber - 1;
            String key = headerNamesList.get(index);
            String value = lineDataList.get(index);
            prop.put(key, value);
        }

        return prop;
    }

    private List<String> getColumnNamesList(String[] line, List<String> configuredColumnNames,List<Integer> configuredColumnNumbers) {
        List<String> columnNamesList = Arrays.asList(line);
        if (configuredColumnNames.size() == 0) {
            configuredColumnNumbers.addAll(getNumberOfColumns(line.length));
            return columnNamesList;
        }

        List<String> columnNames = new ArrayList<>();
        for (String columnName : columnNamesList) {
            String useColumnName = match(columnName, configuredColumnNames);
            //logData("Using COlumnName= ["+useColumnName.trim()+"]");
            columnNames.add(useColumnName.trim());
        }
        return columnNames;
    }

    private String match(String columnName, List<String> configuredColumnNames) {
        for (String colName : configuredColumnNames) {
            if (colName.contains(":")) {
                String useColumnName = colName.substring(0, colName.indexOf(":"));
                String originalColumnName = colName.substring(colName.indexOf(":") + 1);
                if (originalColumnName.equals(columnName)) {
                    //logData("Original col "+originalColumnName+" matched, using custom column name = "+useColumnName);
                    return CommonUtil.isNotEmptyOrNull(useColumnName) ? useColumnName : originalColumnName;
                }
            }
        }

        return columnName;
    }

    private List<Integer> getColumnNumbersList(String[] line, List<Integer> configuredColumnNumbers) {
        if (configuredColumnNumbers.size() != 0) {
            return configuredColumnNumbers;
        }

        int length = line.length;
        return getNumberOfColumns(length);
    }

    private List<Integer> getNumberOfColumns(int length) {
        List<Integer> list = new ArrayList<>();
        for (int index = 1; index <= length; index++) {
            list.add(index);
        }
        return list;
    }

    private void logData(String str){
        System.out.println(str);
    }


    /*public static void main(String[] args) {
        CSVFileReader reader = new CSVFileReaderImpl();
        String path = Constants.FAMILY_TABLE_CSV_FILE_NAME;
        FileSystemResource resource = new FileSystemResource(new File(path));
        try {
            List<Map<String,String>> list = reader.readCsvFile(resource.getInputStream(), Constants.FAMILY_DATA_IMPORT_CSV_HEADERS);
            //System.out.println("all data - "+list);
            list.stream().forEach(map -> {
                map.forEach((k, v) -> System.out.println(k + " " + v));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
