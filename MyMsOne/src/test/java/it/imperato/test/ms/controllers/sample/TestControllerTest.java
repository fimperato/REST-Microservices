package it.imperato.test.ms.controllers.sample;

import it.imperato.test.ms.model.pojo.Company;
import it.imperato.test.ms.model.restbean.MyResponseBody;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestControllerTest {

    @Autowired
    TestController testController;

    @Test
    public void testMethodController() throws Exception {
        String res = testController.testMethod();
        Assert.assertEquals("TestController ready.", res);
    }

    @Test
    public void testCompanyTest() throws Exception {
        ResponseEntity<MyResponseBody> res = testController.testCompany(new Company());
        Assert.assertEquals(HttpStatus.OK, res.getStatusCode());
    }

}
