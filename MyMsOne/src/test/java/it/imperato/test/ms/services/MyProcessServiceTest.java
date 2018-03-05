package it.imperato.test.ms.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MyProcessServiceTest {

    @Autowired
    MyProcessService myProcessService;

    @Test
    public void doSomethingTest() throws Exception {
        String res = myProcessService.doSomething("");
        Assert.assertNull(res);
    }

}
