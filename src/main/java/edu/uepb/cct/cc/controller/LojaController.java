package edu.uepb.cct.cc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import edu.uepb.cct.cc.model.Loja;
import edu.uepb.cct.cc.services.SecurityHash;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LojaController {

    private static final String ARQUIVO_LOJAS = "src/main/resources/data/lojas.json";
    private static ObjectMapper objectMapper = new ObjectMapper();

    // Método para criar loja
    public static Loja createLoja(String nome, String email, String senha, String cpfCnpj, String endereco) {
        if (nome == null || email == null || senha == null || cpfCnpj == null || endereco == null) {
            throw new IllegalArgumentException("Dados inválidos.");
        }

        List<Loja> lojas = carregarLojas();

        // Verificar se a loja já existe
        for (Loja loja : lojas) {
            if (loja.getCpfCnpj().equals(cpfCnpj)) {
                throw new IllegalArgumentException("Loja já cadastrada no sistema.");
            }
        }

        Loja novaLoja = new Loja(nome, email, SecurityHash.hashPassword(senha), cpfCnpj, endereco);
        lojas.add(novaLoja);
        salvarLojas(lojas);
        return novaLoja;
    }

    // Método para buscar loja pelo CPF/CNPJ
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

    // Histórico de pedidos
    public static List<Object> getHistoricoPedidosDaLoja(String cpfCnpj) {
        if (cpfCnpj == null || cpfCnpj.isEmpty()) {
            throw new IllegalArgumentException("CPF/CNPJ inválido.");
        }

        File arquivoVendas = new File("src/main/resources/data/vendas.json");
        List<Object> pedidosDaLoja = new ArrayList<>();

        if (!arquivoVendas.exists()) {
            return pedidosDaLoja; // Nenhuma venda registrada ainda
        }

        try (Reader reader = new FileReader(arquivoVendas)) {
            ObjectMapper mapper = new ObjectMapper();
            List<Object> todasAsVendas = mapper.readValue(reader, List.class);

            for (Object venda : todasAsVendas) {
                // Conversão para Map para acessar os campos
                @SuppressWarnings("unchecked")
                var vendaMap = (java.util.Map<String, Object>) venda;
                if (cpfCnpj.equals(vendaMap.get("id_loja"))) {
                    pedidosDaLoja.add(vendaMap);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o histórico de pedidos da loja.", e);
        }

        return pedidosDaLoja;
    }

    // Método para listar todas as lojas
    public static List<Loja> getTodasLojas() {
        List<Loja> lojas = carregarLojas();
        lojas.sort((l1, l2) -> l1.getNome().compareToIgnoreCase(l2.getNome()));
        return lojas;
    }

    // Método para atualizar loja
    public static Loja atualizarLoja(String cpfCnpj, String nome, String email, String senha, String endereco) {
        if (cpfCnpj == null || cpfCnpj.isEmpty()) {
            throw new IllegalArgumentException("CPF/CNPJ inválido.");
        }
        List<Loja> lojas = carregarLojas();
        boolean lojaAtualizada = false;

        for (int i = 0; i < lojas.size(); i++) {
            if (lojas.get(i).getCpfCnpj().equals(cpfCnpj)) {
                Loja loja = lojas.get(i);
                if (nome != null && !nome.isEmpty()) {
                    loja.setNome(nome);
                }
                if (email != null && !email.isEmpty()) {
                    loja.setEmail(email);
                }
                if (senha != null && !senha.isEmpty()) {
                    loja.setSenha(senha);
                }
                if (endereco != null && !endereco.isEmpty()) {
                    loja.setEndereco(endereco);
                }
                lojas.set(i, loja);
                lojaAtualizada = true;
                break;
            }
        }

        if (!lojaAtualizada) {
            throw new IllegalArgumentException("Loja não encontrada.");
        }

        salvarLojas(lojas);
        return lojas.stream().filter(l -> l.getCpfCnpj().equals(cpfCnpj)).findFirst().orElse(null);
    }

    // Método para deletar loja
    public static void deleteLojaPorCpfCnpj(String cpfCnpj) {
        if (cpfCnpj == null || cpfCnpj.isEmpty()) {
            throw new IllegalArgumentException("CPF/CNPJ inválido.");
        }
        List<Loja> lojas = carregarLojas();
        boolean lojaRemovida = lojas.removeIf(loja -> loja.getCpfCnpj().equals(cpfCnpj));

        if (!lojaRemovida) {
            throw new IllegalArgumentException("Loja não encontrada.");
        }
        salvarLojas(lojas);
    }

    // Método para carregar lojas do arquivo JSON
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

    // Método para salvar lojas no arquivo JSON
    private static void salvarLojas(List<Loja> lojas) {
        try (Writer writer = new FileWriter(ARQUIVO_LOJAS)) {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(writer, lojas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
