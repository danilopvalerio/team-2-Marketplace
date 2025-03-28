package edu.uepb.cct.cc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import edu.uepb.cct.cc.model.Produto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoController {

    
    private static final String ARQUIVO_PRODUTOS = "src/main/resources/data/produtos.json";
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static Produto create(Produto produto) {
        if (produto == null || produto.getNome() == null || produto.getTipo() == null || produto.getMarca() == null
                || produto.getDescricao() == null || produto.getValor() <= 0 || produto.getQuantidade() < 0) {
            throw new IllegalArgumentException("Produto ou dados inválidos.");
        }
        List<Produto> produtos = carregarProdutos();

        for (Produto p : produtos) {
            if (p.getId().equalsIgnoreCase(produto.getId())) {
                System.out.println(
                        "-------------------------------------------------------------------------------------\n" + 
                        "Não foi possível adicionar o produto pois o ID já está cadastrado no sistema." + 
                        "-------------------------------------------------------------------------------------\n");
                throw new IllegalArgumentException("Produto já cadastrado no sistema com o mesmo ID.");
            }
        }
        produtos.add(produto);
        salvarProdutos(produtos);
        System.out.println("------------------------------------\n" + //
                "Produto adicionado.");
        return produto;
    }

    public static Produto getProdutoPorID(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID inválido.");
        }
        List<Produto> produtos = carregarProdutos();
        for (Produto produto : produtos) {
            if (produto.getId().equalsIgnoreCase(id)) {
                return produto;
            }
        }
        return null;
    }

    public static List<Produto> getTodosProdutos() {
        List<Produto> produtos = carregarProdutos();
        produtos.sort((p1, p2) -> p1.getNome().compareToIgnoreCase(p2.getNome()));
        return produtos;
    }

    private static List<Produto> carregarProdutos() {
        File file = new File(ARQUIVO_PRODUTOS);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (Reader reader = new FileReader(file)) {
            return objectMapper.readValue(reader,
                    TypeFactory.defaultInstance().constructCollectionType(List.class, Produto.class));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar produtos.", e);
        }
    }

    private static List<Produto> salvarProdutos(List<Produto> produtos) {
        if (produtos == null) {
            throw new IllegalArgumentException("Lista de produtos não pode ser nula.");
        }
        try (Writer writer = new FileWriter(ARQUIVO_PRODUTOS)) {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(writer, produtos);
            return produtos;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Produto atualizarProduto(String id, Produto produtoAtualizado) {
        if (id == null || id.isEmpty() || produtoAtualizado == null) {
            throw new IllegalArgumentException("ID ou dados inválidos.");
        }
        List<Produto> produtos = carregarProdutos();
        boolean atualizado = false;

        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getId().equals(id)) { // Comparar pelo id, não pelo nome
                produtoAtualizado.setId(id); // Atualizar o id
                produtos.set(i, produtoAtualizado);
                atualizado = true;
                break;
            }
        }

        if (!atualizado) {
            throw new IllegalArgumentException("Produto não encontrado.");
        }

        salvarProdutos(produtos);
        System.out.println("------------------------------------\n" +
                "Produto atualizado com sucesso.");
        return produtoAtualizado;
    }

    public static List<Produto> deleteProdutoPorID(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID inválido.");
        }
        List<Produto> produtos = carregarProdutos();
        boolean removido = produtos.removeIf(produto -> produto.getId().equalsIgnoreCase(id));
        if (!removido) {
            throw new IllegalArgumentException("Produto não encontrado.");
        }
        salvarProdutos(produtos);
        System.out.println("------------------------------------\n" + //
                "Produto removido com sucesso.");
        return produtos;
    }

    public static List<Produto> getProdutosPorLoja(String cnpjCpfLoja) {
        if (cnpjCpfLoja == null || cnpjCpfLoja.isEmpty()) {
            throw new IllegalArgumentException("CNPJ ou CPF inválido.");
        }
        List<Produto> produtos = carregarProdutos();
        List<Produto> produtosDaLoja = new ArrayList<>();

        for (Produto produto : produtos) {
            if (produto.getIdLoja().equalsIgnoreCase(cnpjCpfLoja)) {
                produtosDaLoja.add(produto);
            }
        }

        return produtosDaLoja;
    }

}
