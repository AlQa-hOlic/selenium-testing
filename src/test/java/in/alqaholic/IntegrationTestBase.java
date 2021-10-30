package in.alqaholic;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@SpringBootTest(classes = SeleniumTesting.class)
public class IntegrationTestBase extends AbstractTestNGSpringContextTests {
    
}
