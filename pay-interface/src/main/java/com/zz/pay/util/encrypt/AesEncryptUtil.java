package com.zz.pay.util.encrypt;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AesEncryptUtil {
    private static String KEY = "zhgp20171229java";
    private static String IV = "zhgp20171229java";
    public static final String KEY_ALGORITHM = "AES";
    public static final String ECB_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
    public static final String CBC_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

    public AesEncryptUtil() {
    }

    public static String encrypt(String data, String key, String iv) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength += blockSize - plaintextLength % blockSize;
            }

            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
            cipher.init(1, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);
            return (new Base64()).encodeToString(encrypted);
        } catch (Exception var11) {
            var11.printStackTrace();
            return null;
        }
    }

    public static String desEncrypt(String data, String key, String iv) throws Exception {
        try {
            byte[] encrypted1 = (new Base64()).decode(data);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
            cipher.init(2, keyspec, ivspec);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            return originalString;
        } catch (Exception var9) {
            var9.printStackTrace();
            return null;
        }
    }

    public static String encrypt(String data) throws Exception {
        return encrypt(data, KEY, IV);
    }

    public static String desEncrypt(String data) throws Exception {
        return desEncrypt(data, KEY, IV);
    }

    public static void main(String[] args) throws Exception {
        String data = "123";
        String tokenkey = "f05c8d3edc3a31cee738032619c21a6e";
        String key = tokenkey.substring(0, 16);
        String iv = tokenkey.substring(16, 32);
        System.out.println(key);
        System.out.println(iv);
        String token = encrypt(data, key.toString(), iv.toString());
        System.out.println(token);
        String str = desEncrypt(token, key, iv);
        System.out.println(str.trim());
    }
}