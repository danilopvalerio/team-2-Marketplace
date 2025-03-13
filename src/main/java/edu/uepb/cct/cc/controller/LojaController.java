package edu.uepb.cct.cc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import edu.uepb.cct.cc.model.Loja;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LojaController {

    private static final String ARQUIVO_LOJAS = "src/main/resources/data/lojas.json";
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void create(Loja loja) {

        boolean checkLoja = false;
        List<Loja> lojas = new ArrayList<>();
        File file = new File(ARQUIVO_LOJAS);

        // Garantir que o diretório exista
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs(); // Cria o diretório, se necessário
        }

        // Se o arquivo já existir, lê as lojas, caso contrário, cria o arquivo
        if (file.exists()) {
            try (Reader reader = new FileReader(file)) {
                System.out.println("Arquivo encontrado");

                // Lê a lista de lojas do arquivo
                lojas = objectMapper.readValue(reader,
                        TypeFactory.defaultInstance().constructCollectionType(List.class, Loja.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            try {
                if (file.createNewFile()) {
                    System.out.println("Arquivo criado: " + file.getName());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (Loja l : lojas) {
            if (l.getCpfCnpj().equals(loja.getCpfCnpj())) {
                System.out.println("Não foi possível adicionar a loja pois ela já está cadastrada no sistema.");
                return;
            }
        }
        lojas.add(loja);

        try (Writer writer = new FileWriter(file)) {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(writer, lojas);
            System.out.println("Loja adicionada.\nProcesso finalizado.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
