package com.liang.module_base.utils

import java.util.*

object RandomUtilKt {

    /**
     * 生成一个0 到 count 之间的随机数
     */
    fun getNum(endNum: Int): Int {
        if (endNum > 0) {
            val random = Random()
            return random.nextInt(endNum)
        }
        return 0
    }

    /**
     * 生成一个startNum 到 endNum之间的随机数(不包含endNum的随机数)
     */
    fun getNum(startNum: Int, endNum: Int): Int {
        if (endNum > startNum) {
            val random = Random()
            return random.nextInt(endNum - startNum) + startNum
        }
        return 0
    }

    /**
     * 生成随机大写字母
     */
    val largeLetter: String
        get() {
            val random = Random()
            return (random.nextInt(27) + 'A'.code).toChar().toString()
        }

    /**
     * 生成随机大写字母字符串
     */
    fun getLargeLetter(size: Int): String {
        val buffer = StringBuffer()
        val random = Random()
        for (i in 0 until size) {
            buffer.append((random.nextInt(27) + 'A'.code).toChar())
        }
        return buffer.toString()
    }

    /**
     * 生成随机小写字母
     */
    val smallLetter: String
        get() {
            val random = Random()
            return (random.nextInt(27) + 'a'.code).toChar().toString()
        }

    /**
     * 生成随机小写字母字符串
     */
    fun getSmallLetter(size: Int): String {
        val buffer = StringBuffer()
        val random = Random()
        for (i in 0 until size) {
            buffer.append((random.nextInt(27) + 'a'.code).toChar())
        }
        return buffer.toString()
    }

    /**
     * 数字与小写字母混编字符串
     */
    fun getNumSmallLetter(size: Int): String {
        val buffer = StringBuffer()
        val random = Random()
        for (i in 0 until size) {
            if (random.nextInt(2) % 2 == 0) { //字母
                buffer.append((random.nextInt(27) + 'a'.code).toChar())
            } else { //数字
                buffer.append(random.nextInt(10))
            }
        }
        return buffer.toString()
    }

    /**
     * 数字与大写字母混编字符串
     */
    fun getNumLargeLetter(size: Int): String {
        val buffer = StringBuffer()
        val random = Random()
        for (i in 0 until size) {
            if (random.nextInt(2) % 2 == 0) { //字母
                buffer.append((random.nextInt(27) + 'A'.code).toChar())
            } else { //数字
                buffer.append(random.nextInt(10))
            }
        }
        return buffer.toString()
    }

    /**
     * 数字与大小写字母混编字符串
     */
    fun getNumLargeSmallLetter(size: Int): String {
        val buffer = StringBuffer()
        val random = Random()
        for (i in 0 until size) {
            if (random.nextInt(2) % 2 == 0) { //字母
                if (random.nextInt(2) % 2 == 0) {
                    buffer.append((random.nextInt(27) + 'A'.code).toChar())
                } else {
                    buffer.append((random.nextInt(27) + 'a'.code).toChar())
                }
            } else { //数字
                buffer.append(random.nextInt(10))
            }
        }
        return buffer.toString()
    }

    /**
     * 32位默认长度的uuid
     * (获取32位uuid)
     */
    val uUID: String
        get() = UUID.randomUUID().toString().replace("-".toRegex(), "")

    /**
     * (获取指定长度uuid)
     */
    fun getUUID(len: Int): String? {
        if (0 >= len) {
            return null
        }
        val uuid = uUID
        val str = StringBuffer()
        for (i in 0 until len) {
            str.append(uuid[i])
        }
        return str.toString()
    }

    /**
     * (获取14位长度uuid)
     */
    fun get14UUID(): String {
        val len = 14
        val uuid = uUID
        val str = StringBuffer()
        for (i in 0 until len) {
            str.append(uuid[i])
        }
        return str.toString()
    }

    /**
     * (获取保险uuid)
     */
    val insuranceUUID: String
        get() {
            val len = 14
            val uuid = uUID
            val str = StringBuffer()
            str.append("PCAA")
            for (i in 0 until len) {
                str.append(uuid[i])
            }
            return str.toString().uppercase(Locale.getDefault())
        }
}