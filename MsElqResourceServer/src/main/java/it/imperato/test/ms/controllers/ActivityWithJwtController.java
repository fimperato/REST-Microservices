package it.imperato.test.ms.controllers;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import it.imperato.test.ms.exceptions.UserNotLoggedException;
import it.imperato.test.ms.model.entities.Activity;
import it.imperato.test.ms.model.restbean.ActivityRBean;
import it.imperato.test.ms.model.restbean.ActivityBodyRBean;
import it.imperato.test.ms.services.ActivityService;
import it.imperato.test.ms.services.JwtAuthService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
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

    @Autowired
    ActivityService activityService;

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
            @RequestBody ActivityBodyRBean reqBody,
            @PathVariable(name = "contactId", required = false) String contactId){
        try {
            jwtAuthService.checkJwt(headers);
            contactId = (contactId==null||"0".equals(contactId))&&(reqBody!=null&&reqBody.getContactId()!=null) ?
                    String.valueOf(reqBody.getContactId()):contactId;
            if(contactId != null) {
                List<Activity> activities = activityService.findByContact(contactId);
                return ResponseEntity.status(HttpStatus.OK).header("header-info", "NO-INFO")
                        .body(activities);
            }
        }
        catch(UserNotLoggedException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        catch(UnsupportedEncodingException | MalformedJwtException | ExpiredJwtException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

}
