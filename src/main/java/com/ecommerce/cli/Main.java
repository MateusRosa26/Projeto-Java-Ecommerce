package com.ecommerce.cli;

import com.ecommerce.csv.CsvUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class Main {

    private static final String USAGE = """
        Uso:
          java -jar csv-cli.jar read <arquivo.csv>
          java -jar csv-cli.jar write <arquivo.csv>
        """;

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println(USAGE);
            return;
        }

        String command = args[0];
        String filePath = args[1];

        try {
            switch (command) {
                case "read" -> read(filePath);
                case "write" -> write(filePath);
                default -> System.out.println("Comando desconhecido: " + command + "\n" + USAGE);
            }
        } catch (IOException e) {
            System.err.println("Erro ao acessar arquivo: " + e.getMessage());
        }
    }

    private static void read(String filePath) throws IOException {
        List<String[]> rows = CsvUtils.read(filePath);
        rows.forEach(row -> System.out.println(Arrays.toString(row)));
    }

    private static void write(String filePath) throws IOException {
        String[] header = {"id", "nome", "preco", "descricao", "imagemUrl", "disponivel"};
        List<String[]> rows = List.of(
                new String[]{"1", "Teclado gamer", "199.90", "Teclado mecânico", "http://imagem.com/teclado", "true"},
                new String[]{"2", "Mouse óptico", "89.90", "Mouse 1600dpi", "http://imagem.com/mouse", "true"}
        );
        CsvUtils.write(filePath, header, rows);
        System.out.println("Arquivo CSV gerado com sucesso em " + filePath);
    }
}
