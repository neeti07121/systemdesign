package com;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.Chunker.Chunk;

public class Main {
	public static void main(String args[]) {
		long startTime = System.currentTimeMillis();
		Chunker chunker = new Chunker();
		String filePath = "I:\\Other Workspace\\test.pdf";

		try {
			List<Chunker.Chunk> chunks = chunker.chunkFile(filePath);
			System.out.println("Files chunked into " + chunks.size() + "parts..");

			// simulate uploading each chunk
			for (Chunker.Chunk chunk : chunks) {
				uploadChunk(chunk);
			}

		} catch (IOException | NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}

		System.out.println("File Uploaded successfully...");
		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime;
		System.out.println(duration+" ms");
	}

	private static void uploadChunk(Chunk chunk) {
		System.out.println("Uploading chunk " + chunk.getIndex() + " with hash " + chunk.getHash());
	}

}
