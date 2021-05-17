import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ScreenshotDemo {
    private String baseUrl;
    private WebDriver driver;

    @Test
    public void f() throws IOException,InterruptedException {
        driver.get(baseUrl);

        //take screenshot of the page and save it as FILE type
        File scrshot=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

        //copy it in D:\\
        FileUtils.copyFile(scrshot, new File("screenshot.png"));

        //find the specific webelement whom we want the screenshot
        WebElement e=driver.findElement(By.xpath("//*[@name='email']/.."));

        Point p= e.getLocation();

        //get height and width of element
        int h=e.getSize().getHeight();
        int w=e.getSize().getWidth();

        System.out.println(h);
        System.out.println(w);

        BufferedImage img= ImageIO.read(scrshot);

        //crop image using the height and width dimensions
        BufferedImage finalImg=img.getSubimage(p.getX(), p.getY(), w, h);

        ImageIO.write(finalImg, "png", scrshot);

        File f1=new File("element.png");
        FileUtils.copyFile(scrshot, f1);

    }

    @BeforeTest
    public void beforeTest() {
        System.setProperty("webdriver.chrome.driver", "c:\\tools\\chromedriver\\chromedriver.exe");
        driver = new ChromeDriver();
        baseUrl="https://qatest-28flsd5.meshmd.com/register";
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    @AfterTest
    public void afterTest() {
        driver.quit();
    }

}