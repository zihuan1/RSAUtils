package com.zihuan.rsautils;

import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.MessageDigest;

public class EncryptionUtils {


    /**
     * 将字符串进行md5加密
     * pragma argString, 明文
     * return 经过md5加密后的密文
     */
    public final static String MD5(String argString) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] btInput = argString.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public final static String MD5_16(String argString) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] btInput = argString.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }

            String strResult = new String(str);
            return strResult.substring(8, 24);
            //return str.toString();
        } catch (Exception e) {
            Log.i("----", e.toString());
            e.printStackTrace();
            return null;
        }
    }

    //sha1 加密
    public static String getSha1(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 编码
     *
     * @param m
     * @return
     */
    public static String base64Decode(String m) {
        return new String(Base64.decode(m, Base64.DEFAULT));
    }

    /**
     * 解码
     *
     * @param m
     * @return
     */
    public static String base64Encode(String m) {
        return new String(Base64.encode(m.getBytes(), Base64.DEFAULT));
    }

    /**
     * RandomAccessFile 文件加密/解密
     * 再执行一遍是解密
     * 目前来说安全性并不是特别高,之后会优化成进行前中后三段加密
     * @param file
     * @param key
     * @throws IOException
     */
    public static void randomAccessEncryption(File file, int key) throws IOException {
        /**
         * 从文件读取一个字节值，对key异或后再写回到文件
         */
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        byte[] buff = new byte[8192];// 8192 8k是最佳值
        int n;// 保存每一批的数量,每一批有n个
        while ((n = raf.read(buff)) != -1) {
            // 对数组前n个字节值加密
            for (int i = 0; i < n; i++) {
                buff[i] ^= key;
            }
            // 下标前移n个位置
            raf.seek(raf.getFilePointer() - n);
            // 数组中前n个字节，一批写回文件
            raf.write(buff, 0, n);
        }

        raf.close();
    }
}
