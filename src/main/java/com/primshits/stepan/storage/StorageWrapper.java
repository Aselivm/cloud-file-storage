package com.primshits.stepan.storage;

import java.io.InputStream;

public interface StorageWrapper {
    void createBucket(String bucketName);
    void putObject(long objectSize, long partSizeInMegabytes, String objectName,
                   String bucketName, InputStream inputStream) ;
    InputStream getObject(String bucketName, String objectName);
}
