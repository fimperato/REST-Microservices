package it.imperato.test.ms.repository.impl;

import com.mongodb.WriteResult;
import it.imperato.test.ms.model.entities.Domain;
import it.imperato.test.ms.repository.custom.DomainRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;

// Impl aggiunto al core repository interface 'DomainRepository'
public class DomainRepositoryImpl implements DomainRepositoryCustom {

    @Autowired
    @Lazy
    MongoTemplate mongoTemplate;

    @Override
    public int customMethod(String someParam) {

        // custom method implementation here

        return 0;
    }

}
