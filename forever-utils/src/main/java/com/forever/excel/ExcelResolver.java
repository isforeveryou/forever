package com.forever.excel;

import com.forever.utils.OptionalBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author WJX
 * @date 2020/11/27 13:39
 */
public class ExcelResolver {

    public List<Map<String, String>> parseExcelWithRow(String excelPath, String sheetName, String... rowNames) throws IOException {
        return parseExcelWithRow(new File(excelPath), sheetName, true, rowNames);
    }

    public List<Map<String, String>> parseExcelWithRow(String excelPath, String sheetName, boolean outputEmpty, String... rowNames) throws IOException {
        return parseExcelWithRow(new File(excelPath), sheetName, outputEmpty, rowNames);
    }

    public List<Map<String, String>> parseExcelWithRow(InputStream excelStream, String sheetName, String... rowNames) throws IOException {
        return parseExcelWithRow(WorkbookFactory.create(excelStream), sheetName, true, rowNames);
    }

    public List<Map<String, String>> parseExcelWithRow(InputStream excelStream, String sheetName, boolean outputEmpty, String... rowNames) throws IOException {
        return parseExcelWithRow(WorkbookFactory.create(excelStream), sheetName, outputEmpty, rowNames);
    }

    public List<Map<String, String>> parseExcelWithRow(File excelFile, String sheetName, String... rowNames) throws IOException {
        return parseExcelWithRow(WorkbookFactory.create(excelFile), sheetName, true, rowNames);
    }

    public List<Map<String, String>> parseExcelWithRow(File excelFile, String sheetName, boolean outputEmpty, String... rowNames) throws IOException {
        return parseExcelWithRow(WorkbookFactory.create(excelFile), sheetName, outputEmpty, rowNames);
    }

    public List<Map<String, String>> parseExcelWithRow(Workbook workbook, String sheetName, boolean outputEmpty, String... rowNames) throws IOException {

        if (rowNames == null || rowNames.length <= 0) {
            return new ArrayList<>(0);
        }

        Sheet sheet = Optional.of(workbook.getSheet(sheetName)).orElseThrow(() -> new RuntimeException("no such sheet name:" + sheetName));

        Map<String, Integer> indexMap = new HashMap<>(rowNames.length);

        for (String row : rowNames) {
            indexMap.put(row.trim(), -1);
        }

        int column = getRowIndex(sheet, indexMap);

        return column >= 0 ? readColumnValue(sheet, indexMap, outputEmpty, column + 1) : new ArrayList<>(0);
    }





    public List<Map<String, String>> parseExcelWithRow(String excelPath, String sheetName, Integer... rowIndexes) throws IOException {
        return parseExcelWithRow(new File(excelPath), sheetName, 1, true, rowIndexes);
    }

    public List<Map<String, String>> parseExcelWithRow(String excelPath, String sheetName, boolean outputEmpty, Integer... rowIndexes) throws IOException {
        return parseExcelWithRow(new File(excelPath), sheetName, 1, outputEmpty, rowIndexes);
    }

    public List<Map<String, String>> parseExcelWithRow(String excelPath, String sheetName, Integer columnIndex, boolean outputEmpty, Integer... rowIndexes) throws IOException {
        return parseExcelWithRow(new File(excelPath), sheetName, columnIndex, outputEmpty, rowIndexes);
    }

    public List<Map<String, String>> parseExcelWithRow(InputStream excelStream, String sheetName, Integer... rowIndexes) throws IOException {
        return parseExcelWithRow(WorkbookFactory.create(excelStream), sheetName, 1, true, rowIndexes);
    }

    public List<Map<String, String>> parseExcelWithRow(InputStream excelStream, String sheetName, boolean outputEmpty, Integer... rowIndexes) throws IOException {
        return parseExcelWithRow(WorkbookFactory.create(excelStream), sheetName, 1, outputEmpty, rowIndexes);
    }

