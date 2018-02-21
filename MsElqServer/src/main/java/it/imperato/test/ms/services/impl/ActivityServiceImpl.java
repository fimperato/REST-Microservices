package it.imperato.test.ms.services.impl;

import it.imperato.test.ms.model.entities.Activity;
import it.imperato.test.ms.model.restbean.ActivityRBean;
import it.imperato.test.ms.repository.ActivityRepository;
import it.imperato.test.ms.services.ActivityService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Log
@Service("activityService")
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    ActivityRepository activityRepository;

    @Override
    public void create(Activity activity) {
        Activity c = activityRepository.insert(activity);
        log.info("Created:- " + c);
    }

    @Override
    public void update(Activity activity) {
        Activity c = activityRepository.save(activity);
        log.info("Updated:- " + c);

    }

    @Override
    public void delete(Activity activity) {
        activityRepository.delete(activity);
        log.info("Deleted:- " + activity.getId());
    }

    @Override
    public List<Activity> findAll() {
        return activityRepository.findAll();
    }

    @Override
    public List <Activity> findByContact(String contact) {
        return activityRepository.findByContact(contact);
    }

    @Override
    public Activity findByActivityDate(String activityDate) {
        return activityRepository.findByActivityDate(activityDate);
    }

    @Override
    public Activity find(Activity activity) {
        Optional<Activity> oneActivity = activityRepository.findOne(Example.of(activity));
        if(oneActivity.isPresent()) {
            return oneActivity.get();
        }
        return null;
    }

    @Override
    public void deleteAll() {
        activityRepository.deleteAll();
    }

    // Mock service implementation

    public List<ActivityRBean> getMockActivities(String contactId) {
        List<ActivityRBean> activities = new ArrayList<>();
        // 1
        ActivityRBean a = new ActivityRBean();
        Date d = new Date();
        a.setAssetType("Email");
        a.setActivityType("Completed");
        a.setContact(contactId);
        a.setActivityDate(String.valueOf(d.getTime()));
        activities.add(a);
        // 2
        d = new Date();
        a.setAssetType("Landing Page");
        a.setActivityType("Completed");
        a.setContact(contactId);
        a.setActivityDate(String.valueOf(d.getTime()));
        activities.add(a);

        return activities;
    }
}
