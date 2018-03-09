package it.imperato.test.ms.repository.impl;

import it.imperato.test.ms.model.entities.Activity;
import it.imperato.test.ms.repository.custom.ActivityRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.List;

// Impl aggiunto al core repository interface 'ActivityRepository'
public class ActivityRepositoryImpl implements ActivityRepositoryCustom {

    @Autowired
    @Lazy
    MongoTemplate mongoTemplate;

    @Override
    public List<Activity> customGetSomeActivity(String assetType) {
        List<Activity> activities = new ArrayList<>();

        // custom method implementation here

        return activities;
    }

}
