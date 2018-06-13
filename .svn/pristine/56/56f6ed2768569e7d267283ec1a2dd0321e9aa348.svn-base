//package com.zte.pl.invoicecheck.gaopeng;
//
///**
// * Created by Administrator on 2017/11/11 0011.
// */
//import java.security.KeyFactory;
//import java.security.KeyPair;
//import java.security.KeyPairGenerator;
//import java.security.NoSuchAlgorithmException;
//import java.security.PrivateKey;
//import java.security.PublicKey;
//import java.security.SecureRandom;
//import java.security.spec.PKCS8EncodedKeySpec;
//import java.security.spec.X509EncodedKeySpec;
//
//
///**
// * RSA签名验签类
// */
//public class Signature {
//
//    /**
//     * 签名算法
//     */
//    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";
//    public static final String ENCODING = "UTF-8";
//    public static final String SIGN_ALGORITHM256 = "SHA256withRSA";
//
//
//    /**
//     * 随机生成密钥对
//     */
//    public static KeyPair genKeyPair(int keysize) {
//        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
//        KeyPairGenerator keyPairGen = null;
//        try {
//            keyPairGen = KeyPairGenerator.getInstance("RSA");
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        // 初始化密钥对生成器，密钥大小为96-1024位
//        keyPairGen.initialize(keysize,new SecureRandom());
//        // 生成一个密钥对，保存在keyPair中
//        KeyPair keyPair = keyPairGen.generateKeyPair();
//
//        return keyPair;
//    }
//
//    /**
//     * RSA签名
//     * @param content 待签名数据
//     * @param privateKeyString 私钥
//     *  @param signType 签名方式RSA/RSA2 参考static变量
//     * @return 签名值
//     */
//    public static String sign(String content, String privateKeyString, String signType)
//    {
//        try
//        {
//
//            PKCS8EncodedKeySpec priPKCS8    = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyString));
//            KeyFactory keyf                 = KeyFactory.getInstance("RSA");
//            PrivateKey priKey               = keyf.generatePrivate(priPKCS8);
//
//            java.security.Signature signature = java.security.Signature.getInstance(signType);
//
//            signature.initSign(priKey);
//            signature.update( content.getBytes(ENCODING));
//
//            byte[] signed = signature.sign();
//
//            return Base64.getEncoder().encodeToString(signed);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//
//    /**
//     * RSA/RSA2验签名检查
//     * @param content 待签名数据
//     * @param sign 签名值
//     * @param publicKeyString 公钥
//     * @param signType 签名方式RSA/RSA2 参考static变量
//     * @return 布尔值
//     */
//    public static boolean verify(String content, String sign, String publicKeyString, String signType)
//    {
//        if(content == null || sign == null || publicKeyString == null){
//            return false;
//        }
//        try
//        {
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            byte[] encodedKey = Base64.getDecoder().decode(publicKeyString);
//            PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
//
//
//            java.security.Signature signature = java.security.Signature
//                    .getInstance(signType);
//
//            signature.initVerify(publicKey);
//            signature.update( content.getBytes(ENCODING) );
//
//            boolean bverify = signature.verify(Base64.getDecoder().decode(sign) );
//            return bverify;
//
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//
//        return false;
//    }
//
//}