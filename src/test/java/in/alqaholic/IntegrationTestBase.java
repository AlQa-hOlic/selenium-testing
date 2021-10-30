package in.alqaholic;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@SpringBootTest(classes = SeleniumTesting.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class IntegrationTestBase extends AbstractTestNGSpringContextTests {
    @LocalServerPort
    protected int port;
}
