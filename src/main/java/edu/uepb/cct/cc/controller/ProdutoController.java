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

    public static String create(String nome, float valor, String tipo, int quantidade, String marca, String descricao,
                                String id, String idLoja) {
        if (nome == null || tipo == null || marca == null || descricao == null
                || id == null || idLoja == null || quantidade <= 0 || valor < 0) {
            throw new IllegalArgumentException("Produto ou dados inválidos.");
        }

        List<Produto> produtos = carregarProdutos();

        Produto produto = new Produto(nome, valor, tipo, quantidade, marca, descricao, id, idLoja);
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
        return formatarProduto(produto);
    }

    public static String getProdutoPorID(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID inválido.");
        }
        List<Produto> produtos = carregarProdutos();
        for (Produto produto : produtos) {
            if (produto.getId().equalsIgnoreCase(id)) {
                return formatarProduto(produto);
            }
        }
        return null;
    }

    public static Produto getProdutoPorIDOBJ(String id) {
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

    public static List<String> getTodosProdutos() {
        List<Produto> produtos = carregarProdutos();
        produtos.sort((p1, p2) -> p1.getNome().compareToIgnoreCase(p2.getNome()));

        List<String> produtosFormatados = new ArrayList<>();
        for (Produto produto : produtos) {
            produtosFormatados.add(formatarProduto(produto));
        }

        return produtosFormatados;
    }

    public static List<Produto> carregarProdutos() {
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

    public static List<Produto> salvarProdutos(List<Produto> produtos) {
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

    public static boolean atualizarProduto(String nome, float valor, String tipo, int quantidade, String marca,
                                           String descricao, String id, String idLoja) {
        if (nome == null || tipo == null || marca == null || descricao == null
                || id == null || idLoja == null || quantidade <= 0 || valor < 0) {
            throw new IllegalArgumentException("Produto ou dados inválidos.");
        }
        List<Produto> produtos = carregarProdutos();
        boolean atualizado = false;
        Produto produtoAtualizado = new Produto(nome, valor, tipo, quantidade, marca, descricao, id, idLoja);

        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getId().equals(id)) {
                produtoAtualizado.setId(id);
                produtos.set(i, produtoAtualizado);
                atualizado = true;
                break;
            }
        }

        if (!atualizado) {
            throw new IllegalArgumentException("Produto não encontrado.");
        }

        salvarProdutos(produtos);
        return atualizado;
    }

    public static boolean deleteProdutoPorID(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID inválido.");
        }
        List<Produto> produtos = carregarProdutos();
        boolean removido = produtos.removeIf(produto -> produto.getId().equalsIgnoreCase(id));

        if (!removido) {
            throw new IllegalArgumentException("Produto não encontrado.");
        }

        salvarProdutos(produtos);
        System.out.println("------------------------------------\n" + "Produto removido com sucesso.");

        // Retorna true se o produto foi removido com sucesso
        return removido;
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

    public static String formatarProduto(Produto produto) {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(produto.getId()).append("\n");
        sb.append("Nome: ").append(produto.getNome()).append("\n");
        sb.append("Tipo: ").append(produto.getTipo()).append("\n");
        sb.append("Marca: ").append(produto.getMarca()).append("\n");
        sb.append("Descrição: ").append(produto.getDescricao()).append("\n");
        sb.append("Valor: ").append(produto.getValor()).append("\n");
        sb.append("Quantidade: ").append(produto.getQuantidade()).append("\n");
        sb.append("ID Loja: ").append(produto.getIdLoja()).append("\n");
        sb.append("Avaliação: ").append(produto.getMediaAvaliacoes()).append("\n");

        sb.append("------------------------------------");
        return sb.toString();
    }

    public static Produto buscarProdutoPorID(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID inválido.");
        }
        List<Produto> produtos = carregarProdutos();
        for (Produto produto : produtos) {
            if (produto.getId().equalsIgnoreCase(id)) {
                return produto;
            }
        }
        return null; // ou pode lançar exceção se preferir
    }

    public static void adicionar_avaliacao(String idProduto, int nota) {
        if (nota < 1 || nota > 5) {
            throw new IllegalArgumentException("A nota deve ser um valor entre 1 e 5.");
        }
        if (idProduto == null || idProduto.isEmpty()) {
            throw new IllegalArgumentException("ID do produto inválido.");
        }

        List<Produto> produtos = carregarProdutos();
        boolean produtoEncontrado = false;

        for (Produto produto : produtos) {
            if (produto.getId().equalsIgnoreCase(idProduto)) {
                List<Integer> avaliacoes = produto.getAvaliacoes();
                if (avaliacoes == null) {
                    avaliacoes = new ArrayList<>();
                }
                avaliacoes.add(nota);
                produto.setAvaliacoes(avaliacoes);

                // Atualiza a média de avaliações
                double soma = 0.0;
                for (int avaliacao : avaliacoes) {
                    soma += avaliacao;
                }
                double media = soma / avaliacoes.size();
                produto.setMediaAvaliacoes((float) media);

                produtoEncontrado = true;
                break;
            }
        }

        if (!produtoEncontrado) {
            throw new IllegalArgumentException("Produto com ID informado não encontrado.");
        }

        salvarProdutos(produtos);
    }

    public static float obter_media_avaliacoes(String idProduto) {
        List<Produto> produtos = carregarProdutos();
        for (Produto produto : produtos) {
            if (produto.getId().equalsIgnoreCase(idProduto)) {
                List<Integer> avaliacoes = produto.getAvaliacoes();
                if (avaliacoes == null || avaliacoes.isEmpty()) {
                    return 0.0f; // ou lançar exceção se preferir
                }
                double soma = 0.0;
                for (int avaliacao : avaliacoes) {
                    soma += avaliacao;
                }
                return (float) (soma / avaliacoes.size());
            }
        } return 0.0f;
    }
}
