package it.imperato.test.ms.services.impl;

import it.imperato.test.ms.exceptions.UserNotLoggedException;
import it.imperato.test.ms.model.entities.User;
import it.imperato.test.ms.model.pojo.UserInMem;
import it.imperato.test.ms.utils.JwtUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MyAuthServiceImplTest {

    @Autowired
    MyAuthServiceImpl myAuthService;

    @Test
    public void createJwtTest() throws Exception {
        String subject="subject";
        String name="name";
        String permission="permission";
        String token = myAuthService.createJwt(subject,name,permission,new Date());
        Assert.assertNotNull(token);
    }

    @Test
    public void doSomethingTest() throws Exception {
        HttpServletRequest request = null;
        try {
            Map<String, Object> res = myAuthService.verifyJwtAndDoSomeOperation(request);
        } catch (UserNotLoggedException e) {
            Assert.assertEquals("Request not well formed.", e.getMessage());
        }
    }

    @Test
    public void checkUserForTest() {
        String userId="user";
        String userPsw="pass";
        Optional<UserInMem> user = myAuthService.checkUserForTest(userId,userPsw);
        Assert.assertEquals(user.get().getUsername(), userId);
    }

    @Test
    public void checkUserTest() {
        String userId="user";
        String userPsw="pass";
        Optional<User> user = myAuthService.checkUser(userId,userPsw);
        Assert.assertFalse(user.isPresent());
    }

    @Test
    public void checkAndReadJwtByClientTest() throws Exception {
        Map<String, Object> res = myAuthService.checkAndReadJwtByClient(null);
        Assert.assertNull(res);
    }

}
