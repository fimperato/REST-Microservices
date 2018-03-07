package it.imperato.test.ms.controllers;

import io.jsonwebtoken.ExpiredJwtException;
import it.imperato.test.ms.exceptions.UserNotLoggedException;
import it.imperato.test.ms.model.entities.User;
import it.imperato.test.ms.model.pojo.UserInMem;
import it.imperato.test.ms.services.JwtAuthService;
import it.imperato.test.ms.services.LoginService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/jwt")
public class UserAuthenticationController {

    private static final Logger log = LoggerFactory.getLogger(UserAuthenticationController.class);

    @Autowired
    LoginService loginService;

    @Autowired
    JwtAuthService jwtAuthService;

    @RequestMapping("/userAuthentication")
    public String userAuthenticationTest(){
        return "userAuthentication controller ready.";
    }

    /*@AllArgsConstructor
    public class UserJwtInfoResponseBody implements Serializable {
        @Getter @Setter
        private int serverCode;
        @Getter @Setter
        private String jwt;
        @Getter @Setter
        private User user;
        @Getter @Setter
        private String message;
    }*/

    @AllArgsConstructor
    public class UserInMemJwtInfoResponseBody implements Serializable {
        @Getter @Setter
        private int serverCode;
        @Getter @Setter
        private String jwt;
        @Getter @Setter
        private UserInMem userInMem;
        @Getter @Setter
        private String message;
    }

    @RequestMapping(value = "/loginUserMock", method = POST)
    public ResponseEntity<UserInMemJwtInfoResponseBody> loginUser(@RequestHeader HttpHeaders headers,
                                                      @RequestBody User reqBody) {

        try {
            Optional<UserInMem> user = loginService.checkCredentialsMock(reqBody, headers);
            UserInMem userFound = user.get();
            String jwt = null;
            try {
                jwt = jwtAuthService.checkJwt(headers);
            } catch(UserNotLoggedException unlEx) {
                log.info("JWT valido non presente, viene ricreato.");
                jwt = jwtAuthService
                        .createJwt(userFound.getId(), userFound.getUsername(), userFound.getPermission(), new Date());
            }
            return ResponseEntity.status(HttpStatus.OK).body(
                    new UserInMemJwtInfoResponseBody(HttpStatus.OK.value(), jwt, userFound,
                            "User e JWT recuperati correttamente." ));
        }
        catch(UserNotLoggedException unlEx){
            String msg = "User not logged in! ";
            log.error(msg + unlEx.toString(), unlEx);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    new UserInMemJwtInfoResponseBody( HttpStatus.FORBIDDEN.value(),
                            "", null, msg ));
        }
        catch(ExpiredJwtException jwtEx){
            String msg = "Session Expired! ";
            log.error(msg + jwtEx.toString(), jwtEx);
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(
                    new UserInMemJwtInfoResponseBody( HttpStatus.GATEWAY_TIMEOUT.value(),
                            "", null, msg ));
        } catch (UnsupportedEncodingException unsEx) {
            String msg = "Unsupported operation! ";
            log.error(msg + unsEx.toString(), unsEx);
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(
                    new UserInMemJwtInfoResponseBody( HttpStatus.GATEWAY_TIMEOUT.value(),
                            "",null, msg ));
        }
    }


    // TODO : @RequestMapping(value = "/loginUserMock", method = POST)

}
