package it.imperato.test.ms.controllers;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserManagementControllerTest {

    @Autowired
    UserManagementController userManagementController;

    @Test
    public void userManagementTest(){
        String res = userManagementController.userManagementTest();
        Assert.assertEquals("userManagement controller ready.", res);
    }

    @Test
    public void authUserTest() {
        ResponseEntity<UserManagementController.JsonResponseBody> res =
                userManagementController.authUserTest("user","pass");
        Assert.assertEquals(HttpStatus.OK, res.getStatusCode());
    }

    @Test
    public void jwtByClientTest() {
        ResponseEntity<UserManagementController.JsonResponseBody> res =
                userManagementController.jwtByClientTest(null);
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode());
    }


}
