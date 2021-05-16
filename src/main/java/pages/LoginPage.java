package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import java.time.Duration;

public class LoginPage {
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    private WebDriver driver;

    public void verifyBtnSignin() {
        WebElement elem = driver.findElement((By.cssSelector("button[type='submit']")));
        elem.click();
        Assert.assertEquals(driver.findElements(By.cssSelector("span.field-validation-error")).isEmpty(), false);
    }

    public void verifyAllLinks(String startUrl) {
        verifyLink("a[href='/register']", startUrl);
        verifyLink("a[href='/passwordForgot']", startUrl);
        verifyLink("a[href='?lang=fr']", startUrl);
        verifyLink("a[href='?lang=en']", "https://qatest-28flsd5.meshmd.com/SignIn?lang=fr");
        verifyLink("a[href='/terms']", startUrl);
        verifyLink("a[href='/privacy']", startUrl);
    }

    public void verifyLink(String cssSelector, String startUrl) {
        if (driver.getCurrentUrl() != startUrl) {
            driver.get(startUrl);
        }
        WebElement elem = driver.findElement(By.cssSelector(cssSelector));
        String href = elem.getAttribute("href");
        elem.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        Assert.assertEquals(driver.getCurrentUrl().contains(href), true);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
