package org.rssb.phonetree.common.data.importer;

import org.rssb.phonetree.common.Constants;
import org.rssb.phonetree.common.PhoneTreeTableDesign;
import org.rssb.phonetree.entity.Family;
import org.rssb.phonetree.entity.Member;
import org.rssb.phonetree.entity.Sevadar;
import org.rssb.phonetree.entity.TeamLead;
import org.rssb.phonetree.entity.builder.*;
import org.rssb.phonetree.repository.SevadarJpaRepository;
import org.rssb.phonetree.repository.TeamLeadJpaRepository;
import org.rssb.phonetree.services.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DataImportBuilder {
    @Autowired
    private List<EntityBuilder> entityBuilderList;

    @Autowired
    private CSVFileReader csvFileReader;

    @Autowired
    private FamilyService familyService;

    @Autowired
    private TeamLeadJpaRepository teamLeadJpaRepository;

    @Autowired
    private SevadarJpaRepository sevadarJpaRepository;

    public void run() {
        //create table strucutre
        Arrays.asList(PhoneTreeTableDesign.TABLE_CREATE_DDL_STATEMENTS)
                .stream()
                .forEach(ddl -> {
                    DBConnector.executeQuery(ddl, Constants.SQLITE_DB_FILE_NAME);
                });


        //load all the data
        entityBuilderList.stream().forEach(entityBuilder -> {
            String csvFileName = entityBuilder.getCsvFileName();
            String csvHeadersList = entityBuilder.getFileHeaders();
            FileSystemResource resource = new FileSystemResource(new File(csvFileName));
            try {
                List<Map<String, String>> list = csvFileReader.readCsvFile(resource.getInputStream(), csvHeadersList);
                entityBuilder.build(list);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //build Team Lead Table
        List<TeamLead> teamLeadList = getEntityData(TeamLeadEntityBuilder.class);
        teamLeadJpaRepository.save(teamLeadList);

        //build Sevadar Table
        List<Sevadar> sevadarList = getEntityData(SevadarEntityBuilder.class);
        sevadarJpaRepository.save(sevadarList);

        //build family and member relationship
        List<Family> familyList = getEntityData(FamilyEntityBuilder.class);
        List<Member> memberList = getEntityData(MemberEntityBuilder.class);

        //System.out.println("rcvd. family size " + familyList.size());
        //System.out.println("rcvd. member size " + memberList.size());

        familyList.stream().forEach(family -> {
            List<Member> members = matchMembersForFamilyId(family, memberList);
            family.setMembersList(members);
        });

        familyList.stream().forEach(family -> familyService.saveToDatabase(family));
    }

    private <T> List<T> getEntityData(Class<? extends EntityBuilder> type) {
        return entityBuilderList.stream()
                .filter(entityBuilder -> entityBuilder.getClass().isAssignableFrom(type))
                .findFirst().get().getData();
    }

    private List<Member> matchMembersForFamilyId(Family family, List<Member> memberList) {
        return memberList
                .stream()
                .filter(member1 -> {
                    if(member1.getFamily().getFamilyId() == family.getFamilyId()){
                        member1.setFamily(family);
                        return true;
                    }else{
                        return false;
                    }

                })
                .collect(Collectors.toList());
    }
}