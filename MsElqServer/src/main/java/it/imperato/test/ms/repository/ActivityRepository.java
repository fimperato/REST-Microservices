package it.imperato.test.ms.repository;

import it.imperato.test.ms.model.entities.Activity;
import it.imperato.test.ms.model.entities.Domain;
import it.imperato.test.ms.repository.custom.ActivityRepositoryCustom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends MongoRepository<Activity, String>, ActivityRepositoryCustom {

    Activity findFirstByContact(String contact);

    @Query("{ 'activityDate' : ?0 }")
    Activity findByActivityDate(String activityDate);

    @Query(value = "{ 'contact' : ?0}")
    List<Activity> findByContact(String contact);

}
