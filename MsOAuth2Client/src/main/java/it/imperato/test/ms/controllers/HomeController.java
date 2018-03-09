package it.imperato.test.ms.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping("/")
    public String getHomeController(){
        return "Home Controller ready.";
    }

    @RequestMapping("/403")
    public String accessDenied() {

        return "errors/403";
    }

    @RequestMapping(
            value = "/user",
            method = RequestMethod.GET)
    public Principal user(Principal user) {

        return user;
    }

    @RequestMapping(value = "/test/send403", method = RequestMethod.GET)
    public ResponseEntity<?> send403() {

        return new ResponseEntity<Object>(null, HttpStatus.FORBIDDEN);
    }

}
