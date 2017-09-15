package org.rssb.phonetree.services;

import org.junit.Test;
import org.rssb.phonetree.ApplicationSetup;
import org.rssb.phonetree.domain.SearchResult;
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
public class SearchServiceTest extends ApplicationSetup{

    @Autowired
    private SearchService searchService;

    @Test
    public void findFamiliesByFirstName(){
        List<SearchResult> familyList = searchService.findFamiliesByFirstName("Rajes");
        familyList.stream().forEach(family -> {
            System.out.println(family);
        });
    }

    @Test
    public void findFamiliesByLastName(){
        List<SearchResult> familyList = searchService.findFamiliesByLastName("Sharm");
        familyList.stream().forEach(family -> {
            System.out.println(family);
        });
    }

    @Test
    public void findFamiliesByZipCode(){
        List<SearchResult> familyList = searchService.findFamiliesByZipCode("0881");
        familyList.stream().forEach(family -> {
            System.out.println(family);
        });
    }

    @Test
    public void findFamiliesByTown(){
        List<SearchResult> familyList = searchService.findFamiliesByTown("Edis");
        familyList.stream().forEach(family -> {
            System.out.println(family);
        });
    }

    @Test
    public void findFamiliesBySevadarName(){
        List<SearchResult> familyList = searchService.findFamiliesBySevadarName("Geeta");
        familyList.stream().forEach(family -> {
            System.out.println(family);
        });
    }

    @Test
    public void findFamiliesByTeamLeadName(){
        List<SearchResult> familyList = searchService.findFamiliesByTeamLeadName("Jyo");
        familyList.stream().forEach(family -> {
            System.out.println(family);
        });
    }

    @Test
    public void findFamiliesByPhoneNumber(){
        List<SearchResult> familyList = searchService.findFamiliesByPhoneNumber("0145");
        familyList.stream().forEach(family -> {
            System.out.println(family);
        });
    }
}
