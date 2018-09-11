package com.example.springbootwebdemo.reptile.action;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static com.example.springbootwebdemo.reptile.Utils.ConnectionUtils.getConnection;

/**
 * 爬去网页的url地址
 */
public class UrlReptile {
    public static void main(String[] args){
        scrapUrl("https://www.baidu.com");
        System.out.println("scrap success!");
//        match("http://www.rndsystems.com/cn");
    }
    public static void scrapUrl(String url){
        HttpURLConnection connection=null;
        PrintWriter writer=null;
        try {
            connection = getConnection(url);
            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            /*   FileOutputStream os = new FileOutputStream("D:/a.txt");*/
            writer = new PrintWriter(new FileWriter("D:/a.txt"),true);
            String s = null;
            while (null != (s = reader.readLine())) {
                match(s,writer);
                /*os.write(match.getBytes());*/
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (null!=connection) connection.disconnect();
            if (null!=writer) writer.close();
        }
    }
    public static void match(String s,PrintWriter writer) throws IOException {
        String regex = "https://[\\w+\\.?/?]+\\.[A-Za-z]+";//url匹配规则
        Pattern pattern=Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()){
            writer.println( matcher.group());
        }
    }

}
