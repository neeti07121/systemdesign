package com;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class MainWithSerialization {
	public static void main(String args[]) {
		Chunker chunker = new Chunker();
		String filePath = "I:\\\\Other Workspace\\\\test.pdf"; // Change this to your file path

		try {
			List<Chunker.Chunk> chunks = chunker.chunkFile(filePath);
			System.out.println("File chunked into " + chunks.size() + " parts.");

			// Upload each chunk
			for (Chunker.Chunk chunk : chunks) {
				uploadChunk(chunk);
			}

			System.out.println("All chunks uploaded successfully!");

		} catch (IOException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		System.out.println("All chunks uploaded successfully!");
	}

	// Simulated upload method
	private static void uploadChunk(Chunker.Chunk chunk) {
		// Here you would implement your upload logic to the cloud
		// For demonstration, we just serialize the chunk to a file
		serializeChunk(chunk, "chunk_" + chunk.getIndex() + ".ser");
	}

	// Method to serialize a chunk
	private static void serializeChunk(Chunker.Chunk chunk, String filePath) {
		try (FileOutputStream fileOut = new FileOutputStream(filePath);
				ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
			out.writeObject(chunk);
			System.out.println("Serialized chunk with index: " + chunk.getIndex());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
