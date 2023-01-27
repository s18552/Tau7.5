package webdemo.seleniumDemo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class LoginToWPTest {

    private static WebDriver driver;

    @BeforeAll
    public static void setUpDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void testLoginToWp() {
        driver.get("https://profil.wp.pl/login/login.html");
        WebElement username = driver.findElement(By.id("login"));
        username.sendKeys("tau7.4");
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("kibel123!@#");
        password.submit();
        WebElement settingsAccount = driver.findElement(By.xpath("//*[@id=\"olw-db-user\"]/div/a[1]"));
        assertNotNull(settingsAccount);
    }

    @Test
    public void testOtherLoginToWp() {
        driver.get("https://profil.wp.pl/login/login.html");
        WebElement username = driver.findElement(By.id("login"));
        username.sendKeys("tau7.4");
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("kibel123!@#");
        password.submit();
        WebElement userPanel = driver.findElement(By.xpath("//*[@id=\"folder-1\"]/div[2]"));
        assertTrue(userPanel.getText().contains("Odebrane"));
    }

    @Test
    public void testIncorrectLoginToWp() {
        driver.get("https://profil.wp.pl/login/login.html");
        WebElement username = driver.findElement(By.id("login"));
        username.sendKeys("XXX");
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("XXX");
        password.submit();
        WebElement fieldTextWithMessage = driver.findElement(By.xpath("//*[@id=\"stgMain\"]/div/div/div[1]/form/div[3]"));
        assertTrue(fieldTextWithMessage.getText().contains("Spróbuj jeszcze raz"));
    }

    @Test
    public void testOtherIncorrectLoginToWp() {
        driver.get("https://profil.wp.pl/login/login.html");
        WebElement username = driver.findElement(By.id("login"));
        username.sendKeys("XXX");
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("XXX");
        password.submit();
        assertThrows(NoSuchElementException.class,
                () -> driver.findElement(By.xpath("//*[@id=\"olw-db-user\"]/div/a[1]")));
    }

}
