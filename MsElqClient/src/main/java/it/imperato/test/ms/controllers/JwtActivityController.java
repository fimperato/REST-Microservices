package it.imperato.test.ms.controllers;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import it.imperato.test.ms.app.GlobalProperties;
import it.imperato.test.ms.model.entities.JwtTokenInfo;
import it.imperato.test.ms.model.restbean.Activity;
import it.imperato.test.ms.model.restbean.ActivityRequestBody;
import it.imperato.test.ms.model.restbean.ActivityResponseBody;
import it.imperato.test.ms.services.MyAuthService;
import it.imperato.test.ms.utils.ConstantsApp;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
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
@RequestMapping("/activity")
public class JwtActivityController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    MyAuthService myAuthService;

    @Autowired
    GlobalProperties globalProperties;

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
    public ResponseEntity<ActivityResponseBody> getActivities(
            @PathVariable(name = "contactId") String contactId,
            @RequestHeader HttpHeaders httpHeaders){
        try {
            HttpHeaders reqHeaders = new HttpHeaders();

            //reqHeaders.add("Authorization", authorization);
            String jwtRequestHeaderValue = httpHeaders.get("jwt") != null ? (String) httpHeaders.get("jwt").get(0) : null;
            if(jwtRequestHeaderValue==null) {
                // verifico su db per informazione precedentemente memorizzata
                JwtTokenInfo jwtTokenInfo = myAuthService.findValidToken();
                jwtRequestHeaderValue = jwtTokenInfo!=null?jwtTokenInfo.getJwt():null;
            }
            reqHeaders.add("jwt", jwtRequestHeaderValue);

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

            String dataUrl = ConstantsApp.JWT_AUTH_TYPE_URL + ConstantsApp.ACTIVITIES_DATA_URL;
            log.info("****** Complete URL: "+globalProperties.getResourceServerBaseUrl() + dataUrl + String.valueOf(cId));
            log.info("****** jwtRequestHeaderValue: "+jwtRequestHeaderValue);
            log.info("****** ActivityRequestBody: "+reqBody.toString());
            ResponseEntity<Activity[]> response =
                    restTemplate.exchange(globalProperties.getResourceServerBaseUrl() + dataUrl + String.valueOf(cId),
                            HttpMethod.POST, entity, Activity[].class);

            List<Activity> acts = Arrays.asList(response.getBody());
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ActivityResponseBody(acts, 200, "Activity ricevute.")
            );
        }
        catch(MalformedJwtException | ExpiredJwtException ex) {
            log.log(java.util.logging.Level.SEVERE, "JWT Exception: "+ex.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    new ActivityResponseBody(null, HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.name())
            );
        }
        catch(HttpClientErrorException ex) {
            log.log(java.util.logging.Level.SEVERE, "HTTP exception: "+ex.getMessage()
                + " Status code: "+ex.getStatusCode().value()
                + " Status text: "+ex.getStatusCode().getReasonPhrase());
            return ResponseEntity.status(ex.getStatusCode()).body(
                    new ActivityResponseBody(null, ex.getStatusCode().value(), ex.getStatusCode().name())
            );
        }
        catch(Exception ex) {
            log.log(java.util.logging.Level.SEVERE, "ERRORE generico: "+ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ActivityResponseBody(null, HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name())
            );
        }
    }

}
