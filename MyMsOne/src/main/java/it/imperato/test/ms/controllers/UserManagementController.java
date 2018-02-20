package it.imperato.test.ms.controllers;

import io.jsonwebtoken.ExpiredJwtException;
import it.imperato.test.ms.exceptions.UserNotLoggedException;
import it.imperato.test.ms.model.pojo.UserInMem;
import it.imperato.test.ms.services.MyAuthService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class UserManagementController {

    private static final Logger log = LoggerFactory.getLogger(UserManagementController.class);

    @Autowired
    MyAuthService authService;

    @RequestMapping("/userManagement")
    public String userManagementTest(){
        return "userManagement controller ready.";
    }

    @AllArgsConstructor
    public class JsonResponseBody implements Serializable {
        @Getter @Setter
        private int server;
        @Getter @Setter
        private Object response;
    }

    @AllArgsConstructor
    public class JwtResponseObject implements Serializable {
        @Getter @Setter
        private String jwtValue;
        @Getter @Setter
        private String msg;
    }

    /*  Esecuzione test con Postman (modalità x-www-form-urlencoded) */

    @RequestMapping(value = "/authUserTest", method = POST)
    public ResponseEntity<JsonResponseBody> authUserTest(
            @RequestParam(value ="userId") String userId, @RequestParam(value="userPsw") String userPsw){

        // Se l'user è censito a DB (controllo fake), viene generato il JWT e viene inviato al client
        try {
            Optional<UserInMem> userFound = authService.checkUserForTest(userId, userPsw);
            if(userFound.isPresent()){
                UserInMem user = userFound.get();
                String jwt = authService.createJwt(user.getId(), user.getUsername(), user.getPermission(), new Date());
                JwtResponseObject jwtResponse = new JwtResponseObject(jwt, "Success! User logged in (with jwt passed).");
                return ResponseEntity.status(HttpStatus.OK).header("jwt", jwt).body(
                        new JsonResponseBody(HttpStatus.OK.value(), jwtResponse));
            }
        }
        catch(UnsupportedEncodingException unsEx){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    new JsonResponseBody( HttpStatus.FORBIDDEN.value(), "Unsupported Encoding: " + unsEx.toString() ));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new JsonResponseBody(HttpStatus.FORBIDDEN.value(), "User not exists in DB!"));
    }

    @RequestMapping(value = "/jwtByClientTest", method = POST)
    public ResponseEntity<JsonResponseBody> jwtByClientTest(HttpServletRequest request) {

        // Request -> get JWT by client -> check JWT info
        try {
            Map<String, Object> jwtInfo = authService.checkAndReadJwtByClient(request);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new JsonResponseBody(HttpStatus.OK.value(), jwtInfo ));
        }
        catch(UnsupportedEncodingException unsEx){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    new JsonResponseBody( HttpStatus.FORBIDDEN.value(), "Unsupported Encoding: " + unsEx.toString() ));
        }
        catch(UserNotLoggedException unlEx){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    new JsonResponseBody( HttpStatus.GATEWAY_TIMEOUT.value(), "User not logged in!: " + unlEx.toString() ));
        }
        catch(ExpiredJwtException jwtEx){
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(
                    new JsonResponseBody( HttpStatus.GATEWAY_TIMEOUT.value(), "Session Expired!: " + jwtEx.toString() ));
        }
    }

}
