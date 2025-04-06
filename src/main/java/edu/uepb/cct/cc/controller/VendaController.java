package edu.uepb.cct.cc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uepb.cct.cc.model.Produto;
import edu.uepb.cct.cc.model.Venda;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class VendaController {

    private static final String ARQUIVO_VENDAS = "src/main/resources/data/vendas.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public void removerProdutosDoEstoque(Venda venda) {
        ProdutoController produtoController = new ProdutoController();
        List<Produto> produtos = produtoController.carregarProdutos();

        for (int i = 0; i < venda.getIdsProdutosVendidos().size(); i++) {
            String idProduto = venda.getIdsProdutosVendidos().get(i);
            int quantidadeVendida = venda.getQuantidades().get(i);

            // Buscar o produto correspondente
            Produto produtoEncontrado = null;
            for (Produto p : produtos) {
                if (p.getId().equals(idProduto)) {
                    produtoEncontrado = p;
                    break;
                }
            }

            // Validar existência e estoque
            if (produtoEncontrado == null) {
                throw new IllegalArgumentException("Produto com ID " + idProduto + " não encontrado.");
            }
            if (produtoEncontrado.getQuantidade() < quantidadeVendida) {
                throw new IllegalStateException("Estoque insuficiente para o produto " + idProduto + ".");
            }

            // Atualizar quantidade
            produtoEncontrado.setQuantidade(produtoEncontrado.getQuantidade() - quantidadeVendida);
        }

        // Salvar alterações no produtos.json
        produtoController.salvarProdutos(produtos);
    }

    // 🔹 Task 4.4.1 — Garantir a existência do arquivo vendas.json
    public void garantirArquivoDeVendas() {
        File arquivo = new File(ARQUIVO_VENDAS);
        File diretorio = arquivo.getParentFile();

        // Criar diretório se não existir
        if (diretorio != null && !diretorio.exists()) {
            diretorio.mkdirs();
        }

        // Criar o arquivo com conteúdo inicial [] se não existir
        if (!arquivo.exists()) {
            try (FileWriter writer = new FileWriter(arquivo)) {
                writer.write("[]");
                System.out.println("Arquivo vendas.json criado com sucesso.");
            } catch (IOException e) {
                System.err.println("Erro ao criar o arquivo vendas.json: " + e.getMessage());
            }
        }
    }

    // 🔹 Task 4.4.2 — Carregar as vendas já registradas
    public List<Map<String, Object>> carregarVendasRegistradas() {
        File arquivo = new File(ARQUIVO_VENDAS);

        if (!arquivo.exists() || arquivo.length() == 0) {
            return new ArrayList<>();
        }

        try {
            return objectMapper.readValue(arquivo,
                    new com.fasterxml.jackson.core.type.TypeReference<List<Map<String, Object>>>() {
                    });
        } catch (IOException e) {
            System.err.println("Erro ao carregar vendas registradas: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public Map<String, Object> construirVendaParaRegistro(Venda venda) {
        Map<String, Object> novaVenda = new LinkedHashMap<>(); // 🔁 Usa LinkedHashMap para manter a ordem de inserção

        // Ordem dos campos conforme desejado
        novaVenda.put("id_venda", venda.getIdVenda());
        novaVenda.put("id_comprador", venda.getIdComprador());

        List<List<Object>> listaProdutos = new ArrayList<>();
        double valorTotal = 0;

        for (int i = 0; i < venda.getIdsProdutosVendidos().size(); i++) {
            String idProduto = venda.getIdsProdutosVendidos().get(i);
            int quantidade = venda.getQuantidades().get(i);
            double valorUnitario = venda.getValoresUnitarios().get(i);

            valorTotal += quantidade * valorUnitario;
            listaProdutos.add(Arrays.asList(idProduto, quantidade, valorUnitario));
        }

        novaVenda.put("produtos", listaProdutos);
        novaVenda.put("valor total", valorTotal);

        return novaVenda;
    }

    public void registrarVenda(Venda venda) {
        // Garante que o arquivo existe
        garantirArquivoDeVendas();

        // Carrega as vendas atuais
        List<Map<String, Object>> vendasExistentes = carregarVendasRegistradas();

        // Verifica se já existe uma venda com o mesmo ID
        boolean vendaJaExiste = vendasExistentes.stream()
                .anyMatch(v -> v.get("id_venda") != null && v.get("id_venda").equals(venda.getIdVenda()));

        if (vendaJaExiste) {
            System.out.println("⚠️ Venda com ID " + venda.getIdVenda() + " já está registrada.");
            return;
        }

        // Constrói o novo registro de venda
        Map<String, Object> novaVendaMap = construirVendaParaRegistro(venda);

        // Adiciona à lista
        vendasExistentes.add(novaVendaMap);

        // Salva a lista atualizada no arquivo JSON
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(ARQUIVO_VENDAS), vendasExistentes);
            System.out.println("✅ Venda registrada com sucesso.");
        } catch (IOException e) {
            System.err.println("❌ Erro ao registrar a venda: " + e.getMessage());
        }
    }
}
