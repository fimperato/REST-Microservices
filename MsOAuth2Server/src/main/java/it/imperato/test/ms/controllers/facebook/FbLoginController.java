package it.imperato.test.ms.controllers.facebook;

import it.imperato.test.ms.configurations.facebook.FbGlobalProperties;
import it.imperato.test.ms.configurations.facebook.GlobalProperties;
import it.imperato.test.ms.utils.ConstantsApp;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

@Log
@RestController
@RequestMapping("/login")
public class FbLoginController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    GlobalProperties globalProperties;

    private static final String authorizationRequestBaseUri = "oauth2/authorize-client";
    Map<String, String> oauth2AuthenticationUrls = new HashMap<>();

    @RequestMapping("/oauth2_fb_login")
    public void oauth2LoginRedirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("http://localhost"+":"+globalProperties.getServerPort()
                +ConstantsApp.FB_LOGIN_URI);
    }

}