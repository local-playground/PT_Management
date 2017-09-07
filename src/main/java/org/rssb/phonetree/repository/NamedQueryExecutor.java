package org.rssb.phonetree.repository;

import org.rssb.phonetree.common.CommonUtil;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class NamedQueryExecutor {

    @PersistenceContext
    private EntityManager entityManager;

    public <T> List<T> executeNamedQuery(String namedQuery, String parameterName,
                                         String parameterValue, Class<T> returnType){
        TypedQuery<T> typedQuery = entityManager.createNamedQuery(namedQuery,returnType);
        if(CommonUtil.isNotEmptyOrNull(parameterValue)) {
            typedQuery.setParameter(parameterName, parameterValue);
        }
        return typedQuery.getResultList();
    }

    public <T> List<T> executeNamedQuery(String namedQuery, String parameterName,
                                         int parameterValue, Class<T> returnType){
        TypedQuery<T> typedQuery = entityManager.createNamedQuery(namedQuery,returnType);
        if(CommonUtil.isNotEmptyOrNull(parameterName)) {
            typedQuery.setParameter(parameterName, parameterValue);
        }
        return typedQuery.getResultList();
    }

}
