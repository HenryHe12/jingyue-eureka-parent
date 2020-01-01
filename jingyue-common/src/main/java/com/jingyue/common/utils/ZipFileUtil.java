/**
 * 文件名：ZipFileUtil.java
 * 版权：Copyright 2017-2022 CMCC All Rights Reserved.
 * 描述：ZIp文件工具类
 */

package com.jingyue.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * ZIP文件工具类
 *
 * @author Wtx
 * @version 1.0
 * @date 2018/4/17 21:28
 */
public final class ZipFileUtil {

    /**
     * 日志类
     */
    private static Logger logger = LoggerFactory.getLogger(ZipFileUtil.class);

    public static final int MAGIC_INT = -1;

    /**
     * 私有构造方法
     */
    private ZipFileUtil() {
    }

    /**
     * 缓存大小
     */
    private static final int BUFFER_SIZE = 2 * 1024;

    /**
     * 解压文件
     *
     * @param zipFilePath        ZIP文件路径
     * @param unzipFilePath      解压文件路径
     * @param includeZipFileName 是否包含解压文件名称
     * @return Map<String,String>    xml索引文件路径和名称
     * @throws Exception 异常
     */
    public static Map<String, String> unzip(String zipFilePath, String unzipFilePath, boolean includeZipFileName)
            throws Exception {
        if (StringUtils.isEmpty(zipFilePath) || StringUtils.isEmpty(unzipFilePath)) {
            throw new
                    RuntimeException("参数异常");
        }
        File zipFile = new File(zipFilePath);
        // 如果解压后的文件保存路径包含压缩文件的文件名，则追加该文件名到解压路径
        if (includeZipFileName) {
            String fileName = zipFile.getName();
            if (!StringUtils.isEmpty(fileName)) {
                fileName = fileName.substring(0, fileName.lastIndexOf("."));
            }
            unzipFilePath = unzipFilePath + File.separator + fileName;
        }
        // 创建解压缩文件保存的路径
        File unzipFileDir = new File(unzipFilePath);
        unzipFileDir.setWritable(true, false);
        if (!unzipFileDir.exists() || !unzipFileDir.isDirectory()) {
            unzipFileDir.mkdirs();
        }

        // 开始解压
        ZipEntry entry = null;
        String entryFilePath = null, entryDirPath = null, entryName = null, fileType = null;
        File entryFile = null, entryDir = null;
        int index = 0, count = 0;
        byte[] buffer = new byte[BUFFER_SIZE];
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        ZipFile zip = null;
        Map<String, String> xmlPathMap = new HashMap<String, String>();
        try {
            zip = getZipFile(zipFile);
            Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zip.entries();
            // 循环对压缩包里的每一个文件进行解压
            while (entries.hasMoreElements()) {
                entry = entries.nextElement();
                if (entry.isDirectory()) {
                    continue;
                }
                // 构建压缩包中一个文件解压后保存的文件全路径
                entryFilePath = unzipFilePath + File.separator + entry.getName();
                File entryFilePathTemp = new File(entryFilePath);
                entryFilePath = entryFilePathTemp.getAbsolutePath();
                // 构建解压后保存的文件夹路径
                index = entryFilePath.lastIndexOf(File.separator);
                if (index != MAGIC_INT) {
                    entryDirPath = entryFilePath.substring(0, index);
                    entryName = entryFilePath.substring(index + 1);
                } else {
                    entryDirPath = "";
                    entryName = entryFilePath;
                }
                fileType = entryName.substring(entryName.lastIndexOf(".") + 1);
                if ("xml".equalsIgnoreCase(fileType)) {
                    xmlPathMap.put(entryDirPath + File.separator, entryName);
                }
                entryDir = new File(entryDirPath);
                entryDir.setWritable(true, false);
                // 如果文件夹路径不存在，则创建文件夹
                if (!entryDir.exists() || !entryDir.isDirectory()) {
                    entryDir.mkdirs();
                }
                // 创建解压文件
                entryFile = new File(entryFilePath);
                entryFile.setWritable(true, false);
                if (entryFile.exists()) {
                    // 删除已存在的目标文件
                    entryFile.delete();
                }
                // 写入文件
                try {
                    logger.info(entryFile.getAbsolutePath());
                    bos = new BufferedOutputStream(new FileOutputStream(entryFile));
                    bis = new BufferedInputStream(zip.getInputStream(entry));
                    while ((count = bis.read(buffer, 0, BUFFER_SIZE)) != MAGIC_INT) {
                        bos.write(buffer, 0, count);
                    }
                    bos.flush();
                } catch (Exception e) {
                    logger.error(e.toString());
                } finally {
                    try {
                        if (bis != null) {
                            bis.close();
                        }
                    } catch (IOException e) {
                        logger.error("io close error:", e);
                    }
                    try {
                        if (bos != null) {
                            bos.close();
                        }
                    } catch (IOException e) {
                        logger.error("io close error:", e);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.toString());
            throw new RuntimeException("解压异常！", e);
        } finally {
            if (zip != null) {
                zip.close();
            }
        }
        return xmlPathMap;
    }

    /**
     * 获取GBK或UTF-8编码的ZipFile
     *
     * @param zipFile 源文件
     * @return ZipFile 获取到的压缩文件
     * @throws Exception 异常信息
     */
    public static ZipFile getZipFile(File zipFile) throws Exception {
        ZipFile tempZip = null;
        ZipEntry entry = null;
        try {
            tempZip = new ZipFile(zipFile, Charset.forName("GBK"));
            Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) tempZip.entries();
            if (entries.hasMoreElements()) {
                entry = entries.nextElement();
            }
        } catch (Exception e) {
            logger.error(e.toString());
            tempZip = new ZipFile(zipFile, Charset.forName("UTF-8"));
            Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) tempZip.entries();
            if (entries.hasMoreElements()) {
                entry = entries.nextElement();
            }
        }
        return tempZip;
    }

