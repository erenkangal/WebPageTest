import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

public class LoginPageTest {
    private WebDriver driver;
    private String browser;

    @Factory(dataProvider = "browserProvider")
    public LoginPageTest(String browser) {
        this.browser = browser;
    }

    @DataProvider(name = "browserProvider")
    public static Object[][] browserProvider() {
        return new Object[][]{{"chrome"}, {"firefox"}};
    }

    @BeforeClass
    public void prepareDriver() {
        if (browser.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            driver = new ChromeDriver(options);
        } else if (browser.equals("firefox")) {
            System.setProperty("webdriver.gecko.driver", "C:\\chromedriver\\geckodriver.exe");
            FirefoxOptions options = new FirefoxOptions();
            driver = new FirefoxDriver(options);
        }
    }

    @Test
    public void verifyValidLogin() {
        driver.get("file:///C:/Users/erenk/IdeaProjects/hw4_2_webpage/src/main/main.html");

        driver.findElement(By.id("username")).sendKeys("admin");
        driver.findElement(By.id("password")).sendKeys("password");
        driver.findElement(By.xpath("//button[text()='Login']")).click();

        Assert.assertEquals(driver.getCurrentUrl(), "file:///C:/Users/erenk/IdeaProjects/hw4_2_webpage/src/main/welcome.html");

        System.out.println("Successfully logged in!");
    }

    @Test
    public void verifyInvalidLogin() {
        driver.get("file:///C:/Users/erenk/IdeaProjects/hw4_2_webpage/src/main/main.html");

        driver.findElement(By.id("username")).sendKeys("admin");
        driver.findElement(By.id("password")).sendKeys("wrongpassword");
        driver.findElement(By.xpath("//button[text()='Login']")).click();

        String errorMessage = driver.findElement(By.id("errorMessage")).getText();

        System.out.println("Login attempt failed with error: " + errorMessage);
        Assert.assertEquals(errorMessage, "Invalid username or password");
    }

    @AfterClass
    public void cleanUp() {
        if (driver != null) {
            driver.quit();
        }
    }
}
