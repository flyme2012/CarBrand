package com.m.car2.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtils {

    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
    private static final int EOF = -1;

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy(input, output);
        return output.toByteArray();
    }

    public static byte[] toByteArray(InputStream input, long size) throws IOException {

        if (size > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Size cannot be greater than Integer max value: " + size);
        }

        return toByteArray(input, (int) size);
    }

    public static byte[] toByteArray(InputStream input, int size) throws IOException {

        if (size < 0) {
            throw new IllegalArgumentException("Size must be equal or greater than zero: " + size);
        }

        if (size == 0) {
            return new byte[0];
        }

        byte[] data = new byte[size];
        int offset = 0;
        int readed;

        while (offset < size && (readed = input.read(data, offset, size - offset)) != EOF) {
            offset += readed;
        }

        if (offset != size) {
            throw new IOException("Unexpected readed size. current: " + offset + ", excepted: " + size);
        }

        return data;
    }



    public static int copy(InputStream input, OutputStream output) throws IOException {
        long count = copyLarge(input, output);
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int) count;
    }

    public static long copy(File input, File output) throws IOException {
        output.delete();
        output.createNewFile();
        InputStream in = new FileInputStream(input);
        OutputStream out = new FileOutputStream(output);
        try {
            return copyLarge(in, out);
        } finally {
            in.close();
            out.close();
        }
    }

    public static long copyLarge(InputStream input, OutputStream output) throws IOException {
        return copyLarge(input, output, new byte[DEFAULT_BUFFER_SIZE]);
    }

    public static long copyLarge(InputStream input, OutputStream output, byte[] buffer) throws IOException {
        long count = 0;
        int n = 0;
        while (EOF != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    public static void closeQuietly(InputStream input) {
        closeQuietly((Closeable) input);
    }

    public static void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
    }

    public static int readFully(InputStream input, byte[] buffer) throws IOException {
        int position = 0, length = buffer.length, bytesRead;
        while (length > 0 && (bytesRead = input.read(buffer, position, length)) > 0) {
            position += bytesRead;
            length -= bytesRead;
        }

        return position;
    }

    public static Bitmap getCountdownImg(Context context, String fileName) {
        return BitmapFactory.decodeFile(context.getFileStreamPath(fileName).getPath());
    }

    public static void deleteCountdownImg(Context context, String fileName) {
        File file = new File(context.getFilesDir(), fileName);
        if (file != null && file.exists()) {
            file.delete();
        }
    }

    /**
     * 获取SD卡中的内存的总数和SD卡已经使用的内存总数 返回值为数组
     * 数组第一个值为内存总数（字节），第二个值为SD卡已经使用的内存总数（字节），第三个值为使用率（0-100）
     */
    public static long[] getSDInfo() {
        long result[] = new long[]{0, 0, 0};
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File path = Environment.getExternalStorageDirectory();
            // fix for #274
            try {
                StatFs statfs = new StatFs(path.getPath());
                // 获取block的SIZE
                long blockSize = statfs.getBlockSize();
                // 获取BLOCK数量
                long totalBlocks = statfs.getBlockCount();
                // 己使用的Block的数量
                long availBlocks = statfs.getAvailableBlocks();

                result[0] = (totalBlocks * blockSize);
                result[1] = ((totalBlocks - availBlocks) * blockSize);
                // fix for error:211617
                result[2] = (totalBlocks == 0) ? 0 : (100 - availBlocks * 100 / totalBlocks);
            } catch (Exception e) {
            }
        }
        return result;
    }
}
