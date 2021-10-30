package in.alqaholic;

import static org.testng.Assert.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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

import io.github.bonigarcia.wdm.WebDriverManager;

public class ApplicationTest extends IntegrationTestBase {
    
    private String testTodo = "testwith8characters";
    
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

    @Test(dependsOnMethods = "hasTitle")
    public void addTodo() {
        driver.findElement(By.xpath("//input[@name='value']")).sendKeys(testTodo + Keys.ENTER);

        new WebDriverWait(driver, 15)
            .until(ExpectedConditions.numberOfElementsToBe(By.xpath("//li"), 1));

        List<WebElement> items = driver.findElements(By.xpath("//li"));
        assertSame(items.size(), 1);
    }

    @Test(dependsOnMethods = "addTodo")
    public void toggleTodo() {
        WebElement button = driver.findElement(By.xpath("//li/form[1]/button"));
        button.click();

        new WebDriverWait(driver, 15)
            .until(ExpectedConditions.attributeContains(By.xpath("//li/form[1]/button"), "class", "line-through"));

        button = driver.findElement(By.xpath("//li/form[1]/button"));
        button.click();
        
        new WebDriverWait(driver, 15)
            .until(ExpectedConditions.not(
                ExpectedConditions.attributeContains(By.xpath("//li/form[1]/button"), "class", "line-through")
            ));
        
        assertFalse(driver.findElement(By.xpath("//li/form[1]/button")).getAttribute("class").contains("line-through"));
    }

    @Test(dependsOnMethods = "toggleTodo")
    public void deleteTodo() {
        WebElement button = driver.findElement(By.xpath("//li/form[2]/button"));
        button.click();

        new WebDriverWait(driver, 15)
            .until(ExpectedConditions.numberOfElementsToBe(By.xpath("//li"), 0));

        List<WebElement> items = driver.findElements(By.xpath("//li"));
        assertSame(items.size(), 0);
    }

    @AfterTest
    void teardown() {
        driver.quit();
    }

}
