package it.imperato.test.ms;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MsFbOAuth2ServerApplicationTest {

    private MockMvc mvc;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
    }

    @Test
    public void contextLoads() {
    }


}
