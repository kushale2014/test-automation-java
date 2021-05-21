package pages;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import helper.Log;

import static helper.EmailValidation.validateEmailAddress;
import static org.apache.poi.ss.usermodel.CellType.BLANK;

public class RegisterPage {
    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        iterator = 0;
        listElem = Arrays.asList(
                "usertype",
                "first",
                "last",
                "email",
                "email_verify",
                "cpso",
                "specialty",
                "birthdate",
                "gender",
                "language",
                "prov"
        );
    }

    private WebDriver driver;
    private Map<String, String>  mapData = new HashMap<String, String>();

    private ArrayList<String> listFirstName = new ArrayList<String>();
    private ArrayList<String> listLastName = new ArrayList<String>();
    private ArrayList<String> listLicense = new ArrayList<String>();
    private ArrayList<String> listEmail = new ArrayList<String>();
    private List<String> listElem;
    private String test;
    private int iterator;

    public void inputDataFromExcel() throws Exception {
        test = "Test2";
        getData_2();
        select("usertype");
        input("first");
        input("last");
        input("email");
        input("email_verify");
        input_js("cpso");
        select("specialty");
        input("birthdate");
        select("gender");
        select("language");
        select("prov");
    }

    public void validateFields() throws Exception {
        test = "Test3";
        mapData.put("usertype", "Physician");
        select("usertype");

        getData_3();
        for (String firstName : listFirstName) {
            mapData.put("first", firstName);
            input("first");
        }
        for (String lastName : listLastName) {
            mapData.put("last", lastName);
            input("last");
        }
        for (String license : listLicense) {
            mapData.put("cpso", license);
            input("cpso");
        }

        for (String email : listEmail) {
            boolean isValid = validateEmailAddress(email);
            if (isValid) Log.i(email, "Pass");
            else Log.e(email, "Fail");
        }

        mapData.put("birthdate", "1976-04-11");
        setDate("birthdate");
    }

    private void setDate(String idElem) throws Exception {
        String value = mapData.get(idElem);
        if (value.isEmpty()) return;
        WebElement elem = driver.findElement(By.id(idElem));
        elem.click();

        String[] date = value.split("-");
        int inputPeriod = Integer.parseInt(date[0] + date[1]);
        int day = Integer.parseInt(date[2]);

        while (getCalendarPeriod() < inputPeriod ) {
            driver.findElement(By.cssSelector("a[title='Next']")).click();
        }
        while (getCalendarPeriod() > inputPeriod ) {
            driver.findElement(By.cssSelector("a[title='Prev']")).click();
        }
        WebElement dayElem = driver.findElement(By.xpath("//a[text()=" + day + "]"));
        dayElem.click();
        saveResult(idElem);
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
        String value = mapData.get(idElem);
        Select drpSelect = new Select(driver.findElement(By.id(idElem)));
        drpSelect.selectByVisibleText(value);
        saveResult(idElem);
    }

    private void input(String idElem) throws Exception {
        String value = mapData.get(idElem);
        WebElement inpElem = driver.findElement(By.id(idElem));
        inpElem.clear();
        int x = (new Random()).nextInt(2);
        inpElem.sendKeys(value);
        saveResult(idElem);
    }

    private void input_js(String idElem) throws Exception {
        String value = mapData.get(idElem);
        WebElement inpElem = driver.findElement(By.id(idElem));
        inpElem.clear();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value='" + value + "';", inpElem);
        saveResult(idElem);
    }

    private void saveResult(String idElem) throws Exception {
        clickNextElem(idElem);

        WebElement labelElem = driver.findElement(By.xpath("//*[@id='" + idElem+ "']/.."));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", labelElem);
        Thread.sleep(200);

        Boolean isErrors = ! driver.findElements(By.xpath("//*[@id='" + idElem + "']/../span[@class='formerror']")).isEmpty();
        if (isErrors) Log.e(idElem,"Errors");
        else Log.i(idElem, "not Errors");

        iterator++;
        FileUtils.copyFile(
                captureElementBitmap(driver, labelElem),
                new File("src\\main\\resources\\" + test + "\\" + iterator + "-" + idElem + ".png")
        );
    }

    private void clickNextElem(String idElem) {
        int nextIndex = listElem.indexOf(idElem)+1;
        WebElement nextEl = null;
        if (nextIndex < listElem.size()) {
            String nextIdElem = (String) listElem.get(nextIndex);
            nextEl = driver.findElement(By.xpath("//*[@id='" + nextIdElem+  "']/.."));
        } else {
            nextEl = driver.findElement(By.xpath("//div[@class='separator adjust-top']"));
        }
        nextEl.click();
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
        int dx = 10;
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int dy = ( (Long) (js.executeScript("return window.pageYOffset;"))).intValue();
        BufferedImage dest = img.getSubimage(p.getX()-dx, p.getY()-dy, rect.width+2*dx, rect.height + 10);
        // Перезаписываем File screen
        ImageIO.write(dest, "png", screen);
        // Возвращаем File c изображением элемента
        return screen;
    }


    private void getData_2() throws IOException {
        String excelFilePath = "src\\main\\resources\\2.2.data.xlsx";
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);

        Row row = firstSheet.getRow(1);
        Cell cell;

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

    }


    private void getData_3() throws IOException {
        String excelFilePath = "src\\main\\resources\\2.3.data.xlsx";
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);

        for (int j=1; ;j++) {
            Row row = firstSheet.getRow(j);
            if (row == null) break;
            int i;
            i = 0; if (row.getCell(i) != null && row.getCell(i).getCellType() != BLANK) listFirstName.add(row.getCell(i+1).getStringCellValue());
            i = 3; if (row.getCell(i) != null && row.getCell(i).getCellType() != BLANK) listLastName.add(row.getCell(i+1).getStringCellValue());
            i = 6; if (row.getCell(i) != null && row.getCell(i).getCellType() != BLANK) listLicense.add(row.getCell(i+1).getStringCellValue());
            i = 9; if (row.getCell(i) != null && row.getCell(i).getCellType() != BLANK) listEmail.add(row.getCell(i+1).getStringCellValue());
        }

        workbook.close();
        inputStream.close();
    }

}

