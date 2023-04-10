package com.pkh.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAUtil {
    private static JSONObject keyMap=new JSONObject();

    static {
        keyMap.put("ANTPOOL_PUBLIC_KEY","MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAk+89V7vpOj1rG6bTAKYM56qmFLwNCBVDJ3MltVVtxVUUByqc5b6u909MmmrLBqS//PWC6zc3wZzU1+ayh8xbUAEZuA3EjlPHIaFIVIz04RaW10+1xnby/RQE23tDqsv9a2jv/axjE/27b62nzvCWeItu1kNQ3MGdcuqKjke+LKhQ7nWPRCOd/ffVqSuRvG0YfUEkOz/6UpsPr6vrI331hWRB4DlYy8qFUmDsyvvExe4NjZWblXCqkEXRRAhi2SQRCl3teGuIHtDUxCskRIDiaMD+Qt2Yp+Vvbz6hUiqIWSIH1BoHJer/JOq2/O6X3cmuppU4AdVNgy8Bq236iXvrMQIDAQAB");
    }

    private static String getPublicKey(String keyName) {
        return keyMap.getString(keyName);
    }

    /**
     * RSA公钥加密
     *
     * @param str       加密字符串
     * @param publicKeyName   公钥名称
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(String str, String publicKeyName) throws Exception {
        String publicKey= getPublicKey(publicKeyName);
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));

        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        byte[] codeByte=cipher.doFinal(str.getBytes("UTF-8"));
        String outStr = Base64.encodeBase64String(codeByte);
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param str        加密字符串
     * @param privateKeyName 私钥名称
     * @return 铭文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKeyName) throws Exception {
        //64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKeyName);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }
}

