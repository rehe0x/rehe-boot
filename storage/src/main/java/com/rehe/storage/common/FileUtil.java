package com.rehe.storage.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiech
 * @description
 * @date 2024/8/9
 */
public class FileUtil {
    public static List<byte[]> splitFile(String filePath, int chunkSize) throws IOException {
        File file = new File(filePath);
       return splitFile(file, chunkSize);
    }

    public static List<byte[]> splitFile(File file, int chunkSize) throws IOException {
        List<byte[]> chunks = new ArrayList<>();
        FileInputStream fis = new FileInputStream(file);

        byte[] buffer = new byte[chunkSize];
        int bytesRead;

        while ((bytesRead = fis.read(buffer)) != -1) {
            byte[] chunk = new byte[bytesRead];
            System.arraycopy(buffer, 0, chunk, 0, bytesRead);
            chunks.add(chunk);
        }

        fis.close();
        return chunks;
    }

    /**
     * 将 byte[] 数组按指定大小分段
     *
     * @param array     要分段的字节数组
     * @param chunkSize 每个分段的大小
     * @return 分段后的字节数组列表
     */
    public static List<byte[]> splitFile(byte[] array, int chunkSize) {
        List<byte[]> chunks = new ArrayList<>();
        int length = array.length;
        int startIndex = 0;

        while (startIndex < length) {
            int endIndex = Math.min(startIndex + chunkSize, length);
            byte[] chunk = new byte[endIndex - startIndex];
            System.arraycopy(array, startIndex, chunk, 0, chunk.length);
            chunks.add(chunk);
            startIndex += chunkSize;
        }

        return chunks;
    }

}
