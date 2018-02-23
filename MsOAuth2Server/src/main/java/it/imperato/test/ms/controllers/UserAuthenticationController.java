package it.imperato.test.ms.controllers;

import it.imperato.test.ms.model.entities.User;
import it.imperato.test.ms.model.pojo.UserInMem;
import it.imperato.test.ms.services.LoginService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/jwt")
public class UserAuthenticationController {

    private static final Logger log = LoggerFactory.getLogger(UserAuthenticationController.class);

    @Autowired
    LoginService loginService;

    @RequestMapping("/userAuthentication")
    public String userAuthenticationTest(){
        return "userAuthentication controller ready.";
    }

    @AllArgsConstructor
    public class UserJwtInfoResponseBody implements Serializable {
        @Getter @Setter
        private int serverCode;
        @Getter @Setter
        private String jwt;
        @Getter @Setter
        private User user;
        @Getter @Setter
        private String message;
    }

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

    @RequestMapping(value = "/loginUser", method = POST)
    public ResponseEntity<UserInMemJwtInfoResponseBody> loginUser(@RequestHeader HttpHeaders headers,
                                                      @RequestBody User reqBody) {

        return null;

    }

}
