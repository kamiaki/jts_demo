package org.example;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        String s1 = new ReadJarData().jarDataRead("/11.json");
        String s = ReadJarData.jarDataReadStatic("/11.json");
        System.out.println(s1);
        System.out.println(s);
    }
}
