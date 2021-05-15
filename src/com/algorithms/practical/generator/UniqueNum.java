package com.algorithms.practical.generator;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 根据当前时间生成唯一编号
 * 16位，年月日时分秒 + 2位序列号
 */
public class UniqueNum {

    private static String lastDate = "19700101000001"; // 记录上一次取号的时间
    private static int lastNum = 0; // 记录上一次取号的最后两位，同1秒内最多有1~99个号
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 取号
     * @return
     */
    public synchronized static String getNumber() {
        // 获取当前时间
        String dateNow = sdf.format(new Date());
        // 和上次时间比较日否一致
        if (dateNow.compareTo(lastDate) == 0) {
            // 时间一致，序列号+1
            lastNum++;
            if (lastNum > 99) {
                // 超过99，等待
                while (true) {
                    String newDate = sdf.format(new Date());
                    // 直到时间不一致时，序号从1开始
                    if (newDate.compareTo(lastDate) != 0) {
                        lastDate = newDate;
                        lastNum = 1;
                        break;
                    }
                }
            }
        } else {
            // 时间不一致，序号从1开始
            lastDate = dateNow;
            lastNum = 1;
        }
        return String.format("%s%02d", lastDate, lastNum);
    }
}
