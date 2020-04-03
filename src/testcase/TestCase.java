package testcase;


import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import utility.Utility;

import java.util.concurrent.TimeUnit;

/**
 * Created by Jay
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestCase extends Utility {

    static String email = null;
    String password = "Abc123";
    String baseUrl = "https://demostore.x-cart.com/";

    @BeforeClass
    public static void setUp(){
        email = "test" + getRandomString(5) + "@gmail.com";
    }

    @Before
    public void openBrowser() {
        // Open chrome browser
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.NONE);
        driver = new ChromeDriver(options);
        driver.manage().window().setPosition(new Point(-2000, 0));//display into second screen
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(baseUrl);
    }

    @Test
    public void userShouldCreateNewAccount() throws InterruptedException {
        System.out.println("Username is " + email);

        Thread.sleep(3000);
        clickOnElement(By.xpath("//div[contains(@class,'header_bar-sign_in')]//button[contains(@class,'popup-button popup-login')]"));
        clickOnElement(By.xpath("//a[@class='popup-button default-popup-button create-account-link']"));
        sendTextToElement(By.xpath("//input[@name='login']"), email);
        Thread.sleep(2000);
        sendTextToElement(By.xpath("//input[@name='password']"), password);
        sendTextToElement(By.xpath("//input[@name='password_conf']"), password);
        clickOnElement(By.xpath("//div[contains(@class,'button submit')]//button[contains(@class,'submit')]"));
        String expectedText = "My account";
        String actualText = getTextFromElement(By.xpath("//h1[@class='title']"));
        Assert.assertEquals(expectedText, actualText);
        clickOnElement(By.xpath("//a[contains(text(),'My account')]"));
        clickOnElement(By.xpath("//*[@id=\"header-bar\"]/div[3]/ul/li[7]/a/span"));
        String expectedSignInText = "Sign in / sign up";
        String actualSignInText = getTextFromElement(By.xpath("//div[contains(@class,'header_bar-sign_in')]//span[contains(text(),'Sign in')]"));
        Assert.assertEquals(expectedSignInText, actualSignInText);
    }

    @Test
    public void userShouldLoginSuccessfully() throws InterruptedException {
        clickOnElement(By.xpath("//div[contains(@class,'header_bar-sign_in')]//button[contains(@class,'popup-button popup-login')]"));
        sendTextToElement(By.xpath("//input[@name='login']"), email);
        sendTextToElement(By.xpath("//input[@name='password']"), password);
        clickOnElement(By.xpath("//table[contains(@class,'login-form')]//button[contains(@class,'submit')]"));
        Thread.sleep(2000);
        String expectedText2 = "My account";
        String actualText2 = getTextFromElement(By.xpath("//div[@class=\"dropdown header_bar-my_account\"]"));
        Assert.assertEquals(expectedText2, actualText2);
        clickOnElement(By.xpath("//a[contains(text(),'My account')]"));
        clickOnElement(By.xpath("//*[@id=\"header-bar\"]/div[3]/ul/li[7]/a/span"));
        String expectedSignInText = "Sign in / sign up";
        String actualSignInText = getTextFromElement(By.xpath("//div[contains(@class,'header_bar-sign_in')]//span[contains(text(),'Sign in')]"));
        Assert.assertEquals(expectedSignInText, actualSignInText);
    }

    @After
    public void closeBrowser() {
        driver.quit();
    }


}
