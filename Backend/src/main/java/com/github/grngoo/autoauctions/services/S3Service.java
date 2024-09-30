package com.github.grngoo.autoauctions.services;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

/**
 * Handles CRUD operations regarding images in S3.
 *
 * @author danielmunteanu
 */
@Service
public class S3Service {

  private final S3Client s3Client;
  private final S3Presigner s3Presigner;
  private final String bucketName;

  /**
   * Constructs S3Service. Using data from application properties.
   *
   * @param bucketName name of S3Bucket.
   * @param region where bucket is hosted.
   * @param accessKeyId public access credential.
   * @param secretAccessKey private access credential.
   */
  public S3Service(
      @Value("${aws.s3.bucket-name}") String bucketName,
      @Value("${aws.s3.region}") String region,
      @Value("${aws.s3.access-key-id}") String accessKeyId,
      @Value("${aws.s3.secret-access-key}") String secretAccessKey) {
    this.bucketName = bucketName;
    this.s3Client =
        S3Client.builder()
            .region(Region.of(region))
            .credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(accessKeyId, secretAccessKey)))
            .build();
    this.s3Presigner =
        S3Presigner.builder()
            .region(Region.of(region))
            .credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(accessKeyId, secretAccessKey)))
            .build();
  }

  /**
   * Add a new image file to S3.
   *
   * @param listingId key for listing associated with image. Acts as key for S3 object.
   * @param multipartFile the image file to be uploaded.
   */
  public void uploadFile(long listingId, MultipartFile multipartFile) {
    try {
      PutObjectRequest putObjectRequest =
          PutObjectRequest.builder().bucket(bucketName).key(String.valueOf(listingId)).build();
      s3Client.putObject(
          putObjectRequest,
          software.amazon.awssdk.core.sync.RequestBody.fromInputStream(
              multipartFile.getInputStream(), multipartFile.getSize()));

    } catch (S3Exception | IOException e) {
      throw new RuntimeException("Failed to upload file: " + e.getMessage(), e);
    }
  }

  /**
   * Get the URL for the image file.
   *
   * @param listingId key for listing associated with image.
   * @return the URL for image.
   */
  public URL getFileUrl(Long listingId) {
    try {
      GetObjectRequest getObjectRequest =
          GetObjectRequest.builder().bucket(bucketName).key(String.valueOf(listingId)).build();
      PresignedGetObjectRequest presignedRequest =
          s3Presigner.presignGetObject(
              builder ->
                  builder
                      .getObjectRequest(getObjectRequest)
                      .signatureDuration(Duration.ofMinutes(60)));

      return presignedRequest.url();
    } catch (S3Exception e) {
      throw new RuntimeException(
          "Failed to get file URL: " + e.awsErrorDetails().errorMessage(), e);
    }
  }

  /**
   * Delete a file from S3.
   *
   * @param listingId key for listing associated with image.
   */
  public void deleteFile(Long listingId) {
    try {
      DeleteObjectRequest deleteObjectRequest =
          DeleteObjectRequest.builder().bucket(bucketName).key(String.valueOf(listingId)).build();
      s3Client.deleteObject(deleteObjectRequest);
    } catch (S3Exception e) {
      throw new RuntimeException("Failed to delete file: " + e.awsErrorDetails().errorMessage(), e);
    }
  }
}
