package com.forever.scan;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author WJX
 * @date 2020/12/1 15:53
 */
public class Scanner {

    private Scanner() {}

    private static Map<String, ScanListener> listeners = new HashMap<>(2);

    static {
        listeners.put("jar", new JarScanner());
        listeners.put("file", new FileScanner());
    }

    public static ScanListener getScanType(String protocol) {
        return listeners.get(protocol);
    }

    public interface ScanListener {
        /**
         * scan point package all class
         * @param url package url
         * @param packageName scan package name
         * @return all class set
         * @throws IOException decode and parse jar
         */
        Set<String> scan(URL url, String packageName) throws IOException;
    }


    public static class FileScanner implements ScanListener {

        @Override
        public Set<String> scan(URL url, String packageName) throws UnsupportedEncodingException {
            // 获取包的物理路径
            String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
            return scanFiles(new File(filePath), packageName);
        }

        private Set<String> scanFiles(File files, String packageName) {
            File[] fileArray = files.listFiles(file -> (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory());

            if (fileArray == null || fileArray.length <= 0) {
                return new HashSet<>(0);
            }

            Set<String> classSet = new HashSet<>();

            for (File file : fileArray) {
                String fileName = file.getName();
                if (file.isFile()) {
                    classSet.add(packageName + "." + fileName.substring(0, fileName.lastIndexOf(".")));
                } else {
                    classSet.addAll(scanFiles(file, packageName + "." + fileName));
                }
            }

            return classSet;
        }
    }


    public static class JarScanner implements ScanListener {

        @Override
        public Set<String> scan(URL url, String packageName) throws IOException {

            // 获取jar
            JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();

            // 获取包的名字 并进行替换
            String packageDirName = packageName.replace('.', '/');

            // 从此jar包 得到一个枚举类
            Set<String> classSet = new HashSet<>();
            Enumeration<JarEntry> entries = jar.entries();

            while (entries.hasMoreElements()) {
                // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                JarEntry entry = entries.nextElement();
                String name = entry.getName().charAt(0) == '/' ? entry.getName().substring(1) : entry.getName();

                // 如果前半部分包名和定义的包名相同
                if (name.startsWith(packageDirName)) {
                    int index = name.lastIndexOf('/');
                    // 以"/"结尾 是一个包,获取包名,把"/"替换成"."
                    packageName = index != -1 ?  name.substring(0, index).replace('/', '.') : packageName;

                    // class文件
                    if (name.endsWith(".class") && !entry.isDirectory()) {
                        // 获取真正的类名
                        classSet.add(packageName + '.' + name.substring(packageName.length() + 1, name.length() - 6));
                    }
                }
            }

            return classSet;
        }
    }

}
