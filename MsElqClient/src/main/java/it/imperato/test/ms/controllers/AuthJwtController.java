package it.imperato.test.ms.controllers;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import it.imperato.test.ms.model.entities.JwtTokenInfo;
import it.imperato.test.ms.model.pojo.User;
import it.imperato.test.ms.model.pojo.UserJwtInfoResponse;
import it.imperato.test.ms.model.restbean.Activity;
import it.imperato.test.ms.model.restbean.ActivityRequestBody;
import it.imperato.test.ms.model.restbean.ActivityResponseBody;
import it.imperato.test.ms.services.MyAuthService;
import it.imperato.test.ms.utils.ConstantsApp;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
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
@RequestMapping("/jwt")
public class AuthJwtController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    MyAuthService myAuthService;

    @RequestMapping("/authJwtController")
    public String authJwtController(){
        return "AuthJwtController (Client) ready.";
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
