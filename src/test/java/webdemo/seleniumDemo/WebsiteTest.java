package webdemo.seleniumDemo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class WebsiteTest {

    private static WebDriver driver;

    @BeforeAll
    public static void setUpDriver() {
        System.setProperty("webdriver.gecko.driver", "resources/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        WebDriverManager.chromedriver().setup();


        driver = new ChromeDriver(options);
        // Implicity wait -> max czas na znalezienie elementu na stronie
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @BeforeEach
    public void setUp() {
        driver.get("https://bing.com/");
    }


    @Test
    public void testFindingFirstAndThirdElement() {
        WebElement form = (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".sb_form_q")));

        form.sendKeys("selenium");
        form.submit();
        WebElement firstElement = (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("li.b_algo > h2 > a ")));
        firstElement.click();
        driver.navigate().back();

        WebElement thirdElement = driver.findElement(By.cssSelector("ol li:nth-of-type(3) > h2 > a"));
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("document.querySelector('li:nth-of-type(3) > h2 > a').click()");

        assertAll(() -> {
            assertNotNull(firstElement);
            assertNotNull(thirdElement);
        });
    }

    @Test
    public void testNoClicking() {
        WebElement p = driver.findElement(By.name("q"));

        p.sendKeys("selenium");

        p.submit();
        WebElement firstResult = driver.findElement(By.cssSelector("li.b_algo > h2 > a"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstResult);

        assertNotNull(firstResult);
    }

    @Test
    public void testForHandleNoElement() {
        assertThrows(Exception.class, () -> driver.findElement(By.name("qaasssssdddd")));
    }


    @Test
    public void testSearchForm() {
        WebElement search = driver.findElement(By.name("q"));

        assertNotNull(search);

    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }
}
