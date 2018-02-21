package it.imperato.test.ms.controllers;

import it.imperato.test.ms.model.entities.Activity;
import it.imperato.test.ms.model.restbean.ActivityRBean;
import it.imperato.test.ms.model.restbean.ActivityBodyRBean;
import it.imperato.test.ms.services.ActivityService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    ActivityService activityService;

    @RequestMapping("/activityController")
    public String activityControllerTest(){
        return "ActivityController ready.";
    }

    /**
     * http://localhost:8085/api/REST/1.0/data/mock/activities/contact/{contactId}
     *
     * @return
     */
    @RequestMapping(value = "/mock/activities/contact/{contactId}", method = POST)
    public ResponseEntity<List<ActivityRBean>> extractActivitiesMock(
            @RequestBody ActivityBodyRBean reqBody, @PathVariable(name = "contactId", required = false) String contactId){
        try {
            contactId = (contactId==null||"0".equals(contactId))&&(reqBody!=null&&reqBody.getContactId()!=null) ?
                    String.valueOf(reqBody.getContactId()):contactId;
            if(contactId != null) {
                List<ActivityRBean> activities = activityService.getMockActivities(contactId);
                return ResponseEntity.status(HttpStatus.OK).header("header-info", "NO-INFO")
                        .body(activities);
            }
        }
        catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    /**
     * http://localhost:8085/api/REST/1.0/data/activities/contact/{contactId}
     * (Simulazione in POST invece che in GET)
     *
     * @return
     */
    @RequestMapping(value = "/activities/contact/{contactId}", method = POST)
    public ResponseEntity<List<Activity>> extractActivities(
            @RequestBody ActivityBodyRBean reqBody, @PathVariable(name = "contactId", required = false) String contactId){
        try {
            contactId = (contactId==null||"0".equals(contactId))&&(reqBody!=null&&reqBody.getContactId()!=null) ?
                    String.valueOf(reqBody.getContactId()):contactId;
            if(contactId != null) {
                List<Activity> activities = activityService.findByContact(contactId);
                return ResponseEntity.status(HttpStatus.OK).header("header-info", "NO-INFO")
                        .body(activities);
            }
        }
        catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

}
