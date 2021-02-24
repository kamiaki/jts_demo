package org.example;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadJarData {
    /**
     * jar数据读取
     *
     * @param filePath
     * @return
     */
    public String jarDataRead(String filePath) {
        String dataStr;
        try {
            InputStream inputStream = this.getClass().getResourceAsStream(filePath);
            InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bReader = new BufferedReader(reader);
            StringBuilder sb = new StringBuilder();
            String s;
            while ((s = bReader.readLine()) != null) {
                sb.append(s);
            }
            bReader.close();
            dataStr = sb.toString();
        } catch (Exception e) {
            dataStr = "";
        }
        return dataStr;
    }

    /**
     * jar数据读取 静态
     * @param filePath
     * @return
     */
    public static String jarDataReadStatic(String filePath) {
        String dataStr;
        try {
            InputStream inputStream = ReadJarData.class.getResourceAsStream(filePath);
            InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bReader = new BufferedReader(reader);
            StringBuilder sb = new StringBuilder();
            String s;
            while ((s = bReader.readLine()) != null) {
                sb.append(s);
            }
            bReader.close();
            dataStr = sb.toString();
        } catch (Exception e) {
            dataStr = "";
        }
        return dataStr;
    }
}
