package com.example.project1;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;
import org.springframework.data.repository.CrudRepository;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class MockSpringData {

	/**
     * Assumptions: E is an entity with UUID as primary key, which has methods getId and setId for it.
     */
    public static <E, R extends CrudRepository<E, String>> R mockCrudRepo(final Class<E> entityClass, Class<R> repositoryClass) {
        R mockedRepository = Mockito.mock(repositoryClass);
        final ConcurrentHashMap<String, E> persistenceMap = new ConcurrentHashMap<>();
        doAnswer(invocation -> {
            Object argument = invocation.getArguments()[0];
            String id = (String) entityClass.getMethod("getId").invoke(argument);
            E persistedItem = id == null ? null : persistenceMap.get(id);
            if (persistedItem == null) {
                String uuid = UUID.randomUUID().toString();
                entityClass.getMethod("setId", String.class).invoke(argument, uuid);
                persistenceMap.put(uuid, (E) argument);
                return argument;
            } else {
                BeanUtils.copyProperties(argument, persistedItem);
                return persistedItem;
            }
        }).when(mockedRepository).save(any(entityClass));
 
        doAnswer(invocation -> persistenceMap.get(invocation.getArguments()[0])).when(mockedRepository).findById(anyString());
 
        doAnswer(invocation -> persistenceMap.remove(invocation.getArguments()[0])).when(mockedRepository).deleteById(anyString());
 
        doAnswer(invocation -> persistenceMap.values()).when(mockedRepository).findAll();
 
        doAnswer(invocation -> persistenceMap.remove(entityClass.getMethod("getId").invoke(invocation.getArguments()[0]))).when(mockedRepository).delete(any(entityClass));
 
        doAnswer(invocation -> {
            for (E item : (Iterable<E>) invocation.getArguments()[0]) {
                persistenceMap.remove(entityClass.getMethod("getId").invoke(item));
            }
            return null;
        }).when(mockedRepository).deleteAll(any(Iterable.class));
 
        return mockedRepository;
    }
}
