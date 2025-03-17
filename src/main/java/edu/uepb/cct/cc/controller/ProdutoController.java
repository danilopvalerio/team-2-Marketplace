package edu.uepb.cct.cc.controller;

import edu.uepb.cct.cc.model.Produto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProdutoController {
    private static final String ARQUIVO_JSON = "produtos.json";
    private List<Produto> produtos;
    private Gson gson;

    public ProdutoController() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.produtos = carregarProdutos();
    }

    private List<Produto> carregarProdutos() {
        try (FileReader reader = new FileReader(ARQUIVO_JSON)) {
            Type listType = new TypeToken<ArrayList<Produto>>() {
            }.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private void salvarProdutos() {
        try (FileWriter writer = new FileWriter(ARQUIVO_JSON)) {
            gson.toJson(produtos, writer);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar produtos.", e);
        }
    }

    public String cadastrarProduto(String nome, float valor, String tipo, int quantidade, String marca,
            String descricao) {
        try {
            // Validações adicionais antes de criar o produto
            if (nome == null || nome.trim().isEmpty()) {
                return "Nome não pode ser vazio ou nulo.";
            }

            if (valor <= 0) {
                return "O valor do produto deve ser maior que zero.";
            }

            if (tipo == null || tipo.trim().isEmpty()) {
                return "Tipo não pode ser vazio ou nulo.";
            }

            if (quantidade < 0) {
                return "A quantidade não pode ser negativa.";
            }

            if (marca == null || marca.trim().isEmpty()) {
                return "Marca não pode ser vazia ou nula.";
            }

            if (descricao == null || descricao.trim().isEmpty()) {
                return "Descrição não pode ser vazia ou nula.";
            }

            for (Produto p : produtos) {
                if (p.getNome().equalsIgnoreCase(nome) && p.getMarca().equalsIgnoreCase(marca)) {
                    return "Produto já existe no sistema.";
                }
            }

            Produto novoProduto = new Produto(nome, valor, tipo, quantidade, marca, descricao);
            produtos.add(novoProduto);
            salvarProdutos();
            return "Produto cadastrado com sucesso.";
        } catch (IllegalArgumentException e) {
            return "Erro ao cadastrar produto: " + e.getMessage();
        } catch (Exception e) {
            return "Erro inesperado ao cadastrar o produto.";
        }
    }

    public String visualizarProduto(String nome) {
        carregarProdutos();

        if (produtos == null || produtos.isEmpty()) {
            return "Produto não encontrado.";
        }

        if (nome == null || nome.trim().isEmpty()) {
            return listarTodosOsProdutos();
        }

        for (Produto p : produtos) {
            if (p.getNome().equalsIgnoreCase(nome)) {
                return p.toString();
            }
        }
        return "Produto não encontrado.";
    }

    public String excluirProduto(String nome) {
        carregarProdutos();

        if (produtos == null || produtos.isEmpty()) {
            return "Nenhum produto cadastrado.";
        }

        Iterator<Produto> iterator = produtos.iterator();
        while (iterator.hasNext()) {
            Produto p = iterator.next();
            if (p.getNome().equalsIgnoreCase(nome)) {
                iterator.remove();
                salvarProdutos();
                return "Produto removido com sucesso.";
            }
        }
        return "Produto não encontrado.";
    }

    private String listarTodosOsProdutos() {
        if (produtos == null || produtos.isEmpty()) {
            return "Nenhum produto cadastrado.";
        }

        StringBuilder lista = new StringBuilder("Lista de Produtos:\n");
        for (Produto p : produtos) {
            lista.append(p.toString()).append("\n");
        }
        return lista.toString();
    }
}
