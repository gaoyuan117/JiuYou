/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014. Ray
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.jiuyou.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {
	private Cipher ecipher;

	public Crypto(String key) {
		try {
			SecretKeySpec skey = new SecretKeySpec(generateKey(key), "AES");
			setupCrypto(skey);
		} catch (Exception e) {
			Log.e("Crypto", e);
		}
	}

	private void setupCrypto(SecretKey key) {
		byte[] iv = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f };
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
		try {
			ecipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
		} catch (Exception e) {
			ecipher = null;
			Log.e("setupCrypto", e);
		}
	}

	public String encrypt(String plaintext) {
		if (ecipher == null)
			return "";

		try {
			byte[] ciphertext = ecipher.doFinal(plaintext.getBytes("UTF-8"));
			return Base_64.encodeToString(ciphertext, Base64.NO_WRAP);
		} catch (Exception e) {
			Log.e("encryp", e);
			return "";
		}
	}

	public static String md5(String plain) {
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.update(plain.getBytes());
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1, digest);
			String hashtext = bigInt.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (Exception e) {
			return "";
		}
	}

	private static byte[] generateKey(String input) {
		try {
			byte[] bytesOfMessage = input.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("SHA256");
			return md.digest(bytesOfMessage);
		} catch (Exception e) {
			Log.e("generateKey", e);
			return null;
		}
	}

	private PublicKey readKeyFromStream(InputStream keyStream) throws IOException {
		ObjectInputStream oin = new ObjectInputStream(new BufferedInputStream(keyStream));
		try {
			PublicKey pubKey = (PublicKey) oin.readObject();
			return pubKey;
		} catch (Exception e) {
			Log.e("readKeyFromStream", e);
			return null;
		} finally {
			oin.close();
		}
	}

	public String rsaEncrypt(InputStream keyStream, String data) {
		try {
			return rsaEncrypt(keyStream, data.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	public String rsaEncrypt(InputStream keyStream, byte[] data) {
		try {
			PublicKey pubKey = readKeyFromStream(keyStream);
			Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			byte[] cipherData = cipher.doFinal(data);
			return Base_64.encodeToString(cipherData, Base64.NO_WRAP);
		} catch (Exception e) {
			Log.e("rsaEncrypt", e);
			return "";
		}
	}

}
