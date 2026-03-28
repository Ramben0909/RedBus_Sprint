package Utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtils_trainstatus {

    private static final String FILE_PATH =
        "src/test/resources/Data/TrainTestData_trainstatus.xlsx";

    public static List<String[]> getSheetData(String sheetName) {

        List<String[]> data = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new RuntimeException("Sheet '" + sheetName + "' not found!");
            }

            int totalRows    = sheet.getPhysicalNumberOfRows();
            int totalColumns = sheet.getRow(0).getPhysicalNumberOfCells();

            // Row 0 is header — start from row 1
            for (int rowIdx = 1; rowIdx < totalRows; rowIdx++) {
                Row row = sheet.getRow(rowIdx);
                if (row == null) continue;

                String[] rowData = new String[totalColumns];

                for (int colIdx = 0; colIdx < totalColumns; colIdx++) {
                    Cell cell = row.getCell(colIdx);
                    rowData[colIdx] = (cell == null) ? "" : getCellValueAsString(cell);
                }
                data.add(rowData);
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to read Excel: " + FILE_PATH, e);
        }

        return data;
    }

    private static String getCellValueAsString(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:  return cell.getStringCellValue().trim();
            case NUMERIC: return String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN: return String.valueOf(cell.getBooleanCellValue());
            case FORMULA: return cell.getCellFormula();
            default:      return "";
        }
    }
}