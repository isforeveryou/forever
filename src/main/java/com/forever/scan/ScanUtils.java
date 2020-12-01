package com.forever.scan;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * @author WJX
 * @date 2020/5/12 19:52
 */
public class ScanUtils {


    public static void main(String[] args) throws IOException {
        System.out.println(scanPackage("com"));
    }

    public static Set<String> scanPackage(String packageName) throws IOException {

        // 获取包的名字 并进行替换
        String packageDirName = packageName.replace('.', '/');
        // 定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);

        // 遍历
        Set<String> resultSet = new HashSet<>();
        while (dirs.hasMoreElements()) {
            // 获取下一个元素
            URL url = dirs.nextElement();
            // 获取全类名
            resultSet.addAll(Scanner.getScanType(url.getProtocol()).scan(url, packageName));
        }

        return resultSet;
    }

}
