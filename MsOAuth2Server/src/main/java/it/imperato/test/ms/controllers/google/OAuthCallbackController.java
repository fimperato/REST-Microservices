package it.imperato.test.ms.controllers.google;

import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * /oauth/callback
 *
 */
@Log
@RestController
@RequestMapping("/oauth")
public class OAuthCallbackController {

    @RequestMapping("/callback")
    public void processOAuthCallback(HttpServletResponse response) throws IOException {

        log.info(response!=null?response.toString():"");

    }


}