package it.imperato.test.ms.controllers;

import it.imperato.test.ms.app.GlobalProperties;
import it.imperato.test.ms.model.entities.JwtTokenInfo;
import it.imperato.test.ms.model.pojo.User;
import it.imperato.test.ms.model.pojo.UserJwtInfoResponse;
import it.imperato.test.ms.services.MyAuthService;
import it.imperato.test.ms.utils.ConstantsApp;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collections;

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

    @Autowired
    GlobalProperties globalProperties;

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
                    restTemplate.exchange(globalProperties.getAuthServerBaseUrl() + dataUrl,
                            HttpMethod.POST, entity, UserJwtInfoResponse.class);

            // save mongodb response
            myAuthService.updateToken(response.getBody());

            return ResponseEntity.status(HttpStatus.OK).body(response.getBody());
        } catch(Exception ex) {
            log.log(java.util.logging.Level.SEVERE, "ERRORE: "+ex.getMessage(), ex);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @RequestMapping(value = "/saveJwtInfo", method = POST)
    public ResponseEntity<UserJwtInfoResponse> saveJwtInfo(
            @Valid JwtTokenInfo jwtTokenInfo, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new UserJwtInfoResponse(HttpStatus.NOT_ACCEPTABLE.value(),
                            null, null, bindingResult.toString()));
        }
        // save mongodb response
        myAuthService.updateToken(jwtTokenInfo);
        UserJwtInfoResponse response = new UserJwtInfoResponse(HttpStatus.OK.value(),jwtTokenInfo.getJwt(),
                null,"JWT Token saved");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @RequestMapping(value = "/saveJwtInfoValidate", method = POST)
    public ResponseEntity<UserJwtInfoResponse> saveJwtInfoValidate(
            JwtTokenInfo jwtTokenInfo, BindingResult bindingResult){
        JwtTokenValidator validator = new JwtTokenValidator();
        validator.validate(jwtTokenInfo,bindingResult);

        if(bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new UserJwtInfoResponse(HttpStatus.NOT_ACCEPTABLE.value(),
                            null, null, bindingResult.toString()));
        }
        // save mongodb response
        myAuthService.updateToken(jwtTokenInfo);
        UserJwtInfoResponse response = new UserJwtInfoResponse(HttpStatus.OK.value(),jwtTokenInfo.getJwt(),
                null,"JWT Token saved");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    class JwtTokenValidator implements Validator{

        @Override
        public boolean supports(Class<?> clazz) {
            return JwtTokenInfo.class.equals(clazz);
        }

        @Override
        public void validate(Object o, Errors errors) {
            JwtTokenInfo jwtTokenInfo = (JwtTokenInfo)o;
            if(jwtTokenInfo.getJwt()==null || jwtTokenInfo.getJwt().length()<5) {
                errors.reject("jwt","token con lunghezza non corretta!");
            }
        }
    }
}
