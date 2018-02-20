package it.imperato.test.ms.controllers;

import it.imperato.test.ms.model.restbean.Activity;
import it.imperato.test.ms.model.restbean.ActivityRequestBody;
import it.imperato.test.ms.model.restbean.Contact;
import it.imperato.test.ms.utils.ConstantsApp;
import lombok.extern.java.Log;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Log
@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/activityController")
    public String userManagementTest(){
        return "ActivityController (Client) ready.";
    }

    /**
     * Documentation - esempio di POST:
     * https://docs.oracle.com/cloud/latest/marketingcs_gs/OMCAC/op-api-REST-2.0-data-activity-post.html#examples
     * (Simulazione su retrieve invece che su create)
     *
     * http://localhost:8086/activity/getActivities/12
     *
     * @return
     */
    @RequestMapping(value = "/getActivities/{contactId}", method = POST)
    public ResponseEntity<List<Activity>> getActivities(
            @PathVariable(name = "contactId") String contactId){
        HttpHeaders reqHeaders = new HttpHeaders();

        //reqHeaders.add("Authorization", authorization);
        //reqHeaders.add("JWT", jwt);

        reqHeaders.setContentType(MediaType.APPLICATION_JSON);
        reqHeaders.setAccept(Collections.singletonList(new MediaType("application", "json")));

        // create the request body
        String activityType="Completed", name="", assetType="Email";
        ActivityRequestBody reqBody = new ActivityRequestBody();
        reqBody.setActivityType(activityType);
        reqBody.setAssetType(assetType);
        int cId = contactId!=null&&!contactId.isEmpty()?Integer.valueOf(contactId):0;
        reqBody.setContactId(cId);
        reqBody.setName(name);

        HttpEntity<ActivityRequestBody> entity = new HttpEntity<ActivityRequestBody>(reqBody, reqHeaders);
        try {
            ResponseEntity<Activity[]> response =
                    restTemplate.exchange(ConstantsApp.BASE_URL + ConstantsApp.ACTIVITIES_DATA_URL + "0",
                            HttpMethod.POST, entity, Activity[].class);

            List<Activity> acts = Arrays.asList(response.getBody());
            return ResponseEntity.status(HttpStatus.OK).body(acts);
        } catch(Exception ex) {
            log.log(java.util.logging.Level.SEVERE, "ERRORE: "+ex.getMessage(), ex);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
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
