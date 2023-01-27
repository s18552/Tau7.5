package webdemo.seleniumDemo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class XPathTauTest {
    private static WebDriver driver;

    @BeforeAll
    public static void setUpDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @BeforeEach
    public void setUp() {
        driver.get("https://fcbarca.com/");
    }


    @Test
    public void testCountOfAllLinks() {
        List<WebElement> links = driver.findElements(By.xpath("//a"));
        assertTrue(!links.isEmpty());
    }

    @Test
    public void testCountOfImages() {
        List<WebElement> images = driver.findElements(By.cssSelector("img"));
        assertTrue(!images.isEmpty());
    }

    @Test
    public void testClickAndBackTroughAllLink() {
        List<WebElement> links = driver.findElements(By.xpath("//a"));
        long performedActionsOnElements = links.stream()
                .filter((link) -> !link.getText().isEmpty())
                .peek(val -> {
                    val.click();
                    driver.navigate().back();
                })
                .count();

        assertEquals(performedActionsOnElements, links.size());
    }

    @Test
    public void testCountOfTextFields() {
        driver.get("https://www.fcbarca.com/konto/zaloguj.html");
        List<WebElement> textFields = driver.findElements(By.xpath("//input[@type='text' or @type='password']"));
        assertEquals(2, textFields.size());
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }
}