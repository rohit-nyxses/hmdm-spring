//package com.hmdm.util;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.BufferedInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.math.BigInteger;
//import java.nio.charset.StandardCharsets;
//import java.security.DigestInputStream;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.util.Base64;
//import java.util.Random;
//
//public final class CryptoUtil {
//
//    private static final Logger logger = LoggerFactory.getLogger(CryptoUtil.class);
//
//    private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();
//
//    private CryptoUtil() {
//        // Utility class
//    }
//
//    public static String getMD5String(String value) {
//        return hash(value, "MD5");
//    }
//
//    public static String getSHA1String(String value) {
//        return hash(value, "SHA-1");
//    }
//
//    private static String hash(String value, String algorithm) {
//        try {
//            MessageDigest md = MessageDigest.getInstance(algorithm);
//            byte[] digest = md.digest(value.getBytes(StandardCharsets.UTF_8));
//            return toHexString(digest).toUpperCase();
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException("Hash algorithm not found: " + algorithm, e);
//        }
//    }
//
//    public static String toHexString(byte[] digest) {
//        char[] hexChars = new char[digest.length * 2];
//        for (int i = 0; i < digest.length; i++) {
//            int v = digest[i] & 0xFF;
//            hexChars[i * 2] = HEX_ARRAY[v >>> 4];
//            hexChars[i * 2 + 1] = HEX_ARRAY[v & 0x0F];
//        }
//        return new String(hexChars);
//    }
//
//    public static String getBase64String(byte[] digest) {
//        return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
//    }
//
//    public static String calculateChecksum(InputStream fileContent) throws IOException {
//        try {
//            MessageDigest md = MessageDigest.getInstance("MD5");
//
//            try (InputStream is = new BufferedInputStream(fileContent);
//                 DigestInputStream dis = new DigestInputStream(is, md)) {
//                while (dis.read() != -1) {
//                    // reading stream to EOF
//                }
//            }
//
//            byte[] digest = md.digest();
//            BigInteger no = new BigInteger(1, digest);
//            String hashtext = no.toString(16);
//
//            // Pad with leading 0s to make it 32 chars
//            while (hashtext.length() < 32) {
//                hashtext = "0" + hashtext;
//            }
//
//            return hashtext;
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException("MD5 algorithm not available", e);
//        }
//    }
//
//    public static String getDataSignature(String hashSecret, Object data) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            String json = objectMapper.writeValueAsString(data).replaceAll("\\s", "");
//            return getSHA1String(hashSecret + json);
//        } catch (Exception e) {
//            logger.error("Error while generating data signature", e);
//            return "";
//        }
//    }
//
//    public static boolean checkRequestSignature(String signature, String value) {
//        if (signature == null || value == null) {
//            return false;
//        }
//
//        try {
//            String expectedSignature = getSHA1String(value);
//            return signature.equalsIgnoreCase(expectedSignature);
//        } catch (Exception e) {
//            logger.error("Error while checking request signature", e);
//            return false;
//        }
//    }
//
//    public static String randomHexString(int length) {
//        StringBuilder sb = new StringBuilder(length);
//        Random random = new Random();
//
//        for (int i = 0; i < length; i++) {
//            sb.append(HEX_ARRAY[random.nextInt(16)]);
//        }
//
//        return sb.toString();
//    }
//}
//
////second version
////package com.hmdm.util;
////
////import com.fasterxml.jackson.databind.ObjectMapper;
////import org.slf4j.Logger;
////import org.slf4j.LoggerFactory;
////
////import java.io.BufferedInputStream;
////import java.io.IOException;
////import java.io.InputStream;
////import java.math.BigInteger;
////import java.nio.charset.StandardCharsets;
////import java.security.DigestInputStream;
////import java.security.MessageDigest;
////import java.security.NoSuchAlgorithmException;
////import java.util.Base64;
////import java.util.Random;
////
////public final class CryptoUtil {
////
////    private static final Logger logger = LoggerFactory.getLogger(CryptoUtil.class);
////
////    private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();
////
////    private CryptoUtil() {
////        // Utility class
////    }
////
////    public static String getMD5String(String value) {
////        return hash(value, "MD5");
////    }
////
////    public static String getSHA1String(String value) {
////        return hash(value, "SHA-1");
////    }
////
////    private static String hash(String value, String algorithm) {
////        try {
////            MessageDigest md = MessageDigest.getInstance(algorithm);
////            byte[] digest = md.digest(value.getBytes(StandardCharsets.UTF_8));
////            return toHexString(digest); // ensure lowercase
////        } catch (NoSuchAlgorithmException e) {
////            throw new RuntimeException("Hash algorithm not found: " + algorithm, e);
////        }
////    }
////
////    public static String toHexString(byte[] digest) {
////        char[] hexChars = new char[digest.length * 2];
////        for (int i = 0; i < digest.length; i++) {
////            int v = digest[i] & 0xFF;
////            hexChars[i * 2] = HEX_ARRAY[v >>> 4];
////            hexChars[i * 2 + 1] = HEX_ARRAY[v & 0x0F];
////        }
////        return new String(hexChars);
////    }
////
////    public static String getBase64String(byte[] digest) {
////        return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
////    }
////
////    public static String calculateChecksum(InputStream fileContent) throws IOException {
////        try {
////            MessageDigest md = MessageDigest.getInstance("MD5");
////
////            try (InputStream is = new BufferedInputStream(fileContent);
////                 DigestInputStream dis = new DigestInputStream(is, md)) {
////                while (dis.read() != -1) {
////                    // reading stream to EOF
////                }
////            }
////
////            byte[] digest = md.digest();
////            BigInteger no = new BigInteger(1, digest);
////            String hashtext = no.toString(16);
////
////            // Pad with leading 0s to make it 32 chars
////            while (hashtext.length() < 32) {
////                hashtext = "0" + hashtext;
////            }
////
////            return hashtext;
////        } catch (NoSuchAlgorithmException e) {
////            throw new RuntimeException("MD5 algorithm not available", e);
////        }
////    }
////
////    public static String getDataSignature(String hashSecret, Object data) {
////        ObjectMapper objectMapper = new ObjectMapper();
////        try {
////            String json = objectMapper.writeValueAsString(data).replaceAll("\\s", "");
////            return getSHA1String(hashSecret + json);
////        } catch (Exception e) {
////            logger.error("Error while generating data signature", e);
////            return "";
////        }
////    }
////
////    public static boolean checkRequestSignature(String signature, String value) {
////        if (signature == null || value == null) {
////            return false;
////        }
////
////        try {
////            String expectedSignature = getSHA1String(value);
////            return signature.equalsIgnoreCase(expectedSignature);
////        } catch (Exception e) {
////            logger.error("Error while checking request signature", e);
////            return false;
////        }
////    }
////
////    public static String randomHexString(int length) {
////        StringBuilder sb = new StringBuilder(length);
////        Random random = new Random();
////
////        for (int i = 0; i < length; i++) {
////            sb.append(HEX_ARRAY[random.nextInt(16)]);
////        }
////
////        return sb.toString();
////    }
////}


