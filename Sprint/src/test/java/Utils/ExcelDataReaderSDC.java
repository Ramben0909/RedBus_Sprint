package Utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility to read test data from Excel (.xlsx) files.
 *
 * Usage:
 *   List<Map<String,String>> rows = ExcelDataReader.getSheetData("BusSearch");
 *   String source = rows.get(0).get("source");
 */
public class ExcelDataReaderSDC {

    // Path relative to project root — place redbus_test_data.xlsx in src/test/resources/testdata/
    private static final String FILE_PATH = "src/test/resources/Data/redbus_test_data_sdc.xlsx";

    /**
     * Reads all rows from the given sheet and returns them as a list of maps.
     * Map keys = header column names (row 1), values = cell values as Strings.
     *
     * @param sheetName  exact name of the Excel sheet tab
     * @return           list of row data maps (one map per data row, header row excluded)
     */
    public static List<Map<String, String>> getSheetData(String sheetName) {
        List<Map<String, String>> data = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new RuntimeException("Sheet not found: " + sheetName);
            }

            Row headerRow = sheet.getRow(0);
            int colCount = headerRow.getLastCellNum();

            // Build header list from row 0
            List<String> headers = new ArrayList<>();
            for (int c = 0; c < colCount; c++) {
                headers.add(getCellValue(headerRow.getCell(c)));
            }

            // Read data rows (skip row 0 which is the header)
            for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) continue;

                Map<String, String> rowMap = new LinkedHashMap<>();
                for (int c = 0; c < colCount; c++) {
                    rowMap.put(headers.get(c), getCellValue(row.getCell(c)));
                }
                data.add(rowMap);
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to read Excel file: " + FILE_PATH, e);
        }

        return data;
    }



     static String getCellValue(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:  return cell.getStringCellValue().trim();
            case NUMERIC:
                // Avoid scientific notation for plain numbers (e.g. day "27")
                double d = cell.getNumericCellValue();
                return (d == Math.floor(d)) ? String.valueOf((long) d) : String.valueOf(d);
            case BOOLEAN: return String.valueOf(cell.getBooleanCellValue());
            case FORMULA: return cell.getCellFormula();
            default:      return "";
        }
    }
}
