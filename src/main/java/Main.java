import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.LoginPage;

import java.time.Duration;

public class Main {
    private static String url = "https://qatest-28flsd5.meshmd.com/SignIn?r=%2F";

    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver", "c:\\tools\\chromedriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        try {
            driver.get(url);
            Thread.sleep(2000);
            LoginPage loginPage = new LoginPage(driver);
//            loginPage.btnSignin.click();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }

//    private static void validationClick(WebElement elem) {
//        if (driver.getCurrentUrl()!=url) {
//            driver.get(url);
//        }
//        elem.click();
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
//    }
}