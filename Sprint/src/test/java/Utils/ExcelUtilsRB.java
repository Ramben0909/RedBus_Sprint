package Utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtilsRB {

    private static final String FILE_PATH =
        "src/test/resources/TestData/TrainSearchData.xlsx";

    @SuppressWarnings("resource")
    public static List<String[]> getTestData(String filePath,
                                              String sheetName) throws IOException {
        List<String[]> data = new ArrayList<>();
        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook   = new XSSFWorkbook(fis);
        Sheet sheet         = workbook.getSheet(sheetName);

        if (sheet == null)
            throw new RuntimeException("Sheet not found: " + sheetName);

        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;
            String[] rowData = new String[row.getLastCellNum()];
            for (int j = 0; j < row.getLastCellNum(); j++) {
                Cell cell  = row.getCell(j);
                rowData[j] = cell == null ? "" : cell.toString().trim();
            }
            data.add(rowData);
        }
        workbook.close();
        fis.close();
        return data;
    }

    // ── fetch specific row by data_id ──────────────────────────────────────────

    public static String[] getRowById(String sheetName,
                                       String dataId) throws IOException {
        List<String[]> all = getTestData(FILE_PATH, sheetName);

        for (int i = 1; i < all.size(); i++) {
            String[] row = all.get(i);
            if (row == null || row[0].trim().isEmpty()) continue;

            if (row[0].trim().equalsIgnoreCase(dataId.trim())) {
                System.out.println("Fetched [" + dataId + "] → "
                    + java.util.Arrays.toString(row));
                return row;
            }
        }
        throw new RuntimeException(
            "data_id '" + dataId + "' not found in sheet: " + sheetName);
    }

    // ── existing methods kept ──────────────────────────────────────────────────

    public static List<String[]> getValidSearchRows() throws IOException {
        return getDataRows(FILE_PATH, "ValidSearch");
    }

    public static List<String[]> getDateSearchRows() throws IOException {
        return getDataRows(FILE_PATH, "DateSearch");
    }

    public static List<String[]> getInvalidSearchRows() throws IOException {
        return getDataRows(FILE_PATH, "InvalidSearch");
    }

    private static List<String[]> getDataRows(String filePath,
                                               String sheetName) throws IOException {
        List<String[]> all      = getTestData(filePath, sheetName);
        List<String[]> filtered = new ArrayList<>();
        for (int i = 1; i < all.size(); i++) {
            String[] row = all.get(i);
            if (row == null || row[0].trim().isEmpty()) continue;
            filtered.add(row);
        }
        return filtered;
    }
}