package com.forum.demo.UtilTool;


import org.apache.commons.codec.digest.DigestUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

public class StringUtils {
    private static String encodeKey = "thisisaMD5encodeKey";

    //检测参数是否为null
    public static boolean checkKey(String key){
        if(key==null||key.isEmpty()){
            return false;
        }
        return true;
    }


    //获取md5加密后的字符串
    public static String toMD5(String str){
        String encodeStr = DigestUtils.md5Hex(str+encodeKey);
        return encodeStr;

    }

    //对比字符串与md5字符串是否相同
    public static boolean equalsToMd5(String str,String md5str){
        if(StringUtils.toMD5(str).equalsIgnoreCase(md5str))
            return true;
        return false;
    }


    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }


    public static String getIPAddress(){
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
        return address.getHostAddress(); //返回IP地址
    }

}
