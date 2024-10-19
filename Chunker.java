package com;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Chunker {
	private static final int CHUNK_SIZE = 1024 * 1024; // 1 MB Chunk Size

	// Method to chunk the file
	public List<Chunk> chunkFile(String filePath) throws FileNotFoundException, IOException, NoSuchAlgorithmException {
		File file = new File(filePath);
		long fileSize = file.length();
		List<Chunk> chunks = new ArrayList<>();

		try (FileInputStream fis = new FileInputStream(file)) {
			byte[] buffer = new byte[CHUNK_SIZE];
			int bytesRead;
			int chunkIndex = 0;

			while ((bytesRead = fis.read(buffer)) != -1) {
				byte[] chunkData = new byte[bytesRead];
				System.arraycopy(buffer, 0, chunkData, 0, bytesRead);
				String chunkHash = calculateHash(chunkData);
				chunks.add(new Chunk(chunkIndex++, chunkData, chunkHash));
			}
		}

		return chunks;
	}

	// Method to calculate the hash of a chunk
	private String calculateHash(byte[] data) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] hash = md.digest(data);

		StringBuilder hexString = new StringBuilder();

		for (byte b : hash) {
			String hex = Integer.toHexString(0xff & b);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}

		return hexString.toString();
	}

	// Inner class to represent the Chunk
	static class  Chunk implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private final int index;
		private final byte[] data;
		private final String hash;

		public Chunk(int index, byte[] data, String hash) {
			this.index = index;
			this.data = data;
			this.hash = hash;
		}

		public int getIndex() {
			return index;
		}

		public byte[] getData() {
			return data;
		}

		public String getHash() {
			return hash;
		}

	}
}
