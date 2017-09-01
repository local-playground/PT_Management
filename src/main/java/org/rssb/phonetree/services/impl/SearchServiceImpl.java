package org.rssb.phonetree.services.impl;


import org.rssb.phonetree.domain.SearchResult;
import org.rssb.phonetree.services.SearchService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<SearchResult> findFamiliesByZipCode(String zipCode) {
        return getFamilyInformation("Family.findFamilyByZipCode","zipCode",zipCode);
    }

    @Override
    public List<SearchResult> findFamiliesByFirstName(String firstName) {
        TypedQuery<SearchResult> typedQuery = entityManager.createNamedQuery("Family.findFamilyByFirstName",SearchResult.class);
        typedQuery.setParameter("firstName","%"+firstName+"%");
        return typedQuery.getResultList();
    }

    @Override
    public List<SearchResult> findFamiliesByLastName(String lastName) {
        return getFamilyInformation("Family.findFamilyByLastName","lastName",lastName);
    }

    @Override
    public List<SearchResult> findFamiliesBySevadarName(String sevadarName) {
        return getFamilyInformation("Family.findFamilyBySevadarName","sevadarName",sevadarName);
    }

    @Override
    public List<SearchResult> findFamiliesByTeamLeadName(String teamLeadName) {
        return getFamilyInformation("Family.findFamilyByTeamLeadName","teamLeadName",teamLeadName);
    }

    @Override
    public List<SearchResult> findFamiliesByPhoneNumber(String phoneNumber) {
        return getFamilyInformation("Family.findFamilyByPhoneNumber","phoneNumber",phoneNumber);
    }

    @Override
    public List<SearchResult> findFamiliesByTown(String town) {
        return getFamilyInformation("Family.findFamilyByTown","town",town);
    }

    private List<SearchResult> getFamilyInformation(String namedQuery,String parameterName,String parameterValue){
        TypedQuery<SearchResult> typedQuery = entityManager.createNamedQuery(namedQuery,SearchResult.class);
        typedQuery.setParameter(parameterName,"%"+parameterValue+"%");
        return typedQuery.getResultList();
    }
}
