package com.liang.module_base.utils;

import java.util.Random;
import java.util.UUID;

public class RandomUtil {
    /**
     * 生成一个0 到 count 之间的随机数
     */
    public static int getNum(int endNum) {
        if (endNum > 0) {
            Random random = new Random();
            return random.nextInt(endNum);
        }
        return 0;
    }

    /**
     * 生成一个startNum 到 endNum之间的随机数(不包含endNum的随机数)
     */
    public static int getNum(int startNum, int endNum) {
        if (endNum > startNum) {
            Random random = new Random();
            return random.nextInt(endNum - startNum) + startNum;
        }
        return 0;
    }

    /**
     * 生成随机大写字母
     */
    public static String getLargeLetter() {
        Random random = new Random();
        return String.valueOf((char) (random.nextInt(27) + 'A'));
    }

    /**
     * 生成随机大写字母字符串
     */
    public static String getLargeLetter(int size) {
        StringBuffer buffer = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            buffer.append((char) (random.nextInt(27) + 'A'));
        }
        return buffer.toString();
    }

    /**
     * 生成随机小写字母
     */
    public static String getSmallLetter() {
        Random random = new Random();
        return String.valueOf((char) (random.nextInt(27) + 'a'));
    }

    /**
     * 生成随机小写字母字符串
     */
    public static String getSmallLetter(int size) {
        StringBuffer buffer = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            buffer.append((char) (random.nextInt(27) + 'a'));
        }
        return buffer.toString();
    }

    /**
     * 数字与小写字母混编字符串
     */
    public static String getNumSmallLetter(int size) {
        StringBuffer buffer = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            if (random.nextInt(2) % 2 == 0) {//字母
                buffer.append((char) (random.nextInt(27) + 'a'));
            } else {//数字
                buffer.append(random.nextInt(10));
            }
        }
        return buffer.toString();
    }

    /**
     * 数字与大写字母混编字符串
     */
    public static String getNumLargeLetter(int size) {
        StringBuffer buffer = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            if (random.nextInt(2) % 2 == 0) {//字母
                buffer.append((char) (random.nextInt(27) + 'A'));
            } else {//数字
                buffer.append(random.nextInt(10));
            }
        }
        return buffer.toString();
    }

    /**
     * 数字与大小写字母混编字符串
     */
    public static String getNumLargeSmallLetter(int size) {
        StringBuffer buffer = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            if (random.nextInt(2) % 2 == 0) {//字母
                if (random.nextInt(2) % 2 == 0) {
                    buffer.append((char) (random.nextInt(27) + 'A'));
                } else {
                    buffer.append((char) (random.nextInt(27) + 'a'));
                }
            } else {//数字
                buffer.append(random.nextInt(10));
            }
        }
        return buffer.toString();
    }

    /**
     * 32位默认长度的uuid
     * (获取32位uuid)
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * (获取指定长度uuid)
     */
    public static String getUUID(int len) {
        if (0 >= len) {
            return null;
        }
        String uuid = getUUID();
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < len; i++) {
            str.append(uuid.charAt(i));
        }
        return str.toString();
    }

    /**
     * (获取14位长度uuid)
     */
    public static String get14UUID() {

        int len = 14;
        String uuid = getUUID();
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < len; i++) {
            str.append(uuid.charAt(i));
        }
        return str.toString();
    }

    /**
     * (获取保险uuid)
     */
    public static String getInsuranceUUID() {
        int len = 14;
        String uuid = getUUID();
        StringBuffer str = new StringBuffer();
        str.append("PCAA");
        for (int i = 0; i < len; i++) {
            str.append(uuid.charAt(i));
        }
        return str.toString().toUpperCase();
    }
}