package com.muaj.lib.util

import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * Created by muaj on 2018/7/2
 * 使用md5的算法进行加密的Utils
 */
internal object Md5Utils {

    fun md5(plainText: String): String {
        var secretBytes: ByteArray? = null
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    plainText.toByteArray())
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("Not Support MD5 Algorithm！")
        }

        var md5code = BigInteger(1, secretBytes!!).toString(16)
        for (i in 0 until 32 - md5code.length) {
            md5code = "0$md5code"
        }
        return md5code
    }


}