package com.rehe.common.util;

/**
 * @author xiech
 * @description
 * @date 2024/8/12
 */
public class HexUtils {
    private static final char[] hex = "0123456789abcdef".toCharArray();
    public static String toHexString(byte[] bytes) {
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
