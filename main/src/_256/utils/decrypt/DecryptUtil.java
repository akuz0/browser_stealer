package _256.utils.decrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DecryptUtil {

	private static final int KEY_LENGTH = 256 / 8;
	private static final int IV_LENGTH = 96 / 8;
	private static final int GCM_TAG_LENGTH = 16;

	public static final byte[] getDecryptBytes(byte[] inputBytes, byte[] keyBytes, byte[] ivBytes) {
		try {
			if (inputBytes == null) {
				throw new IllegalArgumentException();
			}
			if (keyBytes == null) {
				throw new IllegalArgumentException();
			}
			if (keyBytes.length != KEY_LENGTH) {
				throw new IllegalArgumentException();
			}

			if (ivBytes == null) {
				throw new IllegalArgumentException();
			}
			if (ivBytes.length != IV_LENGTH) {
				throw new IllegalArgumentException();
			}
			Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
			SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
			GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, ivBytes);
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, gcmParameterSpec);
			return cipher.doFinal(inputBytes);
		} catch (Exception ex) {
			return null;
		}
	}
}
