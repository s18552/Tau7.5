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
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class EntireWebsiteTest {
    private static WebDriver chromeDriver;
    private static WebDriver edgeDriver;

    @BeforeAll
    public static void setUpDriver() {
        ChromeOptions chromeOptions = new ChromeOptions();
        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.addArguments("--headless");
        chromeOptions.addArguments("--headless");
        WebDriverManager.edgedriver().setup();
        WebDriverManager.chromedriver().setup();
        edgeDriver = new EdgeDriver(edgeOptions);
        chromeDriver = new ChromeDriver(chromeOptions);
        edgeDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        chromeDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @BeforeEach
    public void setUp(){
        chromeDriver.get("http://automationpractice.pl/index.php");
        edgeDriver.get("http://automationpractice.pl/index.php");
    }

    @Test
    public void checkTitleWithChromeDriver() {
        String title = chromeDriver.getTitle();
        assertEquals(title, "My Store");
    }

    @Test
    public void checkTitleWithEdgeDriver() {
        String title = edgeDriver.getTitle();
        assertEquals(title, "My Store");
    }

    @Test
    public void checkSearchBoxWithChromeDriver() {
        WebElement searchBox = chromeDriver.findElement(By.id("search_query_top"));
        searchBox.sendKeys("T-shirt");
        searchBox.submit();
        WebElement headerDiv = chromeDriver.findElement(By.xpath("//*[@id=\"center_column\"]/h1/span[1]"));
        assertEquals(headerDiv.getText(), "\"T-SHIRT\"");
    }

    @Test
    public void checkSearchBoxWithEdgeDriver() {
        WebElement searchBox = edgeDriver.findElement(By.id("search_query_top"));
        searchBox.sendKeys("T-SHIRT");
        searchBox.submit();
        WebElement headerDiv = edgeDriver.findElement(By.xpath("//*[@id=\"center_column\"]/h1/span[1]"));
        assertEquals(headerDiv.getText(), "\"T-SHIRT\"");
    }

    @Test
    public void checkAddItemToChartWithChromeDriver() {
        WebElement searchBox = chromeDriver.findElement(By.id("search_query_top"));
        searchBox.sendKeys("T-shirt");
        searchBox.submit();
        WebElement addButton = chromeDriver
                .findElement(By.xpath("//*[@id=\"center_column\"]/ul/li/div/div[2]/div[2]/a[1]"));
        addButton.click();
        WebElement acceptanceIcon = chromeDriver.findElement(
                By.xpath("//*[@id=\"layer_cart\"]/div[1]/div[1]/h2/i"));

        assertNotNull(acceptanceIcon);

    }

    @Test
    public void checkAddItemToChartWithEdgeDriver() {
        WebElement searchBox = edgeDriver.findElement(By.id("search_query_top"));
        searchBox.sendKeys("T-shirt");
        searchBox.submit();
        WebElement addButton = edgeDriver
                .findElement(By.xpath("//*[@id=\"center_column\"]/ul/li/div/div[2]/div[2]/a[1]"));
        addButton.click();
        WebElement acceptanceIcon = edgeDriver.findElement(
                By.xpath("//*[@id=\"layer_cart\"]/div[1]/div[1]/h2/i"));

        assertNotNull(acceptanceIcon);

    }

    @Test
    public void checkBlogWithEdgeDriver() {
        WebElement blog = edgeDriver.findElement(By.xpath("//*[@id=\"block_top_menu\"]/ul/li[4]/a"));
        blog.click();
        new WebDriverWait(edgeDriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.numberOfWindowsToBe(2));

        WebDriver window = edgeDriver.switchTo().window(
                edgeDriver.getWindowHandles()
                        .stream()
                        .reduce((f, s) -> s)
                        .orElse(null));

        String currentUrl = window.getCurrentUrl();

        assertEquals(currentUrl, "https://www.prestashop.com/pl/blog");

    }

    @Test
    public void checkBlogWithChromeDriver() {
        WebElement blog = chromeDriver.findElement(By.xpath("//*[@id=\"block_top_menu\"]/ul/li[4]/a"));
        blog.click();
        new WebDriverWait(chromeDriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.numberOfWindowsToBe(2));

        WebDriver window = chromeDriver.switchTo().window(
                chromeDriver.getWindowHandles()
                        .stream()
                        .reduce((f, s) -> s)
                        .orElse(null));

        String currentUrl = window.getCurrentUrl();

        assertEquals(currentUrl, "https://www.prestashop.com/pl/blog");

    }

    @Test
    public void checkContactWithEdgeDriver() {
        WebElement contact = edgeDriver.findElement(By.xpath("//*[@id=\"contact-link\"]/a"));
        contact.click();
        WebElement contactHeader = edgeDriver.findElement(By.xpath("//*[@id=\"center_column\"]/h1"));
        assertTrue(contactHeader.getText().contains("CUSTOMER SERVICE - CONTACT US"));

    }

    @Test
    public void checkContactWithChromeDriver() {
        WebElement contact = chromeDriver.findElement(By.xpath("//*[@id=\"contact-link\"]/a"));
        contact.click();
        WebElement contactHeader = chromeDriver.findElement(By.xpath("//*[@id=\"center_column\"]/h1"));
        assertTrue(contactHeader.getText().contains("CUSTOMER SERVICE - CONTACT US"));

    }


    @AfterAll
    public static void tearDown() {
        edgeDriver.quit();
        chromeDriver.quit();
    }
}
