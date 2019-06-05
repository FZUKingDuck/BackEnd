package com.forum.demo.UtilTool;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

public class DateUtil {


    //获取时间戳
    //主要用于做随机的id
    public static String getIdFromDate(){
        Random random = new Random();
        int n = (random.nextInt(9)+1);
        String last = String.valueOf(n);
        return Long.toString(new Date().getTime())+n;


    }

    //获取时间
    public static Timestamp getTime() {
        return new Timestamp(new Date(null).getTime());
    }


    //获取一个随机数字（String格式)J
    public static String getRandomToString(){
        Random random = new Random(new Date().getTime());
        return String.valueOf(random.nextInt(10));
    }
}
