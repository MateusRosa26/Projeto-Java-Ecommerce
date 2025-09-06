package com.ecommerce.repository;

import com.ecommerce.csv.CsvUtils;
import com.ecommerce.csv.CsvSerializable;
import com.ecommerce.csv.EntityWithId;
import com.ecommerce.config.Config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


public abstract class AbstractCsvRepository<T extends CsvSerializable> {

    private final Path filePath;
    private final Function<String[], T> rowMapper;
    private final String[] header;

    protected AbstractCsvRepository(String fileName, String[] header, Function<String[], T> rowMapper) {
        this.filePath = Config.dataDir().resolve(fileName);
        this.header = header;
        this.rowMapper = rowMapper;
        ensureFileExists();
    }

    private void ensureFileExists() {
        try {
            if (Files.notExists(filePath)) {
                Files.createDirectories(filePath.getParent());
                List<String[]> rows = new ArrayList<>();
                rows.add(header);
                CsvUtils.write(filePath.toString(), rows);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao criar arquivo CSV " + filePath, e);
        }
    }

    public List<T> findAll() {
        try {
            ensureFileExists(); // Garantir que o arquivo existe antes de ler
            List<String[]> rows = CsvUtils.read(filePath.toString());
            if (rows.isEmpty()) return List.of();
            List<T> list = new ArrayList<>();
            for (int i = 1; i < rows.size(); i++) { // pula cabeçalho
                list.add(rowMapper.apply(rows.get(i)));
            }
            return list;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveAll(List<T> entities) {
        try {
            ensureFileExists(); // Garantir que o arquivo existe antes de escrever
            List<String[]> rows = new ArrayList<>();
            rows.add(header);
            entities.forEach(e -> rows.add(e.toCsvRow()));
            CsvUtils.write(filePath.toString(), rows);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(T entity) {
        List<T> all = new ArrayList<>(findAll());
        
        // Gerar ID automaticamente se não estiver definido
        if (entity instanceof EntityWithId entityWithId && entityWithId.getId() == 0) {
            long maxId = all.stream()
                    .filter(EntityWithId.class::isInstance)
                    .mapToLong(e -> ((EntityWithId) e).getId())
                    .max()
                    .orElse(0L);
            entityWithId.setId(maxId + 1);
        }
        
        all.add(entity);
        saveAll(all);
    }
}
