package in.alqaholic;

import static org.testng.Assert.assertSame;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ApplicationTest extends IntegrationTestBase {
    
    WebDriver driver;
    
    @BeforeSuite
    void setupClass() {
        WebDriverManager.firefoxdriver().setup();
    }


    @BeforeTest
    void setupTest() {
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(System.getenv("CI") != null);
        driver = new FirefoxDriver(options);
    }


    @Test
    public void itWorks() {
        driver.get("http://localhost:" + port + "");
        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(driver -> driver.getTitle().equals("Todo App"));
        assertSame("itWorks".equalsIgnoreCase("itworks"), true);
    }

    @AfterTest
    void teardown() {
        driver.quit();
    }

}
