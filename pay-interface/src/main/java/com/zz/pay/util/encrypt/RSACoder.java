package com.zz.pay.util.encrypt;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSACoder {
    public static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCMNjCXYegv8FxJF64vT13H2EBIWnMpOzAEVa3ALcWB3ySfzYEXAXJ+S5y03Oe9esw/ZyXyOHx3PKE/FxRIQ3rUdzaWOlWID0Ry3vmclWe/6EpsZCg1wlVYKunoPwswo87Tsmx6zV0bKZBdBwdq6/k+NG9QAUi+f3jOC1Iorsv9zQIDAQAB";
    public static String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIw2MJdh6C/wXEkXri9PXcfYQEhacyk7MARVrcAtxYHfJJ/NgRcBcn5LnLTc5716zD9nJfI4fHc8oT8XFEhDetR3NpY6VYgPRHLe+ZyVZ7/oSmxkKDXCVVgq6eg/CzCjztOybHrNXRspkF0HB2rr+T40b1ABSL5/eM4LUiiuy/3NAgMBAAECgYBLajJEmmK6tjvNDLGhvLyiawxYXl4mxTEUg8kK3xOmwRD/6xcN+rdMevr0xQTYg54sVByy5fL8Z14LgyxjmfgMU8OR7+zIquQjYLTbm8c8V1dhj/Ztdm/AlRXw1CHq36EHvUZ2eYBZhj6jaiihvhOENLWg+SMW5oXCZHCYgoOTQQJBAOZeETQlKpvZU6ToDT7q+QYIgnWJYeiPhno6Lhz2gT2E5dPESDNRgvhd5dCrYt6I8UHnJjdArau5O3M984/ITakCQQCb0BF/VdH8YbU7qiQRQYd+SP7PHbwu922x+bAnb17vcofYXcY3v/TQChbR4F4qPyzyk6ApMpvRZ7DZCpIWI52FAkB7/aFw2ZhHbf/RQHzwonQKRUZCkQE7tRSCcRIk0KDbRc6V3o2l4XeGyrNJTxXWBQsll45AKvXO+dH/wxZ3Cy6RAkA2kWAPYFbyNKXsB2IqSPDS4W2by6YkLLNWKUPwC6kL4uiAdhb9X1S1p1LmndVcplNvgRQOWZMIe3AMEw+mgUKhAkEAvRqJBGsq/DDTuY3dYhgTsQHKRBD2XugpsqcK4ZhRT8u5R7hTB5/ZeSWxn41Ok3FpYSWJpUPiWT0yK0tdeX8uAg==";
    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";
    private static final int MAX_ENCRYPT_BLOCK = 117;
    private static final int MAX_DECRYPT_BLOCK = 128;

    public RSACoder() {
    }

    public static byte[] decryptBASE64(String key) {
        return Base64.decodeBase64(key);
    }

    public static String encryptBASE64(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    public static void init() {
    }

    public static String sign(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = decryptBASE64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(priKey);
        signature.update(data);
        return encryptBASE64(signature.sign());
    }

    public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
        byte[] keyBytes = decryptBASE64(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(pubKey);
        signature.update(data);
        return signature.verify(decryptBASE64(sign));
    }

    public static byte[] decryptByPrivateKey(byte[] encryptedData, String key) throws Exception {
        byte[] keyBytes = decryptBASE64(key);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(2, privateKey);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;

        for(int i = 0; inputLen - offSet > 0; offSet = i * 128) {
            byte[] cache;
            if (inputLen - offSet > 128) {
                cache = cipher.doFinal(encryptedData, offSet, 128);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }

            out.write(cache, 0, cache.length);
            ++i;
        }

        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    public static byte[] decryptByPrivateKey(String data, String key) throws Exception {
        return decryptByPrivateKey(decryptBASE64(data), key);
    }

    public static byte[] decryptByPublicKey(byte[] encryptedData, String key) throws Exception {
        byte[] keyBytes = decryptBASE64(key);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(2, publicKey);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;

        for(int i = 0; inputLen - offSet > 0; offSet = i * 128) {
            byte[] cache;
            if (inputLen - offSet > 128) {
                cache = cipher.doFinal(encryptedData, offSet, 128);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }

            out.write(cache, 0, cache.length);
            ++i;
        }

        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    public static byte[] encryptByPublicKey(String dataStr, String key) throws Exception {
        byte[] keyBytes = decryptBASE64(key);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(1, publicKey);
        byte[] data = dataStr.getBytes();
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;

        for(int i = 0; inputLen - offSet > 0; offSet = i * 117) {
            byte[] cache;
            if (inputLen - offSet > 117) {
                cache = cipher.doFinal(data, offSet, 117);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }

            out.write(cache, 0, cache.length);
            ++i;
        }

        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    public static byte[] encryptByPrivateKey(byte[] data, String key) throws Exception {
        byte[] keyBytes = decryptBASE64(key);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(1, privateKey);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;

        for(int i = 0; inputLen - offSet > 0; offSet = i * 117) {
            byte[] cache;
            if (inputLen - offSet > 117) {
                cache = cipher.doFinal(data, offSet, 117);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }

            out.write(cache, 0, cache.length);
            ++i;
        }

        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    public static String getPrivateKey(Map<String, Key> keyMap) throws Exception {
        Key key = (Key)keyMap.get("RSAPrivateKey");
        return encryptBASE64(key.getEncoded());
    }

    public static String getPublicKey(Map<String, Key> keyMap) throws Exception {
        Key key = (Key)keyMap.get("RSAPublicKey");
        return encryptBASE64(key.getEncoded());
    }

    public static Map<String, Key> initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        Map<String, Key> keyMap = new HashMap(2);
        keyMap.put("RSAPublicKey", keyPair.getPublic());
        keyMap.put("RSAPrivateKey", keyPair.getPrivate());
        return keyMap;
    }

//    public static Map<String, String> initKeyFromCms() throws Exception {
//        String publicKey = PublicCommonConfiguration.getCommonConfig("public.key");
//        String privateKey = PublicCommonConfiguration.getCommonConfig("private.key");
//        Map<String, String> keyMap = new HashMap(2);
//        keyMap.put("RSAPublicKey", publicKey);
//        keyMap.put("RSAPrivateKey", privateKey);
//        return keyMap;
//    }
}