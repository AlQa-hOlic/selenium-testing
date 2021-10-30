package in.alqaholic;

import static org.testng.Assert.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.web.server.LocalServerPort;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ApplicationTest extends IntegrationTestBase {
    
    @LocalServerPort
    private int port;
    
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
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void hasTitle() {
        driver.get("http://localhost:" + port + "/");
        new WebDriverWait(driver, 15)
            .until(ExpectedConditions.titleContains("Todo App"));

        assertEquals(driver.getTitle(), "Todo App");
    }

    @Test
    public void addTodo() {
        String testTodo = "testwith8characters";

        driver.get("http://localhost:" + port + "/");

        driver.findElement(By.xpath("//input[@name='value']")).sendKeys(testTodo + Keys.ENTER);

        new WebDriverWait(driver, 15)
            .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//li")));

        List<WebElement> items = driver.findElements(By.xpath("//li"));
        
        assertSame(items.size(), 1);
    }

    @AfterTest
    void teardown() {
        driver.quit();
    }

}
