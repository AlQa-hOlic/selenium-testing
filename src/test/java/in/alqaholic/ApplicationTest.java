package in.alqaholic;

import static org.testng.Assert.assertSame;

import org.testng.annotations.Test;

public class ApplicationTest extends IntegrationTestBase {
    
    @Test
    public void itWorks() {
        assertSame("itWorks".equalsIgnoreCase("itworks"), true);
    }

}
