package pages;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.apache.poi.ss.usermodel.CellType.BLANK;

public class RegisterPage {
    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    private WebDriver driver;
    Map<String, String>  mapData;

    public void inputDataFromExcel() throws IOException {
        mapData = getData(1);
//        mapData.forEach((k,v) -> System.out.println(k + " = " + v));
//        setUserType(mapData.get("userType"));
//        setInputs();
        String[] inputs = {
                "usertype:S",
                "first:T",
                "last:T",
                "email:T",
                "email_verify:T",
                "cpso:T",
                "specialty:S",
                "birthdate:T",
                "gender:S",
                "language:S",
                "prov:S"
        };
        for (String input : inputs) {
            String[] str = input.split(":");
//            System.out.println(str[0] + " = " + str[1] + " - " + mapData.get(str[0]));
            if ((str[1] == "S")) setSelect(str[0]);
            else setInput(str[0]);
        }
    }

    public void setSelect(String elem) {
        Select drpSelect = new Select(driver.findElement(By.id(elem)));
        drpSelect.selectByVisibleText(elem);
    }

    public void setInput(String elem) {
        WebElement inpElem = driver.findElement(By.id(elem));
        inpElem.sendKeys(mapData.get(elem));
    }

    public void setInputs() {
        String[] inputs = {"first", "last", "email", "email_verify", "cpso"};
        for (String input : inputs) {
            WebElement elem = driver.findElement(By.id(input));
            elem.sendKeys(mapData.get(input));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setUserType(String userType) {
        Select drpUserType = new Select(driver.findElement(By.id("usertype")));
        drpUserType.selectByVisibleText(userType);
    }

    private Map<String, String> getData(int nRow) throws IOException {
        String excelFilePath = "src\\main\\resources\\2.2.data.xlsx";
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

        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
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
