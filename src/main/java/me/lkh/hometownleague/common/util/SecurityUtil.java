package me.lkh.hometownleague.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtil {

    private static final String hashAlgorithm = "SHA-256";

    public static String hashEncrypt(String rawData) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(hashAlgorithm);
        md.update(rawData.getBytes());

        return bytesToHex(md.digest());
    }

    private static String bytesToHex(byte[] bytes){
        StringBuilder stringBuilder = new StringBuilder();
        for(byte b : bytes) {
            stringBuilder.append(String.format("%02x", b));
        }
        return stringBuilder.toString();
    }
}
