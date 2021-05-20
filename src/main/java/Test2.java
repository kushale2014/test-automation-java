import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.apache.poi.ss.usermodel.CellType.BLANK;

public class Test2 {

    private ArrayList<String> listFirstName = new ArrayList<String>();
    private ArrayList<String> listLastName = new ArrayList<String>();
    private ArrayList<String> listLicense = new ArrayList<String>();

    @Test
    public void test2() throws IOException {
        System.out.println("Test 2");
        getData_3();

        for (String firstName : listFirstName) {
            System.out.println(firstName);
        }

        for (String lastName : listLastName) {
            System.out.println(lastName);
        }

        for (String license : listLicense) {
            System.out.println(license);
        }

//        list.add("usertype:S");
//        list.add("first:T");
//        list.add("last:T");
//        list.add("email:T");
//        list.add("email_verify:T");
//        list.add("cpso:T");
//        list.add("specialty:S");
//        list.add("birthdate:D");
//        list.add("gender:S");
//        list.add("language:S");
//        list.add("prov:S");
//
//        for (String ll : list) {
//            String[] str = ll.split(":");
//            String idElem = str[0];
//            String inputType = str[1];
//            saveResult(idElem);
//        }
    }

    private void saveResult(String idElem) {
//        int x = (new Random()).nextInt(2);
//        System.out.println(x);
        System.out.println(idElem);
    }

    private void getData_3() throws IOException {
        String excelFilePath = "src\\main\\resources\\2.3.data.xlsx";
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);

        for (int i=1; ;i++) {
            Row row = firstSheet.getRow(i);
            if (row == null) break;
            if (row.getCell(0) != null) listFirstName.add(row.getCell(1).getStringCellValue());
            if (row.getCell(3) != null) listLastName.add(row.getCell(4).getStringCellValue());
            if (row.getCell(6) != null) listLicense.add(row.getCell(7).getStringCellValue());
        }

        workbook.close();
        inputStream.close();

    }
}
