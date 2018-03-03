package it.imperato.test.ms.controllers;

import it.imperato.test.ms.app.GlobalProperties;
import it.imperato.test.ms.utils.ConstantsApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class CustomErrorController
        implements ErrorController
        {

    private static final Logger log = LoggerFactory.getLogger(CustomErrorController.class);

    private static final String PATH = "/error";

    @Autowired
    GlobalProperties globalProperties;

    @Override
    public String getErrorPath() {
        return PATH;
    }

    @RequestMapping(value = PATH)
    public void error(HttpServletResponse response) throws IOException {
        log.info("Error Controller called.");

        if(globalProperties.getOauth2ProviderFacebookActive()) {
            response.sendRedirect("http://localhost" + ":" + globalProperties.getServerPort()
                    + ConstantsApp.FB_LOGIN_URI);
        }
    }

}
