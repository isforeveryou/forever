package com.forever.excel;

import com.forever.utils.ThreadPool;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author WJX
 * @date 2020/11/30 16:24
 */
public class MultipleSheetResolver {

    public static void main(String[] args) throws Exception {
        MultipleSheetResolver multipleSheetResolver = new MultipleSheetResolver();

        String filePath = "D:" + File.separator + "TestFile" + File.separator + "押品系统数据表-v0.1.xlsx";

        Map<String, String[]> sheets = new HashMap<>();
        sheets.put("权证信息表", new String[]{"中文名", "字段名"});
        sheets.put("押品基本信息表", new String[]{"中文名", "字段名"});
        sheets.put("主权证权人关系表", new String[]{"中文名", "字段名", "类型", "长度"});

        Map<String, List<Map<String, String>>> datas =
                multipleSheetResolver.parseMultipleSheetWithColumn(filePath, false, sheets);

        System.out.println(datas.toString());
    }

    private ExcelResolver excelResolver;

    public MultipleSheetResolver() {
        excelResolver = new ExcelResolver();
    }


    public Map<String, List<Map<String, String>>> parseMultipleSheetWithRow(File excelFile, Map<String, String[]> sheetWithRowNames) throws ExecutionException, InterruptedException {
        return parseMultipleSheetWithRow(excelFile, true, sheetWithRowNames);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithRow(String excelPath, Map<String, String[]> sheetWithRowNames) throws ExecutionException, InterruptedException {
        return parseMultipleSheetWithRow(new File(excelPath), true, sheetWithRowNames);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithRow(String excelPath, boolean outputEmpty, Map<String, String[]> sheetWithRowNames) throws ExecutionException, InterruptedException {
        return parseMultipleSheetWithRow(new File(excelPath), outputEmpty, sheetWithRowNames);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithRow(File excelFile, boolean outputEmpty, Map<String, String[]> sheetWithRowNames) throws ExecutionException, InterruptedException {

        Map<String, List<Map<String, String>>> results = new HashMap<>(sheetWithRowNames.size());
        Map<String, Future<List<Map<String, String>>>> futures = new HashMap<>(sheetWithRowNames.size());

        ThreadPoolExecutor pool = ThreadPool.getThreadPool();

        for (Map.Entry<String, String[]> entry : sheetWithRowNames.entrySet()) {
            futures.put(entry.getKey(), pool.submit(() -> excelResolver.parseExcelWithRow(excelFile, entry.getKey(), outputEmpty, entry.getValue())));
        }

        for (Map.Entry<String, Future<List<Map<String, String>>>> entry : futures.entrySet()) {
            results.put(entry.getKey(), entry.getValue().get());
        }

        return results;
    }



    public Map<String, List<Map<String, String>>> parseMultipleSheetWithColumn(File excelFile, Map<String, String[]> sheetWithColumnNames) throws ExecutionException, InterruptedException {
        return parseMultipleSheetWithColumn(excelFile, true, sheetWithColumnNames);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithColumn(String excelPath, Map<String, String[]> sheetWithColumnNames) throws ExecutionException, InterruptedException {
        return parseMultipleSheetWithColumn(new File(excelPath), true, sheetWithColumnNames);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithColumn(String excelPath, boolean outputEmpty, Map<String, String[]> sheetWithColumnNames) throws ExecutionException, InterruptedException {
        return parseMultipleSheetWithColumn(new File(excelPath), outputEmpty, sheetWithColumnNames);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithColumn(File excelFile, boolean outputEmpty, Map<String, String[]> sheetWithColumnNames) throws ExecutionException, InterruptedException {

        Map<String, List<Map<String, String>>> results = new HashMap<>(sheetWithColumnNames.size());
        Map<String, Future<List<Map<String, String>>>> futures = new HashMap<>(sheetWithColumnNames.size());

        ThreadPoolExecutor pool = ThreadPool.getThreadPool();

        for (Map.Entry<String, String[]> entry : sheetWithColumnNames.entrySet()) {
            futures.put(entry.getKey(), pool.submit(() -> excelResolver.parseExcelWithColumn(excelFile, entry.getKey(), outputEmpty, entry.getValue())));
        }

        for (Map.Entry<String, Future<List<Map<String, String>>>> entry : futures.entrySet()) {
            results.put(entry.getKey(), entry.getValue().get());
        }

        return results;
    }


    public Map<String, List<Map<String, String>>> parseMultipleSheetWithColumnByIndex(File excelFile, Map<String, Integer[]> sheetWithColumnIndexes) throws ExecutionException, InterruptedException {
        return parseMultipleSheetWithColumnByIndex(excelFile, true, sheetWithColumnIndexes);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithColumnByIndex(String excelPath, Map<String, Integer[]> sheetWithColumnIndexes) throws ExecutionException, InterruptedException {
        return parseMultipleSheetWithColumnByIndex(new File(excelPath), true, sheetWithColumnIndexes);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithColumnByIndex(String excelPath, Map<String, Integer> sheetWithColumnIndex, Map<String, Integer[]> sheetWithColumnIndexes) throws ExecutionException, InterruptedException {
        return parseMultipleSheetWithColumnByIndex(new File(excelPath), true, sheetWithColumnIndex, sheetWithColumnIndexes);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithColumnByIndex(String excelPath, boolean outputEmpty, Map<String, Integer> sheetWithColumnIndex, Map<String, Integer[]> sheetWithColumnIndexes) throws ExecutionException, InterruptedException {
        return parseMultipleSheetWithColumnByIndex(new File(excelPath), outputEmpty, sheetWithColumnIndex, sheetWithColumnIndexes);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithColumnByIndex(File excelFile, boolean outputEmpty, Map<String, Integer[]> sheetWithColumnIndexes) throws ExecutionException, InterruptedException {
        Map<String, Integer> sheetWithColumnIndex = new HashMap<>(sheetWithColumnIndexes.size());

        for (String sheetName : sheetWithColumnIndexes.keySet()) {
            sheetWithColumnIndex.put(sheetName, 1);
        }
        return parseMultipleSheetWithColumnByIndex(excelFile, outputEmpty, sheetWithColumnIndex, sheetWithColumnIndexes);
    }

    public Map<String, List<Map<String, String>>> parseMultipleSheetWithColumnByIndex(File excelFile, boolean outputEmpty, Map<String, Integer> sheetWithColumnIndex, Map<String, Integer[]> sheetWithColumnIndexes) throws ExecutionException, InterruptedException {

        Map<String, List<Map<String, String>>> results = new HashMap<>(sheetWithColumnIndexes.size());
        Map<String, Future<List<Map<String, String>>>> futures = new HashMap<>(sheetWithColumnIndexes.size());

        ThreadPoolExecutor pool = ThreadPool.getThreadPool();

        for (Map.Entry<String, Integer[]> entry : sheetWithColumnIndexes.entrySet()) {
            futures.put(entry.getKey(), pool.submit(() -> excelResolver.parseExcelWithColumn(excelFile, entry.getKey(), sheetWithColumnIndex.get(entry.getKey()), outputEmpty, entry.getValue())));
        }

        for (Map.Entry<String, Future<List<Map<String, String>>>> entry : futures.entrySet()) {
            results.put(entry.getKey(), entry.getValue().get());
        }

        return results;
    }

}
