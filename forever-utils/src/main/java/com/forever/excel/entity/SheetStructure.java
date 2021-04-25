package com.forever.excel.entity;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.*;

/**
 * @author WJX
 * @date 2020/12/17 10:56
 */
public class SheetStructure {

    private String sheetName;

    private Integer startRow;

    private Integer startColumn;

    private List<List<String>> cellValues = new ArrayList<>();

    private List<CellRangeAddress> merges = new ArrayList<>();

    private Map<String, Map<String, String>> styles = new HashMap<>();


    public SheetStructure(String sheetName) {
        this.startRow = 1;
        this.startColumn = 1;
        this.sheetName = sheetName;
    }

    public SheetStructure(String sheetName, int startRow, int startColumn) {
        this.sheetName = sheetName;

        this.startRow = startRow;
        this.startColumn = startColumn;
    }

    public String getSheetName() {
        return sheetName;
    }



    public void addRow(List<String> row) {
        this.cellValues.add(row);
    }

    @SafeVarargs
    public final void addRow(List<String>... rows) {
        this.cellValues.addAll(Arrays.asList(rows));
    }

    public Integer getStartRow() {
        return startRow == null ? 1 : startRow;
    }

    public Integer getEndRow() {
        return cellValues.size() + getStartRow() - 1;
    }

    public Integer getValidRowNum() {
        return cellValues.size();
    }

    public List<String> getRow(int rowIndex) {
        return cellValues.get(rowIndex - getStartRow());
    }



    public Integer getStartColumn() {
        return startColumn == null ? 1 : startColumn;
    }

    public Integer getEndColumn(Integer rowIndex) {
        return cellValues.get(rowIndex - getStartRow()).size() + getStartColumn() - 1;
    }

    public Integer getValidColumnNum(int rowIndex) {
        return cellValues.get(rowIndex - getStartRow()).size();
    }

    public String getColumn(int rowIndex, int columnIndex) {
        return cellValues.get(rowIndex - getStartRow()).get(columnIndex - getStartColumn());
    }



    public List<CellRangeAddress> getMerges() {
        return merges;
    }

    public void setMergeRow(int column, int firstRow, int lastRow) {
        setMerge(firstRow, lastRow, column, column);
    }

    public void setMergeColumn(int row, int firstColumn, int lastColumn) {
        setMerge(row, row, firstColumn, lastColumn);
    }

    public void setMerge(int firstRow, int lastRow, int firstColumn, int lastColumn) {
        merges.add(new CellRangeAddress(firstRow - 1, lastRow - 1, firstColumn - 1, lastColumn - 1));
    }



    private final static String SIZE = "1";
    private final static String BOLD = "2";
    private final static String LOCATION = "3";
    private final static String FONT_NAME = "4";
    private final static String FONT_COLOR = "5";
    private final static String GROUND_COLOR = "6";


    public void setFontStyle(int firstRow, int lastRow, int firstColumn, int lastColumn, short size) {
        setCellStyle(firstRow, lastRow, firstColumn, lastColumn,  "宋体", size, (short) 8, false, (short) 9, HorizontalAlignment.CENTER);
    }

    public void setFontStyle(int firstRow, int lastRow, int firstColumn, int lastColumn, boolean bold) {
        setCellStyle(firstRow, lastRow, firstColumn, lastColumn, "宋体", (short) 9, (short) 8, bold, (short) 9, HorizontalAlignment.CENTER);
    }

    public void setFontStyle(int firstRow, int lastRow, int firstColumn, int lastColumn, String fontName) {
        setCellStyle(firstRow, lastRow, firstColumn, lastColumn, fontName, (short) 9, (short) 8, false, (short) 9, HorizontalAlignment.CENTER);
    }

    public void setFontStyle(int firstRow, int lastRow, int firstColumn, int lastColumn, String fontName, short size) {
        setCellStyle(firstRow, lastRow, firstColumn, lastColumn, fontName, size, (short) 8, false, (short) 9, HorizontalAlignment.CENTER);
    }