    public List<Map<String, String>> parseExcelWithRow(InputStream excelStream, String sheetName, Integer columnIndex, boolean outputEmpty, Integer... rowIndexes) throws IOException {
        return parseExcelWithRow(WorkbookFactory.create(excelStream), sheetName, columnIndex, outputEmpty, rowIndexes);
    }

    public List<Map<String, String>> parseExcelWithRow(File excelFile, String sheetName, Integer... rowIndexes) throws IOException {
        return parseExcelWithRow(WorkbookFactory.create(excelFile), sheetName, 1, true, rowIndexes);
    }

    public List<Map<String, String>> parseExcelWithRow(File excelFile, String sheetName, boolean outputEmpty, Integer... rowIndexes) throws IOException {
        return parseExcelWithRow(WorkbookFactory.create(excelFile), sheetName, 1, outputEmpty, rowIndexes);
    }

    public List<Map<String, String>> parseExcelWithRow(File excelFile, String sheetName, Integer columnIndex, boolean outputEmpty, Integer... rowIndexes) throws IOException {
        return parseExcelWithRow(WorkbookFactory.create(excelFile), sheetName, columnIndex, outputEmpty, rowIndexes);
    }

    public List<Map<String, String>> parseExcelWithRow(Workbook workbook, String sheetName, Integer columnIndex, boolean outputEmpty, Integer... rowIndexes) throws IOException {

        if (rowIndexes == null || rowIndexes.length <= 0) {
            return new ArrayList<>(0);
        }

        Sheet sheet = Optional.of(workbook.getSheet(sheetName)).orElseThrow(() -> new RuntimeException("no such sheet name:" + sheetName));

        Map<String, Integer> indexMap = new HashMap<>(rowIndexes.length);

        for (Integer rowIndex : rowIndexes) {
            indexMap.put(String.valueOf(rowIndex), rowIndex - 1);
        }

        return readColumnValue(sheet, indexMap, outputEmpty, columnIndex - 1);
    }






    public List<Map<String, String>> parseExcelWithColumn(String excelPath, String sheetName, String... columnNames) throws IOException {
        return parseExcelWithColumn(new File(excelPath), sheetName, true, columnNames);
    }

    public List<Map<String, String>> parseExcelWithColumn(String excelPath, String sheetName, boolean outputEmpty, String... columnNames) throws IOException {
        return parseExcelWithColumn(new File(excelPath), sheetName, outputEmpty, columnNames);
    }

    public List<Map<String, String>> parseExcelWithColumn(InputStream excelStream, String sheetName, String... columnNames) throws IOException {
        return parseExcelWithColumn(WorkbookFactory.create(excelStream), sheetName, true, columnNames);
    }

    public List<Map<String, String>> parseExcelWithColumn(InputStream excelStream, String sheetName, boolean outputEmpty, String... columnNames) throws IOException {
        return parseExcelWithColumn(WorkbookFactory.create(excelStream), sheetName, outputEmpty, columnNames);
    }

    public List<Map<String, String>> parseExcelWithColumn(File excelFile, String sheetName, String... columnNames) throws IOException {
        return parseExcelWithColumn(WorkbookFactory.create(excelFile), sheetName, true, columnNames);
    }

    public List<Map<String, String>> parseExcelWithColumn(File excelFile, String sheetName, boolean outputEmpty, String... columnNames) throws IOException {
        return parseExcelWithColumn(WorkbookFactory.create(excelFile), sheetName, outputEmpty, columnNames);
    }

    public List<Map<String, String>> parseExcelWithColumn(Workbook workbook, String sheetName, boolean outputEmpty, String... columnNames) {

        if (columnNames == null || columnNames.length <= 0) {
            return new ArrayList<>(0);
        }

        Sheet sheet = Optional.of(workbook.getSheet(sheetName)).orElseThrow(() -> new RuntimeException("no such sheet name:" + sheetName));

        Map<String, Integer> indexMap = new HashMap<>(columnNames.length);

        for (String column : columnNames) {
            indexMap.put(column.trim(), -1);
        }

        int rowNumbers = sheet.getLastRowNum() + 1;
        for (int row = 0; row < rowNumbers; row++) {
            if (getColumnIndex(sheet.getRow(row), indexMap)) {
                return readRowValue(sheet, indexMap, outputEmpty,row + 1);
            }
        }

        return new ArrayList<>(0);
    }






