package com.ecommerce.csv;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public final class CsvUtils {

    private CsvUtils() {

    }


    public static List<String[]> read(String filePath) throws IOException {
        try (CSVReader reader = new CSVReader(new FileReader(Path.of(filePath).toFile()))) {
            return reader.readAll();
        } catch (com.opencsv.exceptions.CsvException e) {
            throw new IOException("Erro ao processar CSV: " + e.getMessage(), e);
        }
    }


    public static void write(String filePath, List<String[]> rows) throws IOException {
        Path path = Path.of(filePath);
        if (path.getParent() != null && !java.nio.file.Files.exists(path.getParent())) {
            java.nio.file.Files.createDirectories(path.getParent());
        }
        try (CSVWriter writer = new CSVWriter(new FileWriter(path.toFile()))) {
            writer.writeAll(rows);
        }
    }


    public static void write(String filePath, String[] header, List<String[]> rows) throws IOException {
        List<String[]> data = new ArrayList<>();
        data.add(header);
        data.addAll(rows);
        write(filePath, data);
    }
}
