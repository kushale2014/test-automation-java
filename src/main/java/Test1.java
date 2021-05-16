import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import pages.LoginPage;

import java.time.Duration;

public class Test1 {

    private static String url = "https://qatest-28flsd5.meshmd.com/SignIn?r=%2F";

    @Test
    public void test1() {
        System.setProperty("webdriver.chrome.driver", "c:\\tools\\chromedriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        try {
            driver.get(url);
            Thread.sleep(2000);
            LoginPage loginPage = new LoginPage(driver);
//            loginPage.verifyAllLinks(url);
            loginPage.verifyLink("a[href='/register']", url);
            loginPage.verifyLink("a[href='/passwordForgot']", url);
            loginPage.verifyLink("a[href*='?lang']", url);
            loginPage.verifyLink("a[href='/terms']", url);
            loginPage.verifyLink("a[href='/privacy']", url);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
            driver.quit();
        }
    }
}
