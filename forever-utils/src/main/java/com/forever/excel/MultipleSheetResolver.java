package com.forever.excel;

import com.forever.utils.ThreadPool;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author WJX
 * @date 2020/11/30 16:24
 */
public class MultipleSheetResolver {

    private ExcelResolver excelResolver;

    public MultipleSheetResolver() {
        excelResolver = new ExcelResolver();
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithRow(String excelPath, Map<String, String[]> sheetWithRowNames) throws ExecutionException, InterruptedException, IOException {
        return parseMultipleSheetWithRow(new File(excelPath), true, sheetWithRowNames);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithRow(String excelPath, boolean outputEmpty, Map<String, String[]> sheetWithRowNames) throws ExecutionException, InterruptedException, IOException {
        return parseMultipleSheetWithRow(new File(excelPath), outputEmpty, sheetWithRowNames);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithRow(InputStream excelStream, Map<String, String[]> sheetWithRowNames) throws ExecutionException, InterruptedException, IOException {
        return parseMultipleSheetWithRow(WorkbookFactory.create(excelStream), true, sheetWithRowNames);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithRow(InputStream excelStream, boolean outputEmpty, Map<String, String[]> sheetWithRowNames) throws ExecutionException, InterruptedException, IOException {
        return parseMultipleSheetWithRow(WorkbookFactory.create(excelStream), outputEmpty, sheetWithRowNames);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithRow(File excelFile, Map<String, String[]> sheetWithRowNames) throws ExecutionException, InterruptedException, IOException {
        return parseMultipleSheetWithRow(WorkbookFactory.create(excelFile), true, sheetWithRowNames);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithRow(File excelFile, boolean outputEmpty, Map<String, String[]> sheetWithRowNames) throws ExecutionException, InterruptedException, IOException {
        return parseMultipleSheetWithRow(WorkbookFactory.create(excelFile), outputEmpty, sheetWithRowNames);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithRow(Workbook workbook, boolean outputEmpty, Map<String, String[]> sheetWithRowNames) throws ExecutionException, InterruptedException {

        Map<String, List<Map<String, String>>> results = new HashMap<>(sheetWithRowNames.size());
        Map<String, Future<List<Map<String, String>>>> futures = new HashMap<>(sheetWithRowNames.size());

        ThreadPoolExecutor pool = ThreadPool.getThreadPool();

        for (Map.Entry<String, String[]> entry : sheetWithRowNames.entrySet()) {
            futures.put(entry.getKey(), pool.submit(() -> excelResolver.parseExcelWithRow(workbook, entry.getKey(), outputEmpty, entry.getValue())));
        }

        for (Map.Entry<String, Future<List<Map<String, String>>>> entry : futures.entrySet()) {
            results.put(entry.getKey(), entry.getValue().get());
        }

        return results;
    }




    public Map<String, List<Map<String, String>>> parseMultipleSheetWithRowByIndex(String excelPath, Map<String, Integer[]> sheetWithRowIndexes) throws ExecutionException, InterruptedException, IOException {
        return parseMultipleSheetWithRowByIndex(new File(excelPath), true, sheetWithRowIndexes);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithRowByIndex(String excelPath, boolean outputEmpty, Map<String, Integer[]> sheetWithRowIndexes) throws ExecutionException, InterruptedException, IOException {
        return parseMultipleSheetWithRowByIndex(new File(excelPath), outputEmpty, sheetWithRowIndexes);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithRowByIndex(String excelPath, Map<String, Integer> sheetWithRowIndex, Map<String, Integer[]> sheetWithRowIndexes) throws ExecutionException, InterruptedException, IOException {
        return parseMultipleSheetWithRowByIndex(new File(excelPath), true, sheetWithRowIndex, sheetWithRowIndexes);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithRowByIndex(String excelPath, boolean outputEmpty, Map<String, Integer> sheetWithRowIndex, Map<String, Integer[]> sheetWithRowIndexes) throws ExecutionException, InterruptedException, IOException {
        return parseMultipleSheetWithRowByIndex(new File(excelPath), outputEmpty, sheetWithRowIndex, sheetWithRowIndexes);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithRowByIndex(InputStream excelStream, Map<String, Integer[]> sheetWithRowIndexes) throws ExecutionException, InterruptedException, IOException {
        return parseMultipleSheetWithRowByIndex(excelStream, true, sheetWithRowIndexes);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithRowByIndex(InputStream excelStream, boolean outputEmpty, Map<String, Integer[]> sheetWithRowIndexes) throws ExecutionException, InterruptedException, IOException {
        Map<String, Integer> sheetWithRowIndex = new HashMap<>(sheetWithRowIndexes.size());
        for (String sheetName : sheetWithRowIndexes.keySet()) {
            sheetWithRowIndex.put(sheetName, 1);
        }
        return parseMultipleSheetWithRowByIndex(WorkbookFactory.create(excelStream), outputEmpty, sheetWithRowIndex, sheetWithRowIndexes);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithRowByIndex(InputStream excelStream, Map<String, Integer> sheetWithRowIndex, Map<String, Integer[]> sheetWithRowIndexes) throws ExecutionException, InterruptedException, IOException {
        return parseMultipleSheetWithRowByIndex(WorkbookFactory.create(excelStream), true, sheetWithRowIndex, sheetWithRowIndexes);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithRowByIndex(InputStream excelStream, boolean outputEmpty, Map<String, Integer> sheetWithRowIndex, Map<String, Integer[]> sheetWithRowIndexes) throws ExecutionException, InterruptedException, IOException {
        return parseMultipleSheetWithRowByIndex(WorkbookFactory.create(excelStream), outputEmpty, sheetWithRowIndex, sheetWithRowIndexes);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithRowByIndex(File excelFile, Map<String, Integer[]> sheetWithRowIndexes) throws ExecutionException, InterruptedException, IOException {
        return parseMultipleSheetWithRowByIndex(excelFile, true, sheetWithRowIndexes);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithRowByIndex(File excelFile, boolean outputEmpty, Map<String, Integer[]> sheetWithRowIndexes) throws ExecutionException, InterruptedException, IOException {
        Map<String, Integer> sheetWithRowIndex = new HashMap<>(sheetWithRowIndexes.size());
        for (String sheetName : sheetWithRowIndexes.keySet()) {
            sheetWithRowIndex.put(sheetName, 1);
        }
        return parseMultipleSheetWithRowByIndex(WorkbookFactory.create(excelFile), outputEmpty, sheetWithRowIndex, sheetWithRowIndexes);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithRowByIndex(File excelFile, Map<String, Integer> sheetWithRowIndex, Map<String, Integer[]> sheetWithRowIndexes) throws ExecutionException, InterruptedException, IOException {
        return parseMultipleSheetWithRowByIndex(WorkbookFactory.create(excelFile), true, sheetWithRowIndex, sheetWithRowIndexes);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithRowByIndex(File excelFile, boolean outputEmpty, Map<String, Integer> sheetWithRowIndex, Map<String, Integer[]> sheetWithRowIndexes) throws ExecutionException, InterruptedException, IOException {
        return parseMultipleSheetWithRowByIndex(WorkbookFactory.create(excelFile), outputEmpty, sheetWithRowIndex, sheetWithRowIndexes);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithRowByIndex(Workbook workbook, boolean outputEmpty, Map<String, Integer> sheetWithRowIndex, Map<String, Integer[]> sheetWithRowIndexes) throws ExecutionException, InterruptedException {

        Map<String, List<Map<String, String>>> results = new HashMap<>(sheetWithRowIndexes.size());
        Map<String, Future<List<Map<String, String>>>> futures = new HashMap<>(sheetWithRowIndexes.size());

        ThreadPoolExecutor pool = ThreadPool.getThreadPool();

        for (Map.Entry<String, Integer[]> entry : sheetWithRowIndexes.entrySet()) {
            futures.put(entry.getKey(), pool.submit(() -> excelResolver.parseExcelWithRow(workbook, entry.getKey(), sheetWithRowIndex.get(entry.getKey()), outputEmpty, entry.getValue())));
        }

        for (Map.Entry<String, Future<List<Map<String, String>>>> entry : futures.entrySet()) {
            results.put(entry.getKey(), entry.getValue().get());
        }

        return results;
    }




    public Map<String, List<Map<String, String>>> parseMultipleSheetWithColumn(String excelPath, Map<String, String[]> sheetWithColumnNames) throws ExecutionException, InterruptedException, IOException {
        return parseMultipleSheetWithColumn(new File(excelPath), true, sheetWithColumnNames);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithColumn(String excelPath, boolean outputEmpty, Map<String, String[]> sheetWithColumnNames) throws ExecutionException, InterruptedException, IOException {
        return parseMultipleSheetWithColumn(new File(excelPath), outputEmpty, sheetWithColumnNames);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithColumn(InputStream excelStream, Map<String, String[]> sheetWithColumnNames) throws ExecutionException, InterruptedException, IOException {
        return parseMultipleSheetWithColumn(WorkbookFactory.create(excelStream), true, sheetWithColumnNames);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithColumn(InputStream excelStream, boolean outputEmpty, Map<String, String[]> sheetWithColumnNames) throws ExecutionException, InterruptedException, IOException {
        return parseMultipleSheetWithColumn(WorkbookFactory.create(excelStream), outputEmpty, sheetWithColumnNames);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithColumn(File excelFile, Map<String, String[]> sheetWithColumnNames) throws ExecutionException, InterruptedException, IOException {
        return parseMultipleSheetWithColumn(excelFile, true, sheetWithColumnNames);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithColumn(File excelFile, boolean outputEmpty, Map<String, String[]> sheetWithColumnNames) throws ExecutionException, InterruptedException, IOException {
        return parseMultipleSheetWithColumn(WorkbookFactory.create(excelFile), outputEmpty, sheetWithColumnNames);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithColumn(Workbook workbook, boolean outputEmpty, Map<String, String[]> sheetWithColumnNames) throws ExecutionException, InterruptedException {

        Map<String, List<Map<String, String>>> results = new HashMap<>(sheetWithColumnNames.size());
        Map<String, Future<List<Map<String, String>>>> futures = new HashMap<>(sheetWithColumnNames.size());

        ThreadPoolExecutor pool = ThreadPool.getThreadPool();

        for (Map.Entry<String, String[]> entry : sheetWithColumnNames.entrySet()) {
            futures.put(entry.getKey(), pool.submit(() -> excelResolver.parseExcelWithColumn(workbook, entry.getKey(), outputEmpty, entry.getValue())));
        }

        for (Map.Entry<String, Future<List<Map<String, String>>>> entry : futures.entrySet()) {
            results.put(entry.getKey(), entry.getValue().get());
        }

        return results;
    }






    public Map<String, List<Map<String, String>>> parseMultipleSheetWithColumnByIndex(String excelPath, Map<String, Integer[]> sheetWithColumnIndexes) throws ExecutionException, InterruptedException, IOException {
        return parseMultipleSheetWithColumnByIndex(new File(excelPath), true, sheetWithColumnIndexes);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithColumnByIndex(String excelPath, Map<String, Integer> sheetWithColumnIndex, Map<String, Integer[]> sheetWithColumnIndexes) throws ExecutionException, InterruptedException, IOException {
        return parseMultipleSheetWithColumnByIndex(new File(excelPath), true, sheetWithColumnIndex, sheetWithColumnIndexes);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithColumnByIndex(String excelPath, boolean outputEmpty, Map<String, Integer> sheetWithColumnIndex, Map<String, Integer[]> sheetWithColumnIndexes) throws ExecutionException, InterruptedException, IOException {
        return parseMultipleSheetWithColumnByIndex(new File(excelPath), outputEmpty, sheetWithColumnIndex, sheetWithColumnIndexes);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithColumnByIndex(InputStream excelStream, Map<String, Integer[]> sheetWithColumnIndexes) throws ExecutionException, InterruptedException, IOException {
        return parseMultipleSheetWithColumnByIndex(excelStream, true, sheetWithColumnIndexes);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithColumnByIndex(InputStream excelStream, boolean outputEmpty, Map<String, Integer[]> sheetWithColumnIndexes) throws ExecutionException, InterruptedException, IOException {
        Map<String, Integer> sheetWithColumnIndex = new HashMap<>(sheetWithColumnIndexes.size());
        for (String sheetName : sheetWithColumnIndexes.keySet()) {
            sheetWithColumnIndex.put(sheetName, 1);
        }
        return parseMultipleSheetWithColumnByIndex(WorkbookFactory.create(excelStream), outputEmpty, sheetWithColumnIndex, sheetWithColumnIndexes);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithColumnByIndex(InputStream excelStream, Map<String, Integer> sheetWithColumnIndex, Map<String, Integer[]> sheetWithColumnIndexes) throws ExecutionException, InterruptedException, IOException {
        return parseMultipleSheetWithColumnByIndex(WorkbookFactory.create(excelStream), true, sheetWithColumnIndex, sheetWithColumnIndexes);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithColumnByIndex(InputStream excelStream, boolean outputEmpty, Map<String, Integer> sheetWithColumnIndex, Map<String, Integer[]> sheetWithColumnIndexes) throws ExecutionException, InterruptedException, IOException {
        return parseMultipleSheetWithColumnByIndex(WorkbookFactory.create(excelStream), outputEmpty, sheetWithColumnIndex, sheetWithColumnIndexes);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithColumnByIndex(File excelFile, Map<String, Integer[]> sheetWithColumnIndexes) throws ExecutionException, InterruptedException, IOException {
        return parseMultipleSheetWithColumnByIndex(excelFile, true, sheetWithColumnIndexes);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithColumnByIndex(File excelFile, boolean outputEmpty, Map<String, Integer[]> sheetWithColumnIndexes) throws ExecutionException, InterruptedException, IOException {
        Map<String, Integer> sheetWithColumnIndex = new HashMap<>(sheetWithColumnIndexes.size());
        for (String sheetName : sheetWithColumnIndexes.keySet()) {
            sheetWithColumnIndex.put(sheetName, 1);
        }
        return parseMultipleSheetWithColumnByIndex(WorkbookFactory.create(excelFile), outputEmpty, sheetWithColumnIndex, sheetWithColumnIndexes);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithColumnByIndex(File excelFile, Map<String, Integer> sheetWithColumnIndex, Map<String, Integer[]> sheetWithColumnIndexes) throws ExecutionException, InterruptedException, IOException {
        return parseMultipleSheetWithColumnByIndex(WorkbookFactory.create(excelFile), true, sheetWithColumnIndex, sheetWithColumnIndexes);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithColumnByIndex(File excelFile, boolean outputEmpty, Map<String, Integer> sheetWithColumnIndex, Map<String, Integer[]> sheetWithColumnIndexes) throws ExecutionException, InterruptedException, IOException {
        return parseMultipleSheetWithColumnByIndex(WorkbookFactory.create(excelFile), outputEmpty, sheetWithColumnIndex, sheetWithColumnIndexes);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithColumnByIndex(Workbook workbook, boolean outputEmpty, Map<String, Integer> sheetWithColumnIndex, Map<String, Integer[]> sheetWithColumnIndexes) throws ExecutionException, InterruptedException {

        Map<String, List<Map<String, String>>> results = new HashMap<>(sheetWithColumnIndexes.size());
        Map<String, Future<List<Map<String, String>>>> futures = new HashMap<>(sheetWithColumnIndexes.size());

        ThreadPoolExecutor pool = ThreadPool.getThreadPool();

        for (Map.Entry<String, Integer[]> entry : sheetWithColumnIndexes.entrySet()) {
            futures.put(entry.getKey(), pool.submit(() -> excelResolver.parseExcelWithColumn(workbook, entry.getKey(), sheetWithColumnIndex.get(entry.getKey()), outputEmpty, entry.getValue())));
        }

        for (Map.Entry<String, Future<List<Map<String, String>>>> entry : futures.entrySet()) {
            results.put(entry.getKey(), entry.getValue().get());
        }

        return results;
    }


}
