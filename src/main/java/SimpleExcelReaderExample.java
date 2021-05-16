import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import static org.apache.poi.ss.usermodel.CellType.*;

public class SimpleExcelReaderExample {
    public static void main(String[] args){
        Map<String, String>  mapData = null;
        try {
            mapData = getDataFromExcel(2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mapData.forEach((k,v) -> System.out.println(k + " = " + v));

    }

    static Map<String, String> getDataFromExcel(int nRow) throws IOException {
        String excelFilePath = "src\\main\\resources\\2.2.data.xlsx";
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);

        Row row = firstSheet.getRow(nRow);
        Cell cell;

        Map<String, String>  mapData = new HashMap<String, String>();
        mapData.put("userType", row.getCell(1).getStringCellValue());
        mapData.put("firstName", row.getCell(2).getStringCellValue());
        mapData.put("lastName", row.getCell(3).getStringCellValue());
        mapData.put("email", row.getCell(4).getStringCellValue());

        mapData.put("licenseNumber", row.getCell(5).getStringCellValue());
        mapData.put("speciality", row.getCell(6).getStringCellValue());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
        cell = row.getCell(7);
        String birthday = (cell.getCellType() == BLANK) ?  "" : dateFormat.format(cell.getDateCellValue());
        mapData.put("birthday", birthday);

        mapData.put("gender", row.getCell(8).getStringCellValue());
        mapData.put("language", row.getCell(9).getStringCellValue());
        mapData.put("province", row.getCell(10).getStringCellValue());

        workbook.close();
        inputStream.close();

        return mapData;
    }

}
