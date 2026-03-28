package Utils;

import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;

public class ExcelUtilAP {

    public static String[][] readExcel(String path, String sheetName) {

        try (FileInputStream fis = new FileInputStream(path);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);

            int rowCount = sheet.getPhysicalNumberOfRows();
            int colCount = sheet.getRow(0).getPhysicalNumberOfCells();

            String[][] data = new String[rowCount - 1][colCount]; // skip header

            for (int i = 1; i < rowCount; i++) {   // start from 1 (skip header)
                Row row = sheet.getRow(i);

                for (int j = 0; j < colCount; j++) {
                    Cell cell = row.getCell(j);
                    data[i - 1][j] = getCellValue(cell);
                }
            }

            return data;

        } catch (Exception e) {
            throw new RuntimeException("Error reading Excel: " + e.getMessage());
        }
    }

    private static String getCellValue(Cell cell) {

        if (cell == null) return "";

        switch (cell.getCellType()) {

            case STRING:
                return cell.getStringCellValue();

            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());

            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());

            case FORMULA:
                return cell.getCellFormula();

            default:
                return "";
        }
    }
}