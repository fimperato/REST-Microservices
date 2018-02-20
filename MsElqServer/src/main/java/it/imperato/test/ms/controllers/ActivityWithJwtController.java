package it.imperato.test.ms.controllers;

import it.imperato.test.ms.exceptions.UserNotLoggedException;
import it.imperato.test.ms.model.restbean.Activity;
import it.imperato.test.ms.model.restbean.ActivityRequestBody;
import it.imperato.test.ms.services.JwtAuthService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Log
@RestController
@RequestMapping("/api/JWT/REST/1.0/data")
public class ActivityWithJwtController {

    @Autowired
    JwtAuthService jwtAuthService;

    @RequestMapping("/activityController")
    public String activityControllerTest(){
        return "ActivityController (with JWT) ready.";
    }

    /**
     * http://localhost:8085/api/JWT/REST/1.0/data/activities/contact/{contactId}
     *
     * @return
     */
    @RequestMapping(value = "/activities/contact/{contactId}", method = POST)
    public ResponseEntity<List<Activity>> extractActivities(
            @RequestHeader HttpHeaders headers,
            @RequestBody ActivityRequestBody reqBody,
            @PathVariable(name = "contactId", required = false) String contactId){
        try {
            jwtAuthService.checkJwt(headers);
            contactId = (contactId==null||"0".equals(contactId))&&(reqBody!=null&&reqBody.getContactId()!=null) ?
                    String.valueOf(reqBody.getContactId()):contactId;
            if(contactId != null) {
                List<Activity> activities = this.getMockActivities(contactId);
                return ResponseEntity.status(HttpStatus.OK).header("header-info", "NO-INFO")
                        .body(activities);
            }
        }
        catch(UserNotLoggedException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    protected List<Activity> getMockActivities(String contactId) {
        List<Activity> activities = new ArrayList<>();

        Activity a = new Activity();
        Date d = new Date();
        a.setAssetType("Email");
        a.setActivityType("Completed");
        a.setContact(contactId);
        a.setActivityDate(String.valueOf(d.getTime()));
        activities.add(a);

        d = new Date();
        a.setAssetType("Landing Page");
        a.setActivityType("Completed");
        a.setContact(contactId);
        a.setActivityDate(String.valueOf(d.getTime()));
        activities.add(a);

        return activities;
    }
}
