package com.zz.pay.util.encrypt;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;

public class RSAEncryptor {
    public static RSAEncryptor sharedInstance = null;
    private RSAPrivateKey privateKey;
    private RSAPublicKey publicKey;
    private static final char[] HEX_CHAR = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static void main(String[] args) throws Exception {
        String privateKeyPath = "C://key//rsa_public_key.pem";
        String publicKeyPath = "C://key//pkcs8_private_key.pem";
        RSAEncryptor rsaEncryptor = new RSAEncryptor(privateKeyPath, publicKeyPath);

        try {
            String test = "JAVA";
            String testRSAEnWith64 = rsaEncryptor.encryptWithBase64(test);
            String testRSADeWith64 = rsaEncryptor.decryptWithBase64(testRSAEnWith64);
            System.out.println("\nEncrypt: \n" + testRSAEnWith64);
            System.out.println("\nDecrypt: \n" + testRSADeWith64);
        } catch (Exception var7) {
            var7.printStackTrace();
        }

    }

    public RSAEncryptor(String publicKeyFilePath, String privateKeyFilePath) throws Exception {
        String public_key = this.getKeyFromFile(publicKeyFilePath);
        String private_key = this.getKeyFromFile(privateKeyFilePath);
        this.loadPublicKey(public_key);
        this.loadPrivateKey(private_key);
    }

    public RSAEncryptor() {
    }

    public String getKeyFromFile(String filePath) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String line = null;
        ArrayList list = new ArrayList();

        while((line = bufferedReader.readLine()) != null) {
            list.add(line);
        }

        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 1; i < list.size() - 1; ++i) {
            stringBuilder.append((String)list.get(i)).append("\r");
        }

        String key = stringBuilder.toString();
        return key;
    }

    public String decryptWithBase64(String base64String) throws Exception {
        byte[] binaryData = this.decrypt(this.getPrivateKey(), (new BASE64Decoder()).decodeBuffer(base64String));
        String string = new String(binaryData);
        return string;
    }

    public String encryptWithBase64(String string) throws Exception {
        byte[] binaryData = this.encrypt(this.getPublicKey(), string.getBytes());
        String base64String = (new BASE64Encoder()).encodeBuffer(binaryData);
        return base64String;
    }

    public static void setSharedInstance(RSAEncryptor rsaEncryptor) {
        sharedInstance = rsaEncryptor;
    }

    public RSAPrivateKey getPrivateKey() {
        return this.privateKey;
    }

    public RSAPublicKey getPublicKey() {
        return this.publicKey;
    }

    public void genKeyPair() {
        KeyPairGenerator keyPairGen = null;

        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException var3) {
            var3.printStackTrace();
        }

        keyPairGen.initialize(1024, new SecureRandom());
        KeyPair keyPair = keyPairGen.generateKeyPair();
        this.privateKey = (RSAPrivateKey)keyPair.getPrivate();
        this.publicKey = (RSAPublicKey)keyPair.getPublic();
    }

    public void loadPublicKey(InputStream in) throws Exception {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String readLine = null;
            StringBuilder sb = new StringBuilder();

            while((readLine = br.readLine()) != null) {
                if (readLine.charAt(0) != '-') {
                    sb.append(readLine);
                    sb.append('\r');
                }
            }

            this.loadPublicKey(sb.toString());
        } catch (IOException var5) {
            throw new Exception("公钥数据流读取错误");
        } catch (NullPointerException var6) {
            throw new Exception("公钥输入流为空");
        }
    }

    public void loadPublicKey(String publicKeyStr) throws Exception {
        try {
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] buffer = base64Decoder.decodeBuffer(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            this.publicKey = (RSAPublicKey)keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException var6) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException var7) {
            throw new Exception("公钥非法");
        } catch (IOException var8) {
            throw new Exception("公钥数据内容读取错误");
        } catch (NullPointerException var9) {
            throw new Exception("公钥数据为空");
        }
    }

    public void loadPrivateKey(InputStream in) throws Exception {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String readLine = null;
            StringBuilder sb = new StringBuilder();

            while((readLine = br.readLine()) != null) {
                if (readLine.charAt(0) != '-') {
                    sb.append(readLine);
                    sb.append('\r');
                }
            }

            this.loadPrivateKey(sb.toString());
        } catch (IOException var5) {
            throw new Exception("私钥数据读取错误");
        } catch (NullPointerException var6) {
            throw new Exception("私钥输入流为空");
        }
    }

    public void loadPrivateKey(String privateKeyStr) throws Exception {
        try {
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] buffer = base64Decoder.decodeBuffer(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            this.privateKey = (RSAPrivateKey)keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException var6) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException var7) {
            var7.printStackTrace();
            throw new Exception("私钥非法");
        } catch (IOException var8) {
            throw new Exception("私钥数据内容读取错误");
        } catch (NullPointerException var9) {
            throw new Exception("私钥数据为空");
        }
    }

    public byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData) throws Exception {
        if (publicKey == null) {
            throw new Exception("加密公钥为空, 请设置");
        } else {
            Cipher cipher = null;

            try {
                cipher = Cipher.getInstance("RSA");
                cipher.init(1, publicKey);
                byte[] output = cipher.doFinal(plainTextData);
                return output;
            } catch (NoSuchAlgorithmException var5) {
                throw new Exception("无此加密算法");
            } catch (NoSuchPaddingException var6) {
                var6.printStackTrace();
                return null;
            } catch (InvalidKeyException var7) {
                throw new Exception("加密公钥非法,请检查");
            } catch (IllegalBlockSizeException var8) {
                throw new Exception("明文长度非法");
            } catch (BadPaddingException var9) {
                throw new Exception("明文数据已损坏");
            }
        }
    }

    public byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData) throws Exception {
        if (privateKey == null) {
            throw new Exception("解密私钥为空, 请设置");
        } else {
            Cipher cipher = null;

            try {
                cipher = Cipher.getInstance("RSA");
                cipher.init(2, privateKey);
                byte[] output = cipher.doFinal(cipherData);
                return output;
            } catch (NoSuchAlgorithmException var5) {
                throw new Exception("无此解密算法");
            } catch (NoSuchPaddingException var6) {
                var6.printStackTrace();
                return null;
            } catch (InvalidKeyException var7) {
                throw new Exception("解密私钥非法,请检查");
            } catch (IllegalBlockSizeException var8) {
                throw new Exception("密文长度非法");
            } catch (BadPaddingException var9) {
                throw new Exception("密文数据已损坏");
            }
        }
    }

    public static String byteArrayToString(byte[] data) {
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < data.length; ++i) {
            stringBuilder.append(HEX_CHAR[(data[i] & 240) >>> 4]);
            stringBuilder.append(HEX_CHAR[data[i] & 15]);
            if (i < data.length - 1) {
                stringBuilder.append(' ');
            }
        }

        return stringBuilder.toString();
    }
}