// spring managed bean
package com.hmdm.util;

import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.common.io.BaseEncoding;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

@Component
public class CryptoUtil {

    private static final char[] hexArray = "0123456789abcdef".toCharArray();

    // Constructor for Spring (you can inject other dependencies here in future)
    public CryptoUtil() {
    }

    public String getMD5String(String value) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(value.getBytes(StandardCharsets.UTF_8));
            byte[] digest = md.digest();
            return getHexString(digest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getHexString(byte[] digest) {
        char[] hexChars = new char[digest.length * 2];

        for (int i = 0; i < digest.length; ++i) {
            int v = digest[i] & 255;
            hexChars[i * 2] = hexArray[v >>> 4];
            hexChars[i * 2 + 1] = hexArray[v & 15];
        }

        return new String(hexChars).toUpperCase();
    }

//    public String getBase64String(byte[] digest) {
//        return BaseEncoding.base64Url().encode(digest);
//    }

    public String calculateChecksum(InputStream fileContent) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("MD5");

        try (InputStream is = new BufferedInputStream(fileContent);
             DigestInputStream dis = new DigestInputStream(is, md)) {
            while (dis.read() != -1) {
                // Stream read for checksum
            }
        }

        byte[] digest = md.digest();
        BigInteger no = new BigInteger(1, digest);
        String hashtext = no.toString(16);

        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }

        return hashtext;
    }

    public String getSHA1String(String value) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(value.getBytes());
            byte[] digest = md.digest();

            char[] hexChars = new char[digest.length * 2];
            for (int i = 0; i < digest.length; i++) {
                int v = digest[i] & 0xFF;
                hexChars[i * 2] = hexArray[v >>> 4];
                hexChars[i * 2 + 1] = hexArray[v & 0x0F];
            }
            String result = new String(hexChars).toUpperCase();
//            System.out.println("🔐 SHA1 result = " + result);  // ✅ log the output
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getDataSignature(String hashSecret, Object data) {
        ObjectMapper objectMapper = new ObjectMapper();
        String s = "";
        try {
            s = objectMapper.writeValueAsString(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        s = s.replaceAll("\\s", "");
        return getSHA1String(hashSecret + s);
    }

    public boolean checkRequestSignature(String signature, String value) {
        if (signature == null) {
            return false;
        }
        try {
            String goodSignature = getSHA1String(value);
            return signature.equalsIgnoreCase(goodSignature);
        } catch (Exception e) {
            return false;
        }
    }

    public String generateHS512Key() {
        byte[] keyBytes = new byte[64]; // 512 bits = 64 bytes
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(keyBytes);

        StringBuilder hexString = new StringBuilder();
        for (byte b : keyBytes) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString(); // 128-character hex string
    }
    public String randomHexString(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            sb.append(hexArray[random.nextInt(16)]);
        }

        return sb.toString();
    }
}
