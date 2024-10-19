package com;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.Chunker.Chunk;

public class MainWithParellelConcept {

	public static void main(String args[]) throws InterruptedException, ExecutionException {
		long startTime = System.currentTimeMillis();
		Chunker chunker = new Chunker();
		String filePath = "I:\\Other Workspace\\test.pdf";

		try {
			List<Chunker.Chunk> chunks = chunker.chunkFile(filePath);
			System.out.println("Files chunked into " + chunks.size() + "parts..");

			// Create a thread pools for parellel uploads.
			ExecutorService exectorService = Executors.newFixedThreadPool(10);
			List<Future<?>> futures = new ArrayList();

			// simulate uploading each chunk
			for (Chunker.Chunk chunk : chunks) {
				Future<?> future = exectorService.submit(() -> uploadChunk(chunk));
				futures.add(future);
			}

			// Wait for all uploads to complete
			for (Future future : futures) {
				future.get();
			}

		} catch (IOException | NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}

		System.out.println("File Uploaded successfully...");
		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime;
		System.out.println(duration + " ms");
	}

	private static void uploadChunk(Chunk chunk) {
		System.out.println("Uploading chunk " + chunk.getIndex() + " with hash " + chunk.getHash());
	}

}
