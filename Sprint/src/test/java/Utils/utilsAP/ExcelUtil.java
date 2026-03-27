package utils;

import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;
//for reading the excel files
public class ExcelUtil {

    public static String[][] readExcel(String path) throws Exception {

        FileInputStream fis = new FileInputStream(path);
        Workbook wb = WorkbookFactory.create(fis);
        Sheet sheet = wb.getSheetAt(0);

        int rows = sheet.getPhysicalNumberOfRows();
        int cols = sheet.getRow(0).getPhysicalNumberOfCells();

        String[][] data = new String[rows][cols];

        for (int i = 0; i < rows; i++) {
            Row row = sheet.getRow(i);
            for (int j = 0; j < cols; j++) {
                data[i][j] = row.getCell(j).toString();
            }
        }

        wb.close();
        return data;
    }
}