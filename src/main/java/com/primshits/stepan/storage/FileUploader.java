package com.primshits.stepan.storage;

import io.minio.*;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor
public class FileUploader {
    private final MinioClient minioClient;

    public void createBucket(String bucketName) throws ServerException, InsufficientDataException,
            ErrorResponseException, IOException, NoSuchAlgorithmException,
            InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        if (!doesBucketExist(bucketName)) {
            minioClient.makeBucket(
                    MakeBucketArgs
                            .builder()
                            .bucket(bucketName)
                            .build());
        }
    }

    public void putObject(long objectSize, long partSize, String objectName,
                          String bucketName, FileInputStream fileInputStream)
            throws IOException, ServerException, InsufficientDataException, ErrorResponseException,
            NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException,
            XmlParserException, InternalException {
        minioClient.putObject(PutObjectArgs
                .builder()
                .bucket(bucketName)
                .object(objectName)
                .stream(fileInputStream,objectSize,partSize)
                .build());
    }

    public InputStream getObject(String bucketName,String objectName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException,
            NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException,
            XmlParserException, InternalException {
        return minioClient.getObject(GetObjectArgs
                             .builder()
                             .bucket(bucketName)
                             .object(objectName)
                             .build());
    }

    private boolean doesBucketExist(String bucketName) throws ServerException, InsufficientDataException,
            ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException,
            InvalidResponseException, XmlParserException, InternalException {
        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    }
}

