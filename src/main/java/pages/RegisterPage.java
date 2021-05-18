package pages;

import io.netty.handler.codec.http.QueryStringDecoder;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Enum.valueOf;
import static org.apache.poi.ss.usermodel.CellType.BLANK;

public class RegisterPage {
    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    private WebDriver driver;
    Map<String, String>  mapData;

    public void inputDataFromExcel() throws Exception {
        mapData = getData(1);

//        select("usertype");
//        input("first");
//        input("last");
//        input("email");
//        input("email_verify");
//        input("cpso");
//        select("specialty");
        setDate("birthdate");
//        select("gender");
//        select("language");
//        select("prov");

    }

    private void setDate(String idElem) throws InterruptedException, ParseException {
        String birthday = mapData.get(idElem);
        if (birthday.isEmpty()) return;
        WebElement elem = driver.findElement(By.id(idElem));
        elem.click();

        String[] date = mapData.get(idElem).split("-");
        int inputPeriod = Integer.parseInt(date[0] + date[1]);
        int day = Integer.parseInt(date[2]);

        while (getCalendarPeriod() < inputPeriod ) {
            driver.findElement(By.cssSelector("a[title='Next']")).click();
        }
        while (getCalendarPeriod() > inputPeriod ) {
            driver.findElement(By.cssSelector("a[title='Prev']")).click();
        }
    }

    private int getCalendarPeriod() throws ParseException {
        String calendarYear = driver.findElement(By.cssSelector("span.ui-datepicker-year")).getText();
        String calendarMes = driver.findElement(By.cssSelector("span.ui-datepicker-month")).getText();

        SimpleDateFormat formatStringMonth = new SimpleDateFormat("yyyy-MMMM", Locale.ENGLISH);
        SimpleDateFormat formatIntMonth = new SimpleDateFormat("yyyyMM");
        String calendarPeriod = formatIntMonth.format(formatStringMonth.parse(calendarYear + "-" + calendarMes));

        return Integer.parseInt(calendarPeriod);
    }

    private void select(String idElem) throws Exception {
        Select drpSelect = new Select(driver.findElement(By.id(idElem)));
        drpSelect.selectByVisibleText(mapData.get(idElem));
        saveScreenshot(idElem);
    }

    private void input(String idElem) throws Exception {
        WebElement inpElem = driver.findElement(By.id(idElem));
        inpElem.sendKeys(mapData.get(idElem));
        saveScreenshot(idElem);
    }

    private void saveScreenshot(String idElem) throws Exception {
        driver.findElement(By.cssSelector("h3")).click();
        WebElement label = driver.findElement(By.xpath("//*[@name='" + idElem+ "']/.."));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", label);
        Thread.sleep(500);

        FileUtils.copyFile(captureElementBitmap(driver, label), new File("src\\main\\resources\\" + idElem + ".png"));
    }

    private File captureElementBitmap(WebDriver driver, WebElement element) throws Exception {
        // Делаем скриншот страницы
        File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        // Создаем экземпляр BufferedImage для работы с изображением
        BufferedImage img = ImageIO.read(screen);
        // Создаем прямоуголник (Rectangle) с размерами элемента
        Rectangle rect = new Rectangle(0, 0, element.getSize().getHeight(), element.getSize().getWidth());
        // Получаем координаты элемента
        Point p = element.getLocation();
        // Вырезаем изображенеи элемента из общего изображения
        int dx = 10; int dy = 10;
        BufferedImage dest = img.getSubimage(p.getX()-dx, p.getY(), rect.width+2*dx, rect.height + dy);
        // Перезаписываем File screen
        ImageIO.write(dest, "png", screen);
        // Возвращаем File c изображением элемента
        return screen;
    }


    private Map<String, String> getData(int nRow) throws IOException {
        String excelFilePath = "src\\main\\resources\\2.data.xlsx";
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);

        Row row = firstSheet.getRow(nRow);
        Cell cell;

        Map<String, String>  mapData = new HashMap<String, String>();
        mapData.put("usertype", row.getCell(1).getStringCellValue());
        mapData.put("first", row.getCell(2).getStringCellValue());
        mapData.put("last", row.getCell(3).getStringCellValue());

        String email = row.getCell(4).getStringCellValue();
        mapData.put("email", email);
        mapData.put("email_verify", email);

        mapData.put("cpso", row.getCell(5).getStringCellValue());
        mapData.put("specialty", row.getCell(6).getStringCellValue());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        cell = row.getCell(7);
        String birthday = (cell.getCellType() == BLANK) ?  "" : dateFormat.format(cell.getDateCellValue());
        mapData.put("birthdate", birthday);

        mapData.put("gender", row.getCell(8).getStringCellValue());
        mapData.put("language", row.getCell(9).getStringCellValue());
        mapData.put("prov", row.getCell(10).getStringCellValue());

        workbook.close();
        inputStream.close();

        return mapData;
    }
}
