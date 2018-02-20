package it.imperato.test.ms.controllers;

import it.imperato.test.ms.model.restbean.Activity;
import it.imperato.test.ms.model.restbean.Contact;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Log
@RestController
@RequestMapping("/api/REST/1.0/data")
public class ActivityController {

    @RequestMapping("/activityController")
    public String userManagementTest(){
        return "ActivityController ready.";
    }

    /**
     * localhost:8085/
     *
     * @return
     */
    @RequestMapping(value = "/activities/contact/{contactId}", method = POST)
    public ResponseEntity<List<Activity>> testCompany(
            @RequestBody Contact contactBody, @PathVariable(name = "contactId") String contactId){
        try {
            if(contactId != null) {
                List<Activity> activities = this.getMockActivities(contactId);
                return ResponseEntity.status(HttpStatus.OK).header("header-info", "NO-INFO")
                        .body(activities);
            }
        }
        catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    protected List<Activity> getMockActivities(String contactId) {
        List<Activity> activities = new ArrayList<>();

        Activity a = new Activity();
        Date d = new Date();
        a.setAssetType("Activity");
        a.setContact(contactId);
        a.setActivityType("Email");
        a.setActivityDate(String.valueOf(d.getTime()));
        activities.add(a);

        d = new Date();
        a.setActivityType("Landing Page");
        a.setContact(contactId);
        a.setActivityDate(String.valueOf(d.getTime()));
        activities.add(a);

        return activities;
    }
}