    public void setFontStyle(int firstRow, int lastRow, int firstColumn, int lastColumn, HorizontalAlignment location) {
        setCellStyle(firstRow, lastRow, firstColumn, lastColumn, "宋体", (short) 9, (short) 8, false, (short) 9, location);
    }

    public void setFontStyle(int firstRow, int lastRow, int firstColumn, int lastColumn, boolean bold, HorizontalAlignment location) {
        setCellStyle(firstRow, lastRow, firstColumn, lastColumn, "宋体", (short) 9, (short) 8, bold, (short) 9, location);
    }

    public void setFontStyle(int firstRow, int lastRow, int firstColumn, int lastColumn, String fontName, short size,  boolean bold) {
        setCellStyle(firstRow, lastRow, firstColumn, lastColumn, fontName, size, (short) 8, bold, (short) 9, HorizontalAlignment.CENTER);
    }

    public void setFontStyle(int firstRow, int lastRow, int firstColumn, int lastColumn, short size, boolean bold, HorizontalAlignment location) {
        setCellStyle(firstRow, lastRow, firstColumn, lastColumn, "宋体", size, (short) 8, bold, (short) 9, location);
    }

    public void setFontStyle(int firstRow, int lastRow, int firstColumn, int lastColumn, String fontName, short size, short fontColor) {
        setCellStyle(firstRow, lastRow, firstColumn, lastColumn, fontName, size, fontColor, false, (short) 9, HorizontalAlignment.CENTER);
    }

    public void setCellStyle(int firstRow, int lastRow, int firstColumn, int lastColumn, short groundColor) {
        setCellStyle(firstRow, lastRow, firstColumn, lastColumn, "宋体", (short) 9, (short) 8, false, groundColor, HorizontalAlignment.CENTER);
    }

    public void setCellStyle(int firstRow, int lastRow, int firstColumn, int lastColumn, boolean bold, short groundColor) {
        setCellStyle(firstRow, lastRow, firstColumn, lastColumn, "宋体", (short) 9, (short) 8, bold, groundColor, HorizontalAlignment.CENTER);
    }

    public void setCellStyle(int firstRow, int lastRow, int firstColumn, int lastColumn, String fontName, short size, short fontColor, boolean bold, short groundColor, HorizontalAlignment location) {
        for (int row = firstRow; row <= lastRow; row ++) {
            for (int column = firstColumn; column <= lastColumn; column++) {
                Map<String, String> style = new HashMap<>(4);

                style.put(FONT_NAME, fontName);
                style.put(SIZE, String.valueOf(size));
                style.put(BOLD, String.valueOf(bold));
                style.put(FONT_COLOR, String.valueOf(fontColor));
                style.put(GROUND_COLOR, String.valueOf(groundColor));
                style.put(LOCATION, location.toString());

                styles.put((row - 1) + "-" + (column - 1), style);
            }
        }
    }


    public Map<String, String> getStyle(int row, int column) {
        String key = row + "-" + column;
        return styles.get(key) == null ? new HashMap<>() : styles.get(key);
    }

    public CellStyle getStyle(CellStyle cellStyle, Font font, Map<String, String> style) {

        font.setFontName(style.get(FONT_NAME) == null ? "宋体" : style.get(FONT_NAME));
        font.setBold(Boolean.parseBoolean(style.get(BOLD) == null ? "false" : style.get(BOLD)));
        font.setColor(Short.parseShort(style.get(FONT_COLOR) == null ? "8" : style.get(FONT_COLOR)));
        font.setFontHeightInPoints(Short.parseShort(style.get(SIZE) == null ? "9" : style.get(SIZE)));

        cellStyle.setFont(font);
        cellStyle.setAlignment(HorizontalAlignment.valueOf(style.get(LOCATION) == null ? HorizontalAlignment.CENTER.toString() : style.get(LOCATION)));
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // 前景颜色
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(Short.parseShort(style.get(GROUND_COLOR) == null ? "9" : style.get(GROUND_COLOR)));

        // 边框 -> 上,左,右,下
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);

        return cellStyle;
    }


    public CellStyle getDefaultStyle(CellStyle cellStyle, Font font) {

        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 9);

        cellStyle.setFont(font);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // 边框 -> 上,左,右,下
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);

        return cellStyle;
    }


}
