package com.sheepshop.businessside.okhttp;

import android.util.Base64;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


/*
 * AES对称加密和解密
 */
public class AESUtils {
    private static final String AES = "AES";

    public static String initKey(String key) {
        return (key + "0000000000000000000000").substring(0, 16);
    }

    /**
     * 加密
     *
     * @param
     * @return
     */
    private static byte[] _encrypt(byte[] src, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(AES);
        SecretKeySpec skey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), AES);
        cipher.init(Cipher.ENCRYPT_MODE, skey);//设置密钥和加密形式
        return cipher.doFinal(src);
    }

    /**
     * 解密
     *
     * @param
     * @return
     * @throws Exception
     */
    private static byte[] _decrypt(byte[] src, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(AES);
        SecretKeySpec skey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), AES);//设置加密Key
        cipher.init(Cipher.DECRYPT_MODE, skey);//设置密钥和解密形式
        return cipher.doFinal(src);
    }

    /**
     * 解密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public final static String decrypt(String data, String key) {
        try {
            byte[] result = Base64.decode(data.getBytes(StandardCharsets.UTF_8), Base64.NO_WRAP);
            return new String(_decrypt(result, key));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public final static String encrypt(String data, String key) {
        try {
            byte[] result = _encrypt(data.getBytes(StandardCharsets.UTF_8), key);
            return Base64.encodeToString(result, Base64.NO_WRAP);
            //return java.util.Base64.getEncoder().encode(result);
            //return new String(java.net.URLEncoder(result,"utf-8"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


}