package learning.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import learning.pages.LoginPage;

import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Epic("Login Module")
@Feature("Login Scenarios")
public class LoginTest {
    static WebDriver driver;
    static String baseUrl;
    static String username;
    static String password;

    @BeforeAll
    public static void setUp() throws Exception {
        Properties props = new Properties();
        props.load(LoginTest.class.getClassLoader().getResourceAsStream("config.properties"));

        baseUrl = props.getProperty("url");
        username = props.getProperty("username");
        password = props.getProperty("password");

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(baseUrl + "/login");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Story("Valid Login")
    @Description("Login using credentials from config file, must land on Dashboard")
    public void testValidLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("dashboard"),
                ExpectedConditions.urlContains("projects")
        ));

        String currentUrl = driver.getCurrentUrl();

        if (!currentUrl.contains("dashboard")) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment("Login Failure Screenshot", new ByteArrayInputStream(screenshot));
            Assertions.fail("Expected Dashboard, but landed on: " + currentUrl);
        }

        Assertions.assertTrue(currentUrl.contains("dashboard"), "Dashboard Not Visible After Login");
    }
}


