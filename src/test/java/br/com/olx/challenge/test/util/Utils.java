package br.com.olx.challenge.test.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

	public static String randomPassword() {
		String letters = "abcdefghijklmnopqrstuvwxyz";
		int random;
		String result = "";

		for (int i = 1; i <= 10; i++) {
			random = (int) (Math.random() * (letters.length() - 1));
			result += letters.charAt(random);
		}

		return result;
	}

	public static String convertStringToMd5(String value) {
		MessageDigest mDigest;
		try {
			mDigest = MessageDigest.getInstance("MD5");

			byte[] valueMD5 = mDigest.digest(value.getBytes("UTF-8"));

			StringBuffer sb = new StringBuffer();
			for (byte b : valueMD5) {
				sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1,
						3));
			}

			return sb.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
}
