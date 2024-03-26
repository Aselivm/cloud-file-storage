package com.primshits.stepan.service;

import com.primshits.stepan.exception.StorageException;
import com.primshits.stepan.storage.StorageProperties;
import com.primshits.stepan.storage.StorageWrapper;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {

    private final StorageWrapper storageWrapper;

    private final StorageProperties properties;
    public FileSystemStorageService(StorageProperties properties, StorageWrapper storageWrapper) {
        this.storageWrapper = storageWrapper;
        this.properties = properties;
    }
    @Override
    public void init() {

    }

    @Override
    public void store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                storageWrapper.putObject(file.getSize(),10,file.getName().toLowerCase(),
                        properties.getBucketName(),inputStream);
            }
            //todo все записывается в один файл под названием "file"
        }
        catch (Exception e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }

    @Override
    public Path load(String filename) {
        return null;
    }

    @Override
    public Resource loadAsResource(String filename) {
        return null;
    }

    @Override
    public void deleteAll() {

    }
}
