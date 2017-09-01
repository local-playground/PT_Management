package org.rssb.phonetree.services;

import org.rssb.phonetree.domain.SearchResult;

import java.util.List;

public interface SearchService {
    List<SearchResult> findFamiliesByZipCode(String zipCode);
    List<SearchResult> findFamiliesByFirstName(String firstName);
    List<SearchResult> findFamiliesByLastName(String lastName);
    List<SearchResult> findFamiliesBySevadarName(String sevadarName);
    List<SearchResult> findFamiliesByTeamLeadName(String teamLeadName);
    List<SearchResult> findFamiliesByPhoneNumber(String phoneNumber);
    List<SearchResult> findFamiliesByTown(String town);
}
