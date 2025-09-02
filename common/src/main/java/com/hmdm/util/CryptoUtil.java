package com.hmdm.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Component
@NoArgsConstructor
public class CryptoUtil {

    private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Generate MD5 hash from a string
     */
    public static String getMD5String(String value) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(value.getBytes(StandardCharsets.UTF_8));
            return getHexString(md.digest());
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate MD5 hash", e);
        }
    }

    /**
     * Convert byte array to hex string
     */
    public static String getHexString(byte[] digest) {
        char[] hexChars = new char[digest.length * 2];
        for (int i = 0; i < digest.length; i++) {
            int v = digest[i] & 0xFF;
            hexChars[i * 2] = HEX_ARRAY[v >>> 4];
            hexChars[i * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars).toUpperCase();
    }

    /**
     * Calculate MD5 checksum of an InputStream
     */
    public String calculateChecksum(InputStream fileContent) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("MD5");

        try (InputStream is = new BufferedInputStream(fileContent);
             DigestInputStream dis = new DigestInputStream(is, md)) {
            while (dis.read() != -1) { /* read stream for checksum */ }
        }

        byte[] digest = md.digest();
        BigInteger no = new BigInteger(1, digest);
        String hashText = no.toString(16);

        while (hashText.length() < 32) {
            hashText = "0" + hashText;
        }

        return hashText;
    }

    /**
     * Generate SHA-1 hash from a string
     */
    public static String getSHA1String(String value) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(value.getBytes(StandardCharsets.UTF_8));
            return getHexString(md.digest());
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate SHA-1 hash", e);
        }
    }

    /**
     * Generate a data signature using SHA-1 from secret + object
     */
    public static String getDataSignature(String hashSecret, Object data) {
        try {
            String json = objectMapper.writeValueAsString(data).replaceAll("\\s", "");
            return getSHA1String(hashSecret + json);
        } catch (Exception e) {
            log.error("Failed to generate data signature", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Verify SHA-1 signature
     */
    public static boolean checkRequestSignature(String signature, String value) {
        if (signature == null) return false;
        try {
            String expected = getSHA1String(value);
            return signature.equalsIgnoreCase(expected);
        } catch (Exception e) {
            log.warn("Signature validation failed", e);
            return false;
        }
    }

    /**
     * Generate a random 512-bit key as a hex string
     */
    public String generateHS512Key() {
        byte[] keyBytes = new byte[64]; // 512 bits
        new SecureRandom().nextBytes(keyBytes);
        StringBuilder hexString = new StringBuilder();
        for (byte b : keyBytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }

    /**
     * Generate a random hex string of given length
     */
    public static String randomHexString(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(HEX_ARRAY[random.nextInt(16)]);
        }
        return sb.toString();
    }
}
