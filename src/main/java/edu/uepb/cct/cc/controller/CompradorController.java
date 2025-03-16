package edu.uepb.cct.cc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import edu.uepb.cct.cc.model.Comprador;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public class CompradorController {
    private static final String ARQUIVO_COMPRADORES = "src/main/resources/data/compradores.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = Logger.getLogger(CompradorController.class.getName());

    public static void create(Comprador comprador) {
        List<Comprador> compradores = new ArrayList<>();
        File file = new File(ARQUIVO_COMPRADORES);

        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        if (file.exists() && file.length() > 0) {
            try (Reader reader = new FileReader(file)) {
                compradores = objectMapper.readValue(reader,
                        TypeFactory.defaultInstance().constructCollectionType(List.class, Comprador.class));
            } catch (IOException e) {
                logger.severe("Erro ao ler o arquivo de compradores: " + e.getMessage());
            }
        }

        for (Comprador c : compradores) {
            if (c.getCpf().equals(comprador.getCpf())) {
                System.out.println("Não foi possível adicionar o comprador pois ele já está cadastrado no sistema.");
                return;
            }
        }

        compradores.add(comprador);

        try (Writer writer = new FileWriter(file)) {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(writer, compradores);
            System.out.println("Comprador adicionado com sucesso.");
        } catch (IOException e) {
            logger.severe("Erro ao salvar o comprador: " + e.getMessage());
        }
    }

    public static List<Comprador> getTodosCompradores() {
        File file = new File(ARQUIVO_COMPRADORES);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }
        try (Reader reader = new FileReader(file)) {
            List<Comprador> compradores = objectMapper.readValue(reader,
                    TypeFactory.defaultInstance().constructCollectionType(List.class, Comprador.class));
            compradores.sort(Comparator.comparing(Comprador::getNome, String.CASE_INSENSITIVE_ORDER));
            return compradores;
        } catch (IOException e) {
            logger.severe("Erro ao carregar compradores: " + e.getMessage());
            throw new RuntimeException("Erro ao carregar compradores.", e);
        }
    }

    public static Comprador getCompradorPorCpf(String cpf) {
        if (cpf == null || cpf.isEmpty()) {
            throw new IllegalArgumentException("CPF inválido.");
        }
        List<Comprador> compradores = getTodosCompradores();
        for (Comprador comprador : compradores) {
            if (comprador.getCpf().equals(cpf)) {
                return comprador;
            }
        }
        return null;
    }

    public static String deleteCompradorPorCpf(String cpf) {
        if (cpf == null || cpf.isEmpty()) {
            return "CPF inválido.";
        }
        List<Comprador> compradores = getTodosCompradores();
        boolean removido = compradores.removeIf(comprador -> comprador.getCpf().equals(cpf));

        if (!removido) {
            return "Comprador não encontrado.";
        }

        try (Writer writer = new FileWriter(ARQUIVO_COMPRADORES)) {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(writer, compradores);
            return "Comprador removido com sucesso.";
        } catch (IOException e) {
            logger.severe("Erro ao salvar compradores após remoção: " + e.getMessage());
            return "Erro ao remover comprador.";
        }
    }
}
