package it.imperato.test.ms.controllers;

import it.imperato.test.ms.model.pojo.User;
import it.imperato.test.ms.model.pojo.UserJwtInfoResponse;
import it.imperato.test.ms.model.restbean.Activity;
import it.imperato.test.ms.model.restbean.ActivityRequestBody;
import it.imperato.test.ms.services.MyAuthService;
import it.imperato.test.ms.utils.ConstantsApp;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Getting Activities with JWT Authentication required
 *
 */
@Log
@RestController
@RequestMapping("/jwt")
public class JwtActivityController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    MyAuthService myAuthService;

    @RequestMapping("/jwtActivityController")
    public String jwtActivityController(){
        return "JwtActivityController (Client) ready.";
    }

    /**
     *
     * http://localhost:8086/JWT/activity/getActivities/12
     *
     * @return
     */
    @RequestMapping(value = "/JWT/getActivities/{contactId}", method = POST)
    public ResponseEntity<List<Activity>> getActivities(
            @PathVariable(name = "contactId") String contactId,
            @RequestHeader HttpHeaders httpHeaders){
        HttpHeaders reqHeaders = new HttpHeaders();

        //reqHeaders.add("Authorization", authorization);
        reqHeaders.add("jwt", httpHeaders.get("jwt")!=null?(String)httpHeaders.get("jwt").get(0):null);

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
            String dataUrl = ConstantsApp.JWT_AUTH_TYPE_URL + ConstantsApp.ACTIVITIES_DATA_URL;
            ResponseEntity<Activity[]> response =
                    restTemplate.exchange(ConstantsApp.RESOURCE_SERVER_BASE_URL + dataUrl + String.valueOf(cId),
                            HttpMethod.POST, entity, Activity[].class);

            List<Activity> acts = Arrays.asList(response.getBody());
            return ResponseEntity.status(HttpStatus.OK).body(acts);
        } catch(Exception ex) {
            log.log(java.util.logging.Level.SEVERE, "ERRORE: "+ex.getMessage(), ex);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     *
     * http://localhost:8087/jwt/loginUserMock
     *
     * @return
     */
    @RequestMapping(value = "/getAndSaveJwtInfo", method = POST)
    public ResponseEntity<UserJwtInfoResponse> getAndSaveJwtInfo(
            @RequestHeader HttpHeaders httpHeaders,
            @RequestBody User requestBody){
        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.setContentType(MediaType.APPLICATION_JSON);
        reqHeaders.setAccept(Collections.singletonList(new MediaType("application", "json")));

        HttpEntity<Object> entity = new HttpEntity<Object>(requestBody, reqHeaders);
        try {
            String dataUrl = ConstantsApp.JWT_RETRIEVE_INFO_MOCK;
            ResponseEntity<UserJwtInfoResponse> response =
                    restTemplate.exchange(ConstantsApp.AUTH_SERVER_BASE_URL + dataUrl,
                            HttpMethod.POST, entity, UserJwtInfoResponse.class);

            // save mongodb response
            myAuthService.updateToken(response.getBody());

            return ResponseEntity.status(HttpStatus.OK).body(response.getBody());
        } catch(Exception ex) {
            log.log(java.util.logging.Level.SEVERE, "ERRORE: "+ex.getMessage(), ex);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

}
