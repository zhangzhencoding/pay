package com.zz.pay.util.encrypt;

import com.alibaba.fastjson.JSON;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.provider.JCERSAPrivateKey;
import org.bouncycastle.jce.provider.JCERSAPublicKey;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAUtil {
    private static final String seedKey = "random";
    private static final String charSet = "UTF-8";

    public RSAUtil() {
    }

    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA", new BouncyCastleProvider());
        kpg.initialize(1024, new SecureRandom());
        KeyPair kp = kpg.generateKeyPair();
        return kp;
    }

    public static byte[] decrypt(PrivateKey privateKey, byte[] encrypted) throws Exception {
        long start = System.currentTimeMillis();
        Cipher cipher = Cipher.getInstance("RSA", new BouncyCastleProvider());
        cipher.init(2, privateKey);
        byte[] re = cipher.doFinal(encrypted);
        long end = System.currentTimeMillis();
        System.out.println("decrypt use time " + (end - start) + "");
        return re;
    }

    public static String decodeJsValue(PrivateKey privateKey, String jsValue) throws Exception {
        byte[] input = Hex.decode(jsValue);
        byte[] raw = decrypt(privateKey, input);

        int i;
        for(i = raw.length - 1; i > 0 && raw[i] != 0; --i) {
            ;
        }

        ++i;
        byte[] data = new byte[raw.length - i];

        for(int j = i; j < raw.length; ++j) {
            data[j - i] = raw[j];
        }

        return new String(data, "UTF-8");
    }

    public static String getJsPublicKey(PublicKey publicKey) {
        if (publicKey != null) {
            JCERSAPublicKey jce = (JCERSAPublicKey)publicKey;
            return jce.getModulus().toString(16);
        } else {
            return null;
        }
    }

    public static String getJsPrivateKey(PrivateKey privateKey) {
        if (privateKey != null) {
            JCERSAPrivateKey jce = (JCERSAPrivateKey)privateKey;
            return jce.getModulus().toString(16);
        } else {
            return null;
        }
    }

    private void test() {
    }

    public static PublicKey getPublicRSAKey(String key) throws Exception {
        X509EncodedKeySpec x509 = new X509EncodedKeySpec(Base64.decode(key));
        KeyFactory kf = KeyFactory.getInstance("RSA", new BouncyCastleProvider());
        return kf.generatePublic(x509);
    }

    public static PrivateKey getPrivateRSAKey(String key) throws Exception {
        PKCS8EncodedKeySpec pkgs8 = new PKCS8EncodedKeySpec(Base64.decode(key));
        KeyFactory kf = KeyFactory.getInstance("RSA", new BouncyCastleProvider());
        return kf.generatePrivate(pkgs8);
    }

    public static byte[] encrypt(PublicKey publicKey, String input) throws Exception {
        long start = System.currentTimeMillis();
        Cipher cipher = Cipher.getInstance("RSA", new BouncyCastleProvider());
        cipher.init(1, publicKey);
        byte[] re = cipher.doFinal(input.getBytes("UTF-8"));
        long end = System.currentTimeMillis();
        System.out.println("encrypt use time " + (end - start) + "");
        return re;
    }

    public static byte[] decryptBase64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    public static String encryptBase64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

    public static void main(String[] args) throws Exception {
        KeyPair keyPair = generateKeyPair();
        System.out.println(JSON.toJSON(keyPair));
        String privateKeyStr = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBANIghL6H4t6E15Y9ljLme2l7bpnQmidwm5/+GiYIPUz0/Zi0IQri4KYPgSp9i9bXCGWunjlwbIyvbai6M0AXKOi2gIzdX9p3nkV85hvEHejsNSRs4W9lWzHalK6prE0D9/Tqnw7vaV9vrfiV00b+qrUfDp0G36eOVzim5j+XTvaRAgMBAAECgYBJ5bXJa/CVYRKQe8g+zl/IkOgsn/gWtruEYNDBX1RvpBkrgu277l4jCIDceJqdS7JgUDDE8oy6TVe7ZwBvIbX4vZShhweePYSwy/DuuVL6DfHT6ThI2gFHiyte51p9eBUch5lWCOs5EPI8ljTy6wkpE2DV7NKuFUj13A8ulKoLgQJBAP83PTJPJcJ8fXfcqomy0KTuskdqqEgwUy+KbdF+vFdk2p3jKYm7iKCeEMsnOGMBzC/93xZS1ieZEVb9OqZ9YOUCQQDSxc+sB6w1f8/VCMpqpjyccmj1yhqGtQbfFBfwgnW4uDG0uLk1roclTn0oOPkxhJwy7oRiudYzNzttWufuJWA9AkBKpP1hGYURvRd1VzUJ0SL1GltKSAewFXI/FV3JSCgN55GJXEdp4U+0qoZ+5Jm5W209HCODg/be3gYUSzw8Na+BAkBXWVMvYB6mLUncZ8A7YplL3L5S3mcP3IX0fUdOjUJsQymWU9etv56Sf2yu9q2PL/3rvZ1vrMRwhTtDnqDvuN2hAkAgujzFmmaPp/ZlNjKvuGfD0BEv1VpLjKfrpZPUJiAMnVjMS5VwHG3pIS4ByoOjsHGEis2qwAH6sOQIhhjWzIdC";
        String publicKeyStr = null;
        PrivateKey privateKey = getPrivateRSAKey(privateKeyStr);
        PublicKey publicKey = keyPair.getPublic();
        System.out.println("js中使用的公钥字符串1=" + getJsPublicKey(publicKey));
        String de = "08f7e292ccb4c73a981569a9c2dbf2b9c0c2cf615967282863e6e358432af288f1f026ed91a8ff5f6579ac246af9ce1f94f85e92b8a926627b95e6bd05b00b80a5548e9ce1a9bb2a20073cce629936ab9e27021af7370c2664065107a702c1805a4ec131a3573007213da3e390221053867074a427ffc28aa642fe2099ad7332";
        de = "803c303d0f76f715c2f9ac6c96336079f2d1f72176224b8f0dde7294dfdae73cc0f4c54c7dfb6788eed739cf420f82b057164fea119fd96a46163ffdb8d64af9862a2ea6d2dfbe95b162dd2c0e314e88347d3207a0f359a8241a445ea4db7884f1c5aad109e81a8a58d3c3cddcdb7166c7add4ae0600ed994b3bc6bb39608aca";
        System.out.println(decodeJsValue(privateKey, de));
    }
}
