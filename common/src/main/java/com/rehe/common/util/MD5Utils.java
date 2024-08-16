package com.rehe.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author xiech
 * @description
 * @date 2024/8/12
 */
public class MD5Utils {
    private static final char[] hex = "0123456789abcdef".toCharArray();

    public static String hashToHexString(byte[] input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return toHexString(md.digest(input));
        } catch (NoSuchAlgorithmException var2) {
            NoSuchAlgorithmException e = var2;
            throw new IllegalStateException(e);
        }
    }

    private static String toHexString(byte[] bytes) {
        if (null == bytes) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder(bytes.length << 1);
            byte[] var2 = bytes;
            int var3 = bytes.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                byte aByte = var2[var4];
                sb.append(hex[(aByte & 240) >> 4]).append(hex[aByte & 15]);
            }

            return sb.toString();
        }
    }
}
