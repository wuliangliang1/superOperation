package com.liang.wechat.util;


import org.apache.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;


public class DiyUploadUtils {
    private static final Logger logger = Logger.getLogger(DiyUploadUtils.class);

    /**
     * 文件上传
     *
     * @param inputStream
     * @param uploadUrl
     * @param filename
     * @return
     * @throws Exception
     */
    public static File upload(InputStream inputStream, String uploadUrl, String filename) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        FileOutputStream outputStream = null;
        File f = null;
        try {
            //如果文件目录不存在，则新建
            f = new File(uploadUrl);
            if (!f.exists()) {
                f.mkdirs();
            }
            outputStream = new FileOutputStream(uploadUrl + File.separator + filename);
            byte[] buf = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, length);
            }
        } catch (Exception e) {
            logger.error("上传异常：" + e);
        } finally {
            outputStream.flush();
            inputStream.close();
            outputStream.close();
        }
        return new File(uploadUrl + File.separator + filename);
    }

    /**
     * 文件上传Channel版 相比较upload性能差异不大，
     *
     * @param inputStream
     * @param uploadUrl   目标文件夹
     * @param filename    保存的文件名
     * @return
     * @throws Exception
     */
    public static File uploadNIO(InputStream inputStream, String uploadUrl, String filename) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        FileOutputStream outputStream = null;
        File f = null;
        try {
            //如果文件目录不存在，则新建
            f = new File(uploadUrl);
            if (!f.exists()) {
                f.mkdirs();
            }
            outputStream = new FileOutputStream(uploadUrl + File.separator + filename);
            //获取读通道
            FileChannel readChannel = ((FileInputStream) inputStream).getChannel();
            //获取读通道
            FileChannel writeChannel = outputStream.getChannel();
            //创建一个缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            //判断输入流中的数据是否已经读完的标识
            int length = 0;
            //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
            while (true) {
                buffer.clear();
                int len = readChannel.read(buffer);//读入数据
                if (len < 0) {
                    break;//读取完毕
                }
                buffer.flip();
                writeChannel.write(buffer);//写入数据
            }
        } catch (Exception e) {
            logger.error("上传异常：" + e);
        } finally {
            outputStream.flush();
            inputStream.close();
            outputStream.close();
        }
        return new File(uploadUrl + File.separator + filename);
    }

    public static void write(OutputStream outputStream, String filename) throws Exception {
        InputStream inputStream = null;
        try {
            File src = new File(filename);
            //打开流
            inputStream = new FileInputStream(src);
            byte[] buf = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, length);
            }
        } catch (Exception e) {
            logger.error("上传异常：" + e);
        } finally {
            outputStream.flush();
            inputStream.close();
            outputStream.close();
        }
    }

    /**
     * @param urlPath     下载路径
     * @param downloadDir 下载存放目录
     * @return 返回下载文件
     */
    public static void downloadFile(String urlPath, String downloadDir,String fileName) {

        URL url = null;
        InputStream inputStream = null;
        FileOutputStream fos = null;
        try {
            url = new URL(urlPath);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(10*1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            //得到输入流
            inputStream = conn.getInputStream();
            //获取自己数组
            byte[] getData = readInputStream(inputStream);

            //文件保存位置
            File saveDir = new File(downloadDir);
            if(!saveDir.exists()){
                saveDir.mkdir();
            }
            File file = new File(saveDir+File.separator+fileName);
            fos = new FileOutputStream(file);
            fos.write(getData);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(fos!=null){

                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }
    public static void main(String[] args) {
//        InputStream inputStream = null;
//        ByteArrayOutputStream baos = null;
//        BufferedOutputStream bout = null;
//        try {
//            inputStream = new FileInputStream(new File("E:\\tmp\\wolf.png"));
//            // 创建一个新的 byte 数组输出流，它具有指定大小的缓冲区容量
//            baos = new ByteArrayOutputStream();
//            //创建一个新的缓冲输出流，以将数据写入指定的底层输出流
//            bout = new BufferedOutputStream(baos);
//            byte[] buffer = new byte[1024];
//            int len = inputStream.read(buffer);
//            while (len != -1) {
//                bout.write(buffer, 0, len);
//                len = inputStream.read(buffer);
//            }
//            //刷新此输出流并强制写出所有缓冲的输出字节，必须这行代码，否则有可能有问题
//            bout.flush();
//            byte[] bytes = baos.toByteArray();
//            //sun公司的API
//            //return encoder.encodeBuffer(bytes).trim();
//            //apache公司的API
//            String s = Base64.encodeBase64String(bytes);
//            convertBase64DataToImage(s, "E:\\tmp\\test\\2.jpg");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                inputStream.close();
//                //关闭 ByteArrayOutputStream 无效。此类中的方法在关闭此流后仍可被调用，而不会产生任何 IOException
//                //baos.close();
//                bout.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        String urlPath="https://cig.dhl.de/gkvlabel/PRODUCTION/dhl-vls/gw/shpmntws/printShipment?token=JD7HKktuvugIFEkhSvCfbEz4J8Ah0dkcVuw4PzBGRyTvETxlbbpyTh6H45BjvmXjZF1H8PNUzSubIrlaQ9mKxk9TSIgVezkZZTyJLjUQQdc%3D";
        String downloadDir="E:/tmp/";
        downloadFile(urlPath,downloadDir,"00340434199080002353.pdf");
    }

}
