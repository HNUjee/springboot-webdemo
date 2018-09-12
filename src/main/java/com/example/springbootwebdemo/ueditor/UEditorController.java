package com.example.springbootwebdemo.ueditor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Api(value = "UEditorController", tags = {"富文本控制器"})
@RestController
public class UEditorController {
/*    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "userId", value = "用户ID", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "password", value = "旧密码", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "newPassword", value = "新密码", required = true, dataType = "String")
    })*/
    /**
     * @Description 获取config.json配置文件
     */
    @ApiOperation(value = "获取config.json配置文件")
    @PostMapping("getConfig")
    public void getConfig(HttpServletResponse response){
        org.springframework.core.io.Resource res = new ClassPathResource("config.json");
        InputStream is = null;
        response.setHeader("Content-Type", "text/html");
        try {
            is = new FileInputStream(res.getFile());
            StringBuffer sb = new StringBuffer();
            byte[] b = new byte[1024];
            int length;
            while (-1 != (length = is.read(b))) {
                sb.append(new String(b, 0, length, "utf-8"));
            }
            String result = sb.toString().replaceAll("/\\*(.|[\\r\\n])*?\\*/", "");
            JSONObject json = JSON.parseObject(result);
            PrintWriter out = response.getWriter();
            out.print(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 富文本上传的文件地址
     * @param files
     */
    @ApiOperation(value = "upload", notes = "富文本上传的文件地址")
    @ApiImplicitParam(value = "富文本上传的文件地址", required = true, paramType = "MultipartFile[]", dataType = "" +
            "String")
    @PostMapping("upload")
    public String upload(@RequestParam("upfile") MultipartFile[] files){
        FileOutputStream outputStream=null;
        try {
            MultipartFile file = files[0];
            outputStream = new FileOutputStream("F:/tomcat/tomcat8/webapps/ROOT/a.jpg");
            try {
                outputStream.write(file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        JSONObject jsobject = new JSONObject();
        jsobject.put("state", "SUCCESS");
        jsobject.put("url","/a.jpg");
        jsobject.put("title", "");
        jsobject.put("original", "");
        return jsobject.toString();
    }
}
