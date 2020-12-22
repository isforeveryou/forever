package com.forever.excel;

import com.forever.excel.entity.SheetStructure;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author WJX
 * @date 2020/12/17 10:56
 */
public class ExcelCreator {

    private Workbook workbook;

    public ExcelCreator() {
        this.workbook = new XSSFWorkbook();
    }

    public ExcelCreator(Workbook workbook) {
        this.workbook = workbook;
    }


    public void createExcel(List<SheetStructure> sheetStructures) {
        sheetStructures.forEach(this::createExcel);
    }


    public void createExcel(SheetStructure sheetStructures) {

        Sheet sheet = this.workbook.createSheet(sheetStructures.getSheetName());

        int lastColumnIndex = sheetStructures.getStartColumn() - 1;

        for (int i = sheetStructures.getStartRow() - 1; i < sheetStructures.getEndRow(); i++) {

            Row row = sheet.createRow(i);
            for (int j = sheetStructures.getStartColumn() - 1; j < sheetStructures.getEndColumn(i + 1); j++) {
                Cell cell = row.createCell(j, CellType.STRING);
                cell.setCellValue(sheetStructures.getColumn(i + 1, j + 1));

                lastColumnIndex = Math.max(lastColumnIndex, j);
                cell.setCellStyle(sheetStructures.getStyle(workbook.createCellStyle(), workbook.createFont(), sheetStructures.getStyle(i, j)));
            }
        }


        for (int column = sheetStructures.getStartColumn() - 1; column < lastColumnIndex; column++) {
            sheet.autoSizeColumn(column);
        }


        sheetStructures.getMerges().forEach(merge -> merge(sheet, merge));
    }


    public void save(String savePath) {
        String suffix = workbook instanceof XSSFWorkbook ? ".xlsx" : ".xls";
        try (FileOutputStream fos = new FileOutputStream(savePath + suffix)) {
            workbook.write(fos);
            fos.flush();
        } catch (IOException ignored) {
        }
    }


    private void merge(Sheet sheet, CellRangeAddress addresses) {
        sheet.addMergedRegion(addresses);
        RegionUtil.setBorderBottom(BorderStyle.THIN, addresses, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, addresses, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, addresses, sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN, addresses, sheet);
    }

}
