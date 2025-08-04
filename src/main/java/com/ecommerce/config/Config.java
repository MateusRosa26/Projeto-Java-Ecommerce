package com.ecommerce.config;

import java.nio.file.Path;
import java.nio.file.Paths;


public final class Config {

    private static final String STORAGE_PROPERTY = "csv.storageDir";

    private Config() {
    }


    public static Path dataDir() {
        String dir = System.getProperty(STORAGE_PROPERTY);
        if (dir == null || dir.isBlank()) {
            dir = "data";
        }
        return Paths.get(dir);
    }
}
