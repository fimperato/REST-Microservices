package it.imperato.test.ms.services;

import it.imperato.test.ms.model.entities.Activity;
import it.imperato.test.ms.model.restbean.ActivityRBean;

import java.util.List;

public interface ActivityService {

    void create(Activity activity);

    void update(Activity activity);

    void delete(Activity activity);

    void deleteAll();

    Activity find(Activity activity);

    List<Activity> findByContact(String contact);

    Activity findByActivityDate(String activityDate);

    List <Activity> findAll();

    // Mock method

    List<ActivityRBean> getMockActivities(String contactId);

}
