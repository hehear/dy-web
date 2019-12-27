package com.dy.u.report.util;

import com.sun.nio.zipfs.ZipPath;
import org.thymeleaf.util.StringUtils;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Description zip压缩包工具类、
 * @author dxy
 * @date 2019-12-04
 *
 */
public class ZipUtil {

    private final static String FILE_SEPERATOR = "/";
    private final static String ZIP_NM_SUFFIX = ".zip";

    /**
     * 生成zip压缩文件
     * @param filePathList 被压缩的文件路径集合
     * @param zipName 压缩包文件名
     * @param zipPath 压缩包生成路径
     * @return
     * @throws Exception
     */
    public static boolean generateZipFile(List<String> filePathList,String zipName, String zipPath) throws Exception {

        boolean result = false;

        FileInputStream fis = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        ZipOutputStream zos = null;

        if(filePathList == null||filePathList.size() == 0){
            return result;
        }

        try {

            if(!new File(zipPath).exists()){
                new File(zipPath).mkdirs();
            }
            //拼接zip名称
            if(!StringUtils.isEmpty(zipName) && !zipName.endsWith(ZIP_NM_SUFFIX) ){
                zipName = zipName+ZIP_NM_SUFFIX;
            }
            //拼接zip路径
            if(!StringUtils.isEmpty(zipPath) && !zipName.endsWith(FILE_SEPERATOR) ){
                zipPath = zipPath+FILE_SEPERATOR;
            }
            File zipFile = new File(zipPath+zipName);

            if(zipFile.exists()){
                //生成前删除
                zipFile.delete();
            }

            fos = new FileOutputStream(zipFile);
            zos = new ZipOutputStream(new BufferedOutputStream(fos));
            byte[] bufs = new byte[1024*10];

            //遍历文件集合
            for (int i = 0; i < filePathList.size(); i++){

                File file = new File(filePathList.get(i));
                //放入pdf
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zos.putNextEntry(zipEntry);
                //读pdf文件
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis,1024*10);
                int read = 0;
                //写入zip压缩包
                while((read = bis.read(bufs,0,1024*10)) != -1){
                    zos.write(bufs,0,read);
                }
            }
            result = true;

        }catch(Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }finally {

            if(null != bis){
                bis.close();
            }

            if(null != zos){
                zos.close();

            }
        }

        return result;

    }


}