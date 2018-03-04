package it.imperato.test.ms.controllers.sample;

import io.jsonwebtoken.ExpiredJwtException;
import it.imperato.test.ms.exceptions.UserNotLoggedException;
import it.imperato.test.ms.model.pojo.Company;
import it.imperato.test.ms.model.pojo.UserInMem;
import it.imperato.test.ms.model.restbean.MyResponseBody;
import it.imperato.test.ms.services.MyAuthService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
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

@Log
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    @Qualifier("italianCompany")
    Company myCompanyOne;
    @Autowired
    Company italianCompany; // stesso risultato

    @Autowired
    @Qualifier("Barilla")
    Company myBrlCompany;

    @RequestMapping("/testController")
    public String testMethod(){
        return "TestController ready.";
    }

    /**
     * localhost:8084/test/testCompany
     *
     * @return
     */
    @RequestMapping(value = "/testCompany", method = POST)
    public ResponseEntity<MyResponseBody> testCompany(@RequestBody Company testBody){
        try {
            if(myCompanyOne != null && myBrlCompany != null) {
                return ResponseEntity.status(HttpStatus.OK).header("header-info", "NO-INFO").body(
                        new MyResponseBody(HttpStatus.OK.value(),
                                myCompanyOne.getName()+" / "+myBrlCompany.getName(),
                                "INFO"));
            }
        }
        catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new MyResponseBody( HttpStatus.BAD_REQUEST.value(), "Error" + ex.toString(), "ERROR" ));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new MyResponseBody( HttpStatus.BAD_REQUEST.value(), "Error", "ERROR" ));
    }

}
