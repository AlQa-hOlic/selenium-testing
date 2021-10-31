package in.alqaholic;

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

public class TodoTest extends IntegrationTestBase {
    
    @LocalServerPort
    protected int port;

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

    @AfterTest
    void teardown() {
        driver.quit();
    }

    @Test
    public void hasTitle() {
        driver.get("http://localhost:" + port + "/");
        new WebDriverWait(driver, 10)
            .until(ExpectedConditions.titleIs("Todo App"));
    }

    @Test(dependsOnMethods = "hasTitle")
    public void addTodo() {
        driver.findElement(By.xpath("//input[@name='value']")).sendKeys("TODO_TEXT" + Keys.ENTER);
        new WebDriverWait(driver, 5)
            .until(ExpectedConditions.numberOfElementsToBe(By.xpath("//li"), 1));
    }

    @Test(dependsOnMethods = "addTodo")
    public void toggleTodo() {
        WebElement button = driver.findElement(By.xpath("//li/form/button"));
        button.click();

        new WebDriverWait(driver, 5)
            .until(ExpectedConditions.attributeContains(By.xpath("//li/form/button"), "class", "line-through"));

        button = driver.findElement(By.xpath("//li/form/button"));
        button.click();
        
        new WebDriverWait(driver, 5)
            .until(ExpectedConditions.not(
                ExpectedConditions.attributeContains(By.xpath("//li/form/button"), "class", "line-through")
            ));
    }

    @Test(dependsOnMethods = "toggleTodo")
    public void editTodo() {
        WebElement anchorTag = driver.findElement(By.xpath("//li/div/a"));
        anchorTag.click();

        new WebDriverWait(driver, 5)
            .until(ExpectedConditions.titleIs("Edit Todo | Todo App"));

        WebElement input = driver.findElement(By.xpath("//main/form/input"));
        input.clear();

        input.sendKeys("NEW_TODO_TEXT" + Keys.ENTER);

        new WebDriverWait(driver, 5)
            .until(ExpectedConditions.textToBe(By.xpath("//li/form/button"), "NEW_TODO_TEXT"));
    }

    @Test(dependsOnMethods = "editTodo")
    public void deleteTodo() {
        WebElement button = driver.findElement(By.xpath("//li/div/form/button"));
        button.click();

        new WebDriverWait(driver, 5)
            .until(ExpectedConditions.numberOfElementsToBe(By.xpath("//li"), 0));
    }
}
