package edu.uepb.cct.cc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import edu.uepb.cct.cc.model.Loja;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LojaController {

    // Caminho relativo para o arquivo
    private static final String ARQUIVO_LOJAS = "src/main/resources/data/lojas.json";
    private static ObjectMapper objectMapper = new ObjectMapper();

    // Método para adicionar uma loja ao JSON
    public static void create(Loja loja) {

        boolean checkLoja = false;
        List<Loja> lojas = new ArrayList<>();
        File file = new File(ARQUIVO_LOJAS);

        // Garantir que o diretório exista
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs(); // Cria o diretório, se necessário
        }

        // Se o arquivo já existir, lê as lojas
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
            // Caso o arquivo não exista, ele irá ser criado
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
                return; // Se encontrar, sai do método sem adicionar a loja
            }
        }
        lojas.add(loja);

        // Salva as lojas novamente no arquivo JSON
        try (Writer writer = new FileWriter(file)) {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(writer, lojas);
            System.out.println("Loja adicionada.\nProcesso finalizado.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