    public List<Map<String, String>> parseExcelWithColumn(String excelPath, String sheetName, Integer... columnIndexes) throws IOException {
        return parseExcelWithColumn(new File(excelPath), sheetName, 1, true, columnIndexes);
    }

    public List<Map<String, String>> parseExcelWithColumn(String excelPath, String sheetName, boolean outputEmpty, Integer... columnIndexes) throws IOException {
        return parseExcelWithColumn(new File(excelPath), sheetName, 1, outputEmpty, columnIndexes);
    }

    public List<Map<String, String>> parseExcelWithColumn(String excelPath, String sheetName, Integer rowIndex, boolean outputEmpty, Integer... columnIndexes) throws IOException {
        return parseExcelWithColumn(new File(excelPath), sheetName, rowIndex, outputEmpty, columnIndexes);
    }

    public List<Map<String, String>> parseExcelWithColumn(File excelFile, String sheetName, Integer... columnIndexes) throws IOException {
        return parseExcelWithColumn(WorkbookFactory.create(excelFile), sheetName, 1, true, columnIndexes);
    }

    public List<Map<String, String>> parseExcelWithColumn(File excelFile, String sheetName, boolean outputEmpty, Integer... columnIndexes) throws IOException {
        return parseExcelWithColumn(WorkbookFactory.create(excelFile), sheetName, 1, outputEmpty, columnIndexes);
    }

    public List<Map<String, String>> parseExcelWithColumn(File excelFile, String sheetName, Integer rowIndex, boolean outputEmpty, Integer... columnIndexes) throws IOException {
        return parseExcelWithColumn(WorkbookFactory.create(excelFile), sheetName, rowIndex, outputEmpty, columnIndexes);
    }

    public List<Map<String, String>> parseExcelWithColumn(InputStream excelStream, String sheetName, Integer... columnIndexes) throws IOException {
        return parseExcelWithColumn(WorkbookFactory.create(excelStream), sheetName, 1, true, columnIndexes);
    }

    public List<Map<String, String>> parseExcelWithColumn(InputStream excelStream, String sheetName, boolean outputEmpty, Integer... columnIndexes) throws IOException {
        return parseExcelWithColumn(WorkbookFactory.create(excelStream), sheetName, 1, outputEmpty, columnIndexes);
    }

    public List<Map<String, String>> parseExcelWithColumn(InputStream excelStream, String sheetName, Integer rowIndex, boolean outputEmpty, Integer... columnIndexes) throws IOException {
        return parseExcelWithColumn(WorkbookFactory.create(excelStream), sheetName, rowIndex, outputEmpty, columnIndexes);
    }

    public List<Map<String, String>> parseExcelWithColumn(Workbook workbook, String sheetName, Integer rowIndex, boolean outputEmpty, Integer... columnIndexes) {

        if (columnIndexes == null || columnIndexes.length <= 0) {
            return new ArrayList<>(0);
        }

        Sheet sheet = Optional.of(workbook.getSheet(sheetName)).orElseThrow(() -> new RuntimeException("no such sheet name:" + sheetName));

        Map<String, Integer> indexMap = new HashMap<>(columnIndexes.length);

        for (Integer columnIndex : columnIndexes) {
            indexMap.put(String.valueOf(columnIndex), columnIndex - 1);
        }

        return readRowValue(sheet, indexMap, outputEmpty, rowIndex - 1);
    }



