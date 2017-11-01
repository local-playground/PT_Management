package org.rssb.phonetree.services;

import org.junit.Test;
import org.rssb.phonetree.ApplicationSetup;
import org.rssb.phonetree.common.Response;
import org.rssb.phonetree.common.file.DocumentWriter;
import org.rssb.phonetree.common.file.DocumentWriterFactory;
import org.rssb.phonetree.common.file.ReportFormat;
import org.rssb.phonetree.common.file.ReportName;
import org.rssb.phonetree.common.file.ReportType;
import org.rssb.phonetree.domain.CalledFamilyDetails;
import org.rssb.phonetree.domain.SevadarPhoneTreeList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = "org.rssb.phonetree")
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class FamilyServiceTest extends ApplicationSetup{

    @Autowired
    private FamilyService familyService;

    @Autowired
    private DocumentWriterFactory documentWriterFactory;

    @Test
    public void moveMemberAsSeparateFamily(){
        Response response = familyService.moveMemberAsSeparateFamily(513);
        System.out.println("Response = "+ (response!=null?response.getMessage():"Null Response"));
    }

    @Test
    public void deleteFamily(){
        Response response = familyService.deleteFamily(327);
        System.out.println("Response = "+ (response!=null?response.getMessage():"Null Response"));
    }

    @Test
    public void deleteFamilyMember(){
        Response response = familyService.deleteFamilyMember(499);
        System.out.println("Response = "+ (response!=null?response.getMessage():"Null Response"));
    }

    @Test
    public void updateSevadarAndTeamLeadIdForFamilyId(){
        familyService.updateSevadarAndTeamLeadIdForFamilyId(5,21,556);
    }

    @Test
    public void moveMemberUnderOtherFamily(){
        familyService.moveMemberUnderOtherFamily(513,332);
    }

    @Test
    public void findFamiliesByTeamLeadAndSevadarName(){
        List<CalledFamilyDetails> familyList =  familyService.getFamiliesByTeamLeadAndSevadarName("Mina Patel","Kamal Singh");
        familyList.stream().forEach(family -> System.out.println(family));
    }

    @Test
    public void findFamiliesCountByTeamLeadAndSevadar(){
        long count= familyService.getTotalFamiliesByTeamLeadAndSevadar("Mina Patel","Kamal Singh");
        System.out.println("total count = "+count);
    }

    @Test
    public void getSevadarPhoneTreeListByTeamLeadAndSevadarName(){
        SevadarPhoneTreeList sevadarPhoneTreeList =
                familyService.getSevadarPhoneTreeListByTeamLeadAndSevadarName("Mina Patel","Kamal Singh");
        System.out.println("data \n"+sevadarPhoneTreeList);
    }

    @Test
    public void createWordDocumentForSevadar(){
        SevadarPhoneTreeList sevadarPhoneTreeList =
                familyService.getSevadarPhoneTreeListByTeamLeadAndSevadarName("Mina Patel","Kamal Singh");
        System.out.println("data \n"+sevadarPhoneTreeList);
        ReportType reportType = new ReportType();
        reportType.setReportFormat(ReportFormat.WORD);
        reportType.setReportName(ReportName.NORMAL_REPORT);

        DocumentWriter documentWriter = documentWriterFactory.getDocumentWriter(reportType).get();
        documentWriter.writeToFile(sevadarPhoneTreeList);
    }

}
