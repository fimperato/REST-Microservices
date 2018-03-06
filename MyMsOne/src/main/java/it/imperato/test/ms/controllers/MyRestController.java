package it.imperato.test.ms.controllers;

import io.jsonwebtoken.ExpiredJwtException;
import it.imperato.test.ms.exceptions.UserNotLoggedException;
import it.imperato.test.ms.services.MyAuthService;
import it.imperato.test.ms.services.MyProcessService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

@RestController
public class MyRestController {

    private static final Logger log = LoggerFactory.getLogger(MyRestController.class);

    @Autowired
    MyAuthService authService;

    @Autowired
    MyProcessService processService;

    @RequestMapping("/hello")
    public String helloWorld(){
        return "Hello world!";
    }

    /*  test con Postman (modalità x-www-form-urlencoded) */

    // Jackson library converte automaticamente il JsonResponseBody Object in una JSON response.
    @AllArgsConstructor
    public class JsonResponseBody implements Serializable {

        @Getter @Setter
        private int server;

        @Getter @Setter
        private Object response;

    }

    @RequestMapping("/endpointone/{myVar}")
    public ResponseEntity<JsonResponseBody> endpointOne(
            HttpServletRequest request, @PathVariable(name = "myVar") String myVar) {

        // Request -> fetch JWT -> check validity -> Do some process [...]
        try {
            authService.verifyJwtAndDoSomeOperation(request);

            //user verified
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new JsonResponseBody( HttpStatus.OK.value(), processService.doSomething(myVar) ));
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
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new JsonResponseBody( HttpStatus.INTERNAL_SERVER_ERROR.value(), "App Exception!: " + e.toString() ));
        }
    }

}
