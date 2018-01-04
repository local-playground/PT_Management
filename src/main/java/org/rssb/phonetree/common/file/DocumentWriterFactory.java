package org.rssb.phonetree.common.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentWriterFactory {

    @Autowired
    private List<DocumentWriter> documentWriterList;

    public  Optional<DocumentWriter> getDocumentWriter(ReportType reportType){
        for(DocumentWriter documentWriter:documentWriterList){
            if(documentWriter.supportsReportFormat()==reportType.getReportFormat() &&
                    documentWriter.supportsReportName() == reportType.getReportName()){
                documentWriter.addColumnsToDocument(reportType.getDocumentTableColumnList());
                return Optional.of(documentWriter);
            }
        }

        return Optional.empty();
    }


}
