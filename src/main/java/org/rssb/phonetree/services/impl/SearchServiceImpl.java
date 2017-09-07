package org.rssb.phonetree.services.impl;


import org.rssb.phonetree.domain.SearchResult;
import org.rssb.phonetree.repository.NamedQueryExecutor;
import org.rssb.phonetree.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService{

    @Autowired
    private NamedQueryExecutor namedQueryExecutor;
   /* @PersistenceContext
    private EntityManager entityManager;*/

    @Override
    public List<SearchResult> findFamiliesByZipCode(String zipCode) {
        return namedQueryExecutor.executeNamedQuery("Family.findFamilyByZipCode","zipCode","%"+zipCode+"%",SearchResult.class);
    }

    @Override
    public List<SearchResult> findFamiliesByFirstName(String firstName) {
        return namedQueryExecutor.executeNamedQuery("Family.findFamilyByFirstName","firstName","%"+firstName+"%",SearchResult.class);
    }

    @Override
    public List<SearchResult> findFamiliesByLastName(String lastName) {
        return namedQueryExecutor.executeNamedQuery("Family.findFamilyByLastName","lastName","%"+lastName+"%",SearchResult.class);
    }

    @Override
    public List<SearchResult> findFamiliesBySevadarName(String sevadarName) {
        return namedQueryExecutor.executeNamedQuery("Family.findFamilyBySevadarName","sevadarName","%"+sevadarName+"%",SearchResult.class);
    }

    @Override
    public List<SearchResult> findFamiliesByTeamLeadName(String teamLeadName) {
        return namedQueryExecutor.executeNamedQuery("Family.findFamilyByTeamLeadName","teamLeadName","%"+teamLeadName+"%",SearchResult.class);
    }

    @Override
    public List<SearchResult> findFamiliesByPhoneNumber(String phoneNumber) {
        return namedQueryExecutor.executeNamedQuery("Family.findFamilyByPhoneNumber","phoneNumber","%"+phoneNumber+"%",SearchResult.class);
    }

    @Override
    public List<SearchResult> findFamiliesByTown(String town) {
        return namedQueryExecutor.executeNamedQuery("Family.findFamilyByTown","town","%"+town+"%",SearchResult.class);
    }

}
