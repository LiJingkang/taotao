package com.taotao.controller;

import com.taotao.common.utils.JsonUtils;
import com.taotao.utils.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.jsp.tagext.TryCatchFinally;
import java.util.HashMap;
import java.util.Map;

/**
 * 图片上传Controller
 *
 * Created by Skying on 2017/3/2.
 */
@Controller
public class PictureController {

    // 从配置文件里面取出数据
    @Value("${IMAGE_SERVER_URL}")
    private String IMAGE_SERVER_URL;

    @RequestMapping("/pic/upload")
    // 响应一个json,加上 @ResponseBody
    // 实际上是通过Response 来响应浏览器。 Map 的兼容性不好，
    // 如果响应一个对象 会默认使用 SpringMVC 使用 jackson 变成一个字符串来响应
    // 我们直接使用字符串来响应
    @ResponseBody
    public String picUpload(MultipartFile uploadFile) {
        // 响应对应的返回JSON 需要有一个对用的pojo。我们先创建一个对应的java对象
        // 使用Map也可以。 Map 里面的key不清楚。

        try {
            // 1. 接受上传的文件
            String originalFilename = uploadFile.getOriginalFilename();
            // 2. 取扩展名
            String exName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

            // 3. 上传到图片服务器
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:/resource/client.conf");
            // 将二进制流上传，然后得到一个字符串数组
            String url = fastDFSClient.uploadFile(uploadFile.getBytes() , exName);
            url = IMAGE_SERVER_URL + url;

            // 4. 响应上传图片的url
            Map result = new HashMap<>();
            result.put("error", 0);
            result.put("url", url);

            // 把result 的对象转为 String
            // 使用工具类
            return JsonUtils.objectToJson(result);
        } catch (Exception e) {
            e.printStackTrace();
            Map result = new HashMap<>();
            result.put("error", 1);
            result.put("message", "图片上传失败");
            return JsonUtils.objectToJson(result);
        }
    }
}
