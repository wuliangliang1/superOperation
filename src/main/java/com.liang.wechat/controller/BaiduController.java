package com.liang.wechat.controller;

/**
 * Created by ll.wu on 2018/6/11.
 */

import com.liang.wechat.pojo.Baidu.BaiduWords;
import com.liang.wechat.util.BaiduUtil;
import com.liang.wechat.util.DiyUploadUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/bd")
public class BaiduController {
    //添加一个日志器
    private static final Logger logger = LoggerFactory.getLogger(BaiduController.class);

    @RequestMapping("upload")
    public String index(){
        return "upload";
    }
    //映射一个action
    @RequestMapping("ocr/doUpload")
    @ResponseBody
    public String index(MultipartHttpServletRequest multipartRequest){
        //输出日志文件
        logger.info("ocr start");
        try {
            List<BaiduWords> wordsList = new ArrayList<BaiduWords>();
            String wordsAll = "";
            for (Iterator it = multipartRequest.getFileNames(); it.hasNext(); ) {
                String key = (String) it.next();
                MultipartFile multipartFile = multipartRequest.getFile(key);
                InputStream is = multipartFile.getInputStream();
                byte[] bytes = IOUtils.toByteArray(is);
                List<BaiduWords> words = BaiduUtil.ocr(bytes);
                for(BaiduWords word : words){
                    wordsAll += word.getWords() + "<br/>";
                }
                return wordsAll;
            }
            return wordsAll;
        }catch (Exception e){
            return null;
        }
    }
    public static void upload(String attachments,String uploadPath,
                              MultipartFile multipartFile) {
        //原文件名称
        String originalFileName = multipartFile.getOriginalFilename();
        // b-截取后缀, 重命名文件, 使用uuid+后缀的方式命名保存到服务器上的文件
        String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));
        logger.info("文件后缀: " + suffix);
        String uuid = UUID.randomUUID().toString();
        //系统重命名后文件名称
        String fileName = uuid + suffix;
        logger.info("新文件名: " + fileName);

        try {

            File uploadFile = DiyUploadUtils.upload(multipartFile.getInputStream(),
                    uploadPath+File.separator+File.separator, fileName);
            if (uploadFile.exists()) {

                logger.info(originalFileName + "上传成功");
            } else {
                logger.info(originalFileName + "上传失败");
                throw new FileNotFoundException("file write fail: "
                        + fileName);
            }
        } catch (Exception e) {
            logger.info(originalFileName + "上传失败");
            e.printStackTrace();
        }
    }
}
