package com.m.car2.utility;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by zhenyu on 15/11/13.
 */
public class FileHelper {


    public static boolean isEmpty(byte[] data) {
        if (data != null && data.length > 0) {
            return false;
        } else {
            return true;
        }
    }


    public static boolean saveDataToInternalFile(Context context, String fileName, byte[] data) {
        FileOutputStream fos = null;
        boolean saved = false;
        try {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(data);
            fos.flush();
            saved = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                IOUtils.closeQuietly(fos);
            }
        }
        return saved;
    }


    public static byte[] readDataFromInternalFile(Context context, String fileName) {
        byte[] data = null;
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(fileName);
            data = IOUtils.toByteArray(fis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                IOUtils.closeQuietly(fis);
            }
        }
        return data;
    }

    public static boolean deleteInternalFile(Context context, String fileName) {
        File file = new File(context.getFilesDir(), fileName);
        if (file.exists()) {
            return file.delete();
        } else {
            return true;
        }
    }

    public static long getInternalFileLastModifiedTime(Context context, String fileName) {
        File file = new File(context.getFilesDir(), fileName);
        return file.lastModified();
    }

    public static byte[] readDataFromSDCardRootFile(String fileName) {
        FileInputStream fis = null;
        byte[] data = null;
        File file;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                file = new File(Environment.getExternalStorageDirectory(),
                        fileName);
                if(!file.exists())
                    return null;
                fis = new FileInputStream(file);
                data = IOUtils.toByteArray(fis);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fis != null) {
                    IOUtils.closeQuietly(fis);
                }
            }
        }
        return data;
    }
}
