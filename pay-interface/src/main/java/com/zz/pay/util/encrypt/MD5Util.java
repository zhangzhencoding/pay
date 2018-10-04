package com.zz.pay.util.encrypt;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.PrivateKey;

public class MD5Util {
    public MD5Util() {
    }

    public static String MD5(String inStr) {
        MessageDigest md5 = null;

        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] byteArray = inStr.getBytes("utf-8");
            byte[] md5Bytes = md5.digest(byteArray);
            StringBuffer hexValue = new StringBuffer();

            for(int i = 0; i < md5Bytes.length; ++i) {
                int val = md5Bytes[i] & 255;
                if (val < 16) {
                    hexValue.append("0");
                }

                hexValue.append(Integer.toHexString(val));
            }

            return hexValue.toString();
        } catch (Exception var7) {
            System.out.println(var7.toString());
            var7.printStackTrace();
            return "";
        }
    }

    public static String sign(String text, String key, String charset) throws Exception {
        text = text + key;
        return DigestUtils.md5Hex(getContentBytes(text, charset));
    }

    /** @deprecated */
    public static String sign(String text, PrivateKey key, String charset) throws Exception {
        throw new UnsupportedOperationException();
    }

    public static boolean verify(String text, String sign, String key, String charset) throws Exception {
        text = text + key;
        String mysign = DigestUtils.md5Hex(getContentBytes(text, charset));
        return mysign.equals(sign);
    }

    private static byte[] getContentBytes(String content, String charset) {
        if (charset != null && !"".equals(charset)) {
            try {
                return content.getBytes(charset);
            } catch (UnsupportedEncodingException var3) {
                throw new RuntimeException("签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
            }
        } else {
            return content.getBytes();
        }
    }

    public static void main(String[] args) {
        String inStr = "user.userId.salt";
        String token = MD5(inStr);
        System.out.println(token);
    }
}