    /**
     * 解压zip文件，获取里面的.dat文件的文件名
     *
     * @param zipFile       zip文件
     * @param unzipFilePath 解压路径
     * @return .dat文件的文件名
     * @throws Exception 异常
     */
    @SuppressWarnings("rawtypes")
    public static String unzip(File zipFile, String unzipFilePath) throws Exception {
        if (StringUtils.isEmpty(unzipFilePath)) {
            throw new RuntimeException("解压路径异常");
        }
        // 创建解压缩文件保存的路径
        File unzipFileDir = new File(unzipFilePath);
        unzipFileDir.setWritable(true, false);
        if (!unzipFileDir.exists() || !unzipFileDir.isDirectory()) {
            unzipFileDir.mkdirs();
        }
        String fileName = "";
        //解决zip文件中有中文目录或者中文文件
        ZipFile zip = new ZipFile(zipFile, Charset.forName("UTF-8"));
        Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zip.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            String zipEntryName = entry.getName();
            if (zipEntryName.endsWith(".dat")) {
                fileName = zipEntryName;
            }
            InputStream in = null;
            OutputStream out = null;
            try {
                in = zip.getInputStream(entry);
                String outPath = unzipFilePath + File.separator + zipEntryName;
                //判断路径是否存在,不存在则创建文件路径
                File file = new File(outPath.substring(0, outPath.lastIndexOf(File.separator)));
                if (!file.exists()) {
                    file.mkdirs();
                }
                //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
                if (new File(outPath).isDirectory()) {
                    continue;
                }
                out = new FileOutputStream(outPath);
                byte[] buf1 = new byte[BUFFER_SIZE];
                int len;
                while ((len = in.read(buf1)) > 0) {
                    out.write(buf1, 0, len);
                }
            } catch (Exception e) {
                logger.error(e.toString());
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException e) {
                    logger.error("io close error:", e);
                }
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    logger.error("io close error:", e);
                }
            }
        }
        return fileName;
    }

    /**
     * 删除单个文件
     *
     * @param fileName 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            return file.delete();
        } else {
            return false;
        }
    }


    /**
     * 删除目录及目录下的文件
     * @param dir 要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     * @throws Exception 抛出异常
     */
    public static boolean deleteDirectory(String dir) throws Exception {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator)) {
            dir = dir + File.separator;
        }
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            throw new RuntimeException("删除目录失败：" + dir + "不存在！");
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            } else if (files[i].isDirectory()) {
                // 删除子目录
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }
        if (!flag) {
            throw new RuntimeException("删除目录失败！");
        }
        // 删除当前目录
        return dirFile.delete();
    }
}
