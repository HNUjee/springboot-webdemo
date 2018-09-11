package com.example.springbootwebdemo.reptile.action;

import com.example.springbootwebdemo.reptile.domain.JdbcConnectionUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.*;

/**
 * 使用jsoup抓取页面信息
 */
public class JsoupReptile {
    public static void main(String[]args) throws SQLException {
        dosScrap();
//        getJdGoods();
        //http://search.jd.com/Search?keyword=Python&enc=utf-8&qrst=1&rt=1&stop=1&book=y&vt=2&wq=Python&page=7&s=181&click=0
            //http://search.jd.com/Search?keyword=Python&enc=utf-8&qrst=1&rt=1&stop=1&book=y&vt=2&wq=Python&page=5&s=121&click=0
            //http://search.jd.com/Search?keyword=Python&enc=utf-8&qrst=1&rt=1&stop=1&book=y&vt=2&wq=Python&page=3&s=61&click=0
            //http://search.jd.com/Search?keyword=Python&enc=utf-8&book=y&wq=Python&pvid=33xo9lni.p4a1qb
    }
    private static void getJdGoods() throws SQLException {
        Connection connection = JdbcConnectionUtils.getConnection();
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery("select * from jdgoods");
        while (set.next()){
            System.out.println(set.getString("author"));
        }
    }
    private static void dosScrap() {
        for (int i=0;i<5;i++){
            final int finalI = i;
            new Thread(new Runnable() {
                public void run() {
                    String url="http://search.jd.com/Search?keyword=Python&enc=utf-8&qrst=1&rt=1&stop=1&book=y&vt=2&wq=Python&page=" +
                            ((finalI +1)*2-1)+ "&s="+(finalI*60+1)+"&click=0";
//                        String url="http://search.jd.com/Search?keyword=Python&enc=utf-8&book=y&wq=Python&pvid=33xo9lni.p4a1qb";
                    try {
                        scrap(url);
                        System.out.println("Thread--"+Thread.currentThread()+"---success!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
    private static void scrap(String url) throws Exception {
        Connection connection =null;
        PreparedStatement pstmt=null;
        try {
            connection = JdbcConnectionUtils.getConnection();
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement("insert into jdgoods(name, author, price,imgurl, publish, publishtime) " +
                    "value (?,?,?,?,?,?)");
            Document dom = Jsoup.connect(url).get();
            //获取京东货物的列表
            Element j_goodsList = dom.getElementById("J_goodsList");
            Elements li = j_goodsList.getElementsByTag("li");
            for (int i = 0; i < li.size(); i++) {
                String imageurl = li.get(i).getElementsByClass("p-img").get(0).getElementsByTag("img").
                        get(0).attr("src");
                String price = li.get(i).getElementsByClass("p-price").first().getElementsByTag("i").text();
                String name = li.get(i).getElementsByClass("p-name").first().getElementsByTag("em").text();
                Elements span = li.get(i).getElementsByClass("p-bookdetails").first().getElementsByTag("span");
                String author ="";
                String publish ="";
                String publishtime="";
                if (span.size()>=3) {
                    author = span.get(0).getElementsByTag("a").text();
                    publish = span.get(1).getElementsByTag("a").text();
                    publishtime = span.get(2).text();
                }else if (span.size()>1)
                {   author = span.get(0).getElementsByTag("a").text();
                    publish = span.get(1).getElementsByTag("a").text();
                }else {
                    author = span.get(0).getElementsByTag("a").text();
                }
                pstmt.setString(1, name);
                pstmt.setString(2, author);
                pstmt.setString(3, price);
                pstmt.setString(4, imageurl);
                pstmt.setString(5, publish);
                pstmt.setString(6, publishtime);
                pstmt.execute();
            }
            connection.commit();
        }catch (Exception e){
            e.printStackTrace();
            connection.rollback();
        }finally {
            if (null!=pstmt) pstmt.close();
            if (null!=connection) connection.close();
        }
    }
    /**
     * jsoup 的基本方法
     *  String location = dom.location();
     *  Element body = dom.body();
     *  dom.charset();
     *  dom.head();
     *  dom.id();
     */
}
