package com.m.car2.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

public class MessageDigestUtil {

	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	public static String getMD5String(byte[] bytes) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(bytes);
			return bufferToHex(digest.digest());
		} catch (Throwable e) {
			return null;
		}
	}

	public static String getFileMD5String(File file) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			InputStream fis;
			fis = new FileInputStream(file);
			byte[] buffer = new byte[8192];
			int numRead = 0;
			while ((numRead = fis.read(buffer)) > 0) {
				digest.update(buffer, 0, numRead);
			}
			fis.close();
			return bufferToHex(digest.digest());
		} catch (Throwable e) {
			return null;
		}
	}

	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = HEX_DIGITS[(bt & 0xf0) >> 4];
		char c1 = HEX_DIGITS[bt & 0xf];// 取字节中低4位的数字转换
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}
}
