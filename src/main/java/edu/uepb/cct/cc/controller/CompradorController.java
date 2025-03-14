package edu.uepb.cct.cc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import edu.uepb.cct.cc.model.Comprador;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CompradorController {
    private static final String ARQUIVO_COMPRADORES = Paths.get("src", "main", "resources", "data", "compradores.json").toString();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = Logger.getLogger(CompradorController.class.getName());

    public static void create(Comprador comprador) {
        List<Comprador> compradores = new ArrayList<>();
        File file = new File(ARQUIVO_COMPRADORES);

        // Garantir que o diretório exista
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        // Se o arquivo existir, lê os compradores
        if (file.exists()) {
            try (Reader reader = new FileReader(file)) {
                compradores = objectMapper.readValue(reader,
                        TypeFactory.defaultInstance().constructCollectionType(List.class, Comprador.class));
            } catch (IOException e) {
                logger.severe("Erro ao ler o arquivo de compradores: " + e.getMessage());
            }
        } else {
            try {
                if (file.createNewFile()) {
                    logger.info("Arquivo criado: " + file.getName());
                }
            } catch (IOException e) {
                logger.severe("Erro ao criar o arquivo de compradores: " + e.getMessage());
            }
        }

        // Verifica se o CPF já está cadastrado
        for (Comprador c : compradores) {
            if (c.getCpf().equals(comprador.getCpf())) {
                System.out.println("Não foi possível adicionar o comprador pois ele já está cadastrado no sistema.");
                return;
            }
        }

        // Adiciona o novo comprador à lista
        compradores.add(comprador);

        // Salva a lista atualizada no arquivo
        try (Writer writer = new FileWriter(file)) {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(writer, compradores);
            System.out.println("Comprador adicionado com sucesso.");
        } catch (IOException e) {
            logger.severe("Erro ao salvar o comprador: " + e.getMessage());
        }
    }
}