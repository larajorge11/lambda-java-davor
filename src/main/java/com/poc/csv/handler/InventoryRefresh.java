package com.poc.csv.handler;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.event.S3EventNotification;
import com.amazonaws.services.s3.model.S3Object;
import com.opencsv.exceptions.CsvException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static com.poc.csv.handler.CacheData.storeCache;
import static com.poc.csv.handler.CsvInventory.readCsvFile;

public class InventoryRefresh implements RequestHandler<S3Event, Void> {

	private static LambdaLogger logger;

	@Override
	public Void handleRequest(S3Event event, Context context) {
		logger = context.getLogger();
		logger.log("EVENT: Hello from lambda csv");

		final AmazonS3 amazonS3Client = getAmazonS3Client();

		event.getRecords().stream()
				.forEach(record -> {
					S3EventNotification.S3Entity s3Entity = record.getS3();
					String key = record.getS3().getObject().getKey();
					String bucketName = s3Entity.getBucket().getName();
					logger.log(String.format("My Bucket Name: %1$s, and the key: %2$s", bucketName, key));

					try (S3Object s3Object = amazonS3Client.getObject(bucketName, key);
						 final InputStreamReader streamReader = new InputStreamReader(s3Object.getObjectContent(), StandardCharsets.UTF_8);
						 final BufferedReader reader = new BufferedReader(streamReader)) {

						storeCache.accept(readCsvFile(reader), logger);

					} catch (IOException | CsvException e) {
						logger.log(e.getMessage());
					}
				});

		return null;
	}

	private AWSCredentials getAmazonCredentials() {
		return new BasicAWSCredentials(System.getenv("aws_access_key_id"),
				System.getenv("aws_secret_access_key"));
	}

	private AmazonS3 getAmazonS3Client() {
		return AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(getAmazonCredentials()))
				.withRegion(Regions.EU_WEST_2)
				.build();
	}

}

