import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Locatable;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.Duration;

public class Screenshot {
    @Test
    public void elementScreenshotTest(){
        System.setProperty("webdriver.chrome.driver", "c:\\tools\\chromedriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        try {
            driver.get("https://qatest-28flsd5.meshmd.com/register");
            Thread.sleep(2000);

            driver.findElement(By.id("email")).sendKeys("test@test.com");
            driver.findElement(By.cssSelector("h3")).click();
            try {
                FileUtils.copyFile(captureElementBitmap(driver, driver.findElement(By.xpath("//*[@name='email']/.."))), new File("email.png"));

                WebElement element = driver.findElement(By.id("btn_submit"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
                Thread.sleep(2000);

                FileUtils.copyFile(captureElementBitmap(driver, driver.findElement(By.xpath("//*[@name='prov']/.."))), new File("prov.png"));
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    public static File captureElementBitmap(WebDriver driver, WebElement element) throws Exception {
        // Делаем скриншот страницы
        File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        // Создаем экземпляр BufferedImage для работы с изображением
        BufferedImage img = ImageIO.read(screen);
        // Создаем прямоуголник (Rectangle) с размерами элемента
        Rectangle rect = new Rectangle(0, 0, element.getSize().getHeight(), element.getSize().getWidth());
        // Получаем координаты элемента
//        Point p = element.getLocation();
        // Вырезаем изображенеи элемента из общего изображения
        Locatable elementLocation = (Locatable) element;
        Point p = elementLocation.getCoordinates().inViewPort();
        System.out.println(p);
        
        int dx = 10;
        System.out.println("x= " + (p.getX()-dx) + " y= " + p.getY() + " w= " + (rect.width+2*dx) + " h= " + rect.height);
        BufferedImage dest = img.getSubimage(p.getX()-dx, p.getY(), rect.width+2*dx, rect.height);
        // Перезаписываем File screen
        ImageIO.write(dest, "png", screen);
        // Возвращаем File c изображением элемента
        return screen;
    }

}
