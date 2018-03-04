package it.imperato.test.ms.controllers;

import it.imperato.test.ms.controllers.UserManagementController;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

}
