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

    public <T> List<T> executeNamedQuery(String namedQuery,String[] parameterNames,
                                         String[] parameterValues,Class<T> returnType){
        TypedQuery<T> typedQuery = entityManager.createNamedQuery(namedQuery,returnType);
        for (int index = 0; index < parameterNames.length; index++) {
            String parameterName = parameterNames[index];
            if(CommonUtil.isNotEmptyOrNull(parameterName)) {
                typedQuery.setParameter(parameterName, parameterValues[index]);
            }
        }

        return typedQuery.getResultList();
    }

    public <T> T executeSingleResultQuery(String namedQuery,String[] parameterNames,
                                          String[] parameterValues,Class<T> returnType) {
        TypedQuery<T> typedQuery = entityManager.createNamedQuery(namedQuery, returnType);
        for (int index = 0; index < parameterNames.length; index++) {
            String parameterName = parameterNames[index];
            if (CommonUtil.isNotEmptyOrNull(parameterName)) {
                typedQuery.setParameter(parameterName, parameterValues[index]);
            }
        }

        return typedQuery.getSingleResult();
    }

    public <T> T executeSingleResultQuery(String namedQuery, String parameterName,
                                         String parameterValue, Class<T> returnType){
        TypedQuery<T> typedQuery = entityManager.createNamedQuery(namedQuery,returnType);
        if(CommonUtil.isNotEmptyOrNull(parameterValue)) {
            typedQuery.setParameter(parameterName, parameterValue);
        }
        return typedQuery.getSingleResult();
    }

}
