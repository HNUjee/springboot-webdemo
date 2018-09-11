package com.example.springbootwebdemo.qrcode.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;

@RequestMapping("qrcode")
@RestController
public class PicController {

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @ResponseBody
    @RequestMapping("/upload")
    public void uploadImg(HttpServletRequest request){
        FileOutputStream outputStream=null;
        InputStream inputStream=null;

        try {
            Part pic = request.getPart("pic");
            inputStream = pic.getInputStream();
            outputStream = new FileOutputStream("D:/test.jpg");
            int len;
            byte[] buff=new byte[1024];
            while ((len=inputStream.read(buff))!=-1){
                outputStream.write(buff,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }finally {
            if (null!=outputStream){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null!=inputStream){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @RequestMapping("/showPic")
    public String showPic(){
        try {
            String encodePic = encodePic(new FileInputStream("D://test.jpg"));
            //在img标签下直接显示base64格式图片需要在前面加上:  "data:image/png;base64,"
            System.out.println("-----------------------------------------");
            FileOutputStream fileOutputStream = new FileOutputStream("d://test.txt");
            try {
                fileOutputStream.write(encodePic.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fileOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.print("data:image/png;base64,"+encodePic);
            return null==encodePic?"":"data:image/png;base64,"+encodePic;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
    /**
     * @param encodePic
     * @descrip 将base64格式字符串转成byte数组
     */
    private byte[] decodePic(String encodePic) {
        BASE64Decoder decoder=new BASE64Decoder();
        try {
            return decoder.decodeBuffer(encodePic);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param inputStream
     * 将图片转成base64格式
     */
    public String encodePic(FileInputStream inputStream){
        BASE64Encoder encoder=new BASE64Encoder();
        try {
            byte buff [] =new byte[inputStream.available()];
            inputStream.read(buff);
            return encoder.encode(buff);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
