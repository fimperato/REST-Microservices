package it.imperato.test.ms;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.imperato.test.ms.controllers.sample.TestController;
import it.imperato.test.ms.model.pojo.Company;
import it.imperato.test.ms.model.restbean.MyResponseBody;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MyApplicationTest {

    private MockMvc mvc;

    @InjectMocks
    private TestController testController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        // We would need this line if we would not use MockitoJUnitRunner
        // MockitoAnnotations.initMocks(this);
        // Initializes the JacksonTester
        JacksonTester.initFields(this, new ObjectMapper());
        // MockMvc standalone approach
        mvc = MockMvcBuilders.standaloneSetup(testController)
                .build();
    }

    @Test
    public void contextLoads() {
    }

    @Ignore
    @Test
    public void canRetrieveResponseByMockMvc() throws Exception {
        // given
        // ...

        // when
        MockHttpServletResponse response = mvc.perform(
                post("/test/testCompany")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void canRetrieveResponseByRestTemplate() {
        // given
        // ...

        // when
        ResponseEntity<MyResponseBody> testCompanyResponse = restTemplate
                .postForEntity("/test/testCompany", new Company(), MyResponseBody.class);

        // then
        assertThat(testCompanyResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