    /**
     * 读取行数据
     */
    private List<Map<String, String>> readRowValue(Sheet sheet, Map<String, Integer> columnIndexMap, boolean outputEmpty, int rowIndex) {
        List<Map<String,String>> results = new ArrayList<>();

        int rowNumbers = sheet.getLastRowNum() + 1;
        for (; rowIndex < rowNumbers; rowIndex++) {
            Row temp = sheet.getRow(rowIndex);

            if (temp == null) {
                continue;
            }

            boolean isEmpty = true;
            Map<String, String> result = new HashMap<>(columnIndexMap.size());
            for (Map.Entry<String, Integer> entry : columnIndexMap.entrySet()) {
                String value = getCellStringValue(temp.getCell(entry.getValue()));

                result.put(entry.getKey(), value);
                isEmpty = isEmpty && StringUtils.isBlank(value);
            }

            boolean finalIsEmpty = isEmpty;
            OptionalBean.ofNullable(result).ifPresent(() -> !finalIsEmpty || outputEmpty, results::add);
        }
        return results;
    }


    /**
     * 读取列数据
     */
    private List<Map<String, String>> readColumnValue(Sheet sheet, Map<String, Integer> rowIndexMap, boolean outputEmpty, int columnIndex) {

        int columns = columnIndex + 1;
        List<Map<String,String>> results = new ArrayList<>();

        for (; columnIndex < columns; columnIndex++) {
            boolean isEmpty = true;
            Map<String, String> result = new HashMap<>();

            for (Map.Entry<String, Integer> entry : rowIndexMap.entrySet()) {
                Row temp = sheet.getRow(entry.getValue());
                if (temp != null) {
                    columns = Math.max(temp.getPhysicalNumberOfCells(), columns);
                    String value = getCellStringValue(temp.getCell(columnIndex));

                    result.put(entry.getKey(), value);
                    isEmpty = isEmpty && StringUtils.isBlank(value);
                }
            }

            boolean finalIsEmpty = isEmpty;
            OptionalBean.ofNullable(result).ifPresent(() -> !finalIsEmpty || outputEmpty, results::add);
        }

        return results;
    }


    /**
     * 获取行下标
     */
    private Integer getRowIndex(Sheet sheet, Map<String, Integer> indexMap) {

        int columnIndex = -1;
        int rowNumbers = sheet.getLastRowNum() + 1;

        for (int row = 0; row < rowNumbers; row++) {
            Row temp = sheet.getRow(row);
            int cells = temp == null ? 0 : temp.getPhysicalNumberOfCells();

            if (cells > columnIndex && cells > 0 && columnIndex > -1) {
                String cellValue = getCellStringValue(temp.getCell(columnIndex)).trim();

                if (indexMap.get(cellValue) != null) {
                    indexMap.put(cellValue, row);
                }

                continue;
            }

            for (int col = 0; col < cells; col++) {
                Cell cell = temp.getCell(col);
                String cellValue = getCellStringValue(cell).trim();

                if (indexMap.get(cellValue) != null) {
                    columnIndex = col;
                    indexMap.put(cellValue, row);
                }
            }
        }

        return columnIndex;
    }


    /**
     * 获取列下标
     */
    private boolean getColumnIndex(Row temp, Map<String, Integer> indexMap) {

        int cells = temp == null ? 0 : temp.getPhysicalNumberOfCells();

        // 遍历每列
        for (int col = 0; col < cells; col++) {
            Cell cell = temp.getCell(col);
            String cellValue = getCellStringValue(cell).trim();

            if (indexMap.get(cellValue) != null) {
                indexMap.put(cellValue, col);
            }
        }

        return !indexMap.containsValue(-1);
    }


    /**
     *  获取cell中的字符串
     */
    private String getCellStringValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING: return cell.getStringCellValue();
            case ERROR: return String.valueOf(cell.getErrorCellValue());
            case BOOLEAN: return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                try {
                    String numeric =  String.valueOf(cell.getNumericCellValue());
                    return numeric.indexOf(".") > 0 ? numeric.replaceAll("0+?$", "").replaceAll("[.]$", "") : numeric;
                } catch (IllegalStateException e) {
                    return String.valueOf(cell.getRichStringCellValue());
                }
            case NUMERIC:
                String numeric =  String.valueOf(cell.getNumericCellValue());
                return numeric.indexOf(".") > 0 ? numeric.replaceAll("0+?$", "").replaceAll("[.]$", "") : numeric;
            default: return "";
        }
    }


}
