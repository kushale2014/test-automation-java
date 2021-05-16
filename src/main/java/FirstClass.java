import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import java.time.Duration;

public class FirstClass {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "c:\\tools\\chromedriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        try {
            driver.get("https://qatest-28flsd5.meshmd.com/SignIn?r=%2F");
            Thread.sleep(2000);
            String title = driver.getTitle();
            Assert.assertEquals(title, "Enrol Me™ | Sign In");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
