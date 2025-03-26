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

    public static Loja create(Loja loja) {
        if (loja == null || loja.getCpfCnpj() == null || loja.getNome() == null || loja.getSenha() == null
                || loja.getEmail() == null || loja.getEndereco() == null) {
            throw new IllegalArgumentException("Loja ou dados inválidos.");
        }
        List<Loja> lojas = carregarLojas();

        for (Loja l : lojas) {
            if (l.getCpfCnpj().equals(loja.getCpfCnpj())) {
                System.out.println("Não foi possível adicionar a loja pois ela já está cadastrada no sistema.");
                throw new IllegalArgumentException("Loja já cadastrada no sistema.");

            }
        }
        lojas.add(loja);
        salvarLojas(lojas);
        System.out.println("Loja adicionada.");
        return loja;
    }

    public static Loja getLojaPorCpfCnpj(String cpfCnpj) {
        if (cpfCnpj == null || cpfCnpj.isEmpty()) {
            throw new IllegalArgumentException("CPF/CNPJ inválido.");
        }
        List<Loja> lojas = carregarLojas();
        for (Loja loja : lojas) {
            if (loja.getCpfCnpj().equals(cpfCnpj)) {
                return loja;
            }
        }
        return null;
    }

    public static List<Loja> getTodasLojas() {
        List<Loja> lojas = carregarLojas();
        lojas.sort((l1, l2) -> l1.getNome().compareToIgnoreCase(l2.getNome()));
        return lojas;
    }

    private static List<Loja> carregarLojas() {
        File file = new File(ARQUIVO_LOJAS);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (Reader reader = new FileReader(file)) {
            return objectMapper.readValue(reader,
                    TypeFactory.defaultInstance().constructCollectionType(List.class, Loja.class));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar lojas.", e);
        }
    }

    private static List<Loja> salvarLojas(List<Loja> lojas) {
        if (lojas == null) {
            throw new IllegalArgumentException("Lista de lojas não pode ser nula.");
        }
        try (Writer writer = new FileWriter(ARQUIVO_LOJAS)) {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(writer, lojas);
            return lojas;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Loja atualizarLoja(String cpfCnpj, Loja lojaAtualizada) {
        if (cpfCnpj == null || cpfCnpj.isEmpty() || lojaAtualizada == null) {
            throw new IllegalArgumentException("CPF/CNPJ ou dados inválidos.");
        }
        List<Loja> lojas = carregarLojas();
        boolean atualizado = false;

        for (int i = 0; i < lojas.size(); i++) {
            if (lojas.get(i).getCpfCnpj().equals(cpfCnpj)) {
                lojaAtualizada.setCpfCnpj(cpfCnpj);
                lojas.set(i, lojaAtualizada);
                atualizado = true;
                break;
            }
        }

        if (!atualizado) {
            throw new IllegalArgumentException("Loja não encontrada.");
        }

        salvarLojas(lojas);
        return lojaAtualizada;
    }

    public static List<Loja> deleteLojaPorCpfCnpj(String cpfCnpj) {
        if (cpfCnpj == null || cpfCnpj.isEmpty()) {
            throw new IllegalArgumentException("CPF/CNPJ inválido.");
        }
        List<Loja> lojas = carregarLojas();
        boolean removido = lojas.removeIf(loja -> loja.getCpfCnpj().equals(cpfCnpj));
        if (!removido) {
            throw new IllegalArgumentException("Loja não encontrada.");
        }
        salvarLojas(lojas);
        return lojas;
    }
}
