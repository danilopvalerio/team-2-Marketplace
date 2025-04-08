package edu.uepb.cct.cc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uepb.cct.cc.model.Produto;
import edu.uepb.cct.cc.model.Venda;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class VendaController {

    private static final String ARQUIVO_VENDAS = "src/main/resources/data/vendas.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public void removerProdutosDoEstoque(Venda venda) {
        ProdutoController produtoController = new ProdutoController();
        List<Produto> produtos = produtoController.carregarProdutos();

        for (int i = 0; i < venda.getIdsProdutosVendidos().size(); i++) {
            String idProduto = venda.getIdsProdutosVendidos().get(i);
            int quantidadeVendida = venda.getQuantidades().get(i);

            Produto produtoEncontrado = null;
            for (Produto p : produtos) {
                if (p.getId().equals(idProduto)) {
                    produtoEncontrado = p;
                    break;
                }
            }

            if (produtoEncontrado == null) {
                throw new IllegalArgumentException("Produto com ID " + idProduto + " não encontrado.");
            }
            if (produtoEncontrado.getQuantidade() < quantidadeVendida) {
                throw new IllegalStateException("Estoque insuficiente para o produto " + idProduto + ".");
            }

            produtoEncontrado.setQuantidade(produtoEncontrado.getQuantidade() - quantidadeVendida);
        }

        produtoController.salvarProdutos(produtos);
    }

    public void garantirArquivoDeVendas() {
        File arquivo = new File(ARQUIVO_VENDAS);
        File diretorio = arquivo.getParentFile();

        if (diretorio != null && !diretorio.exists()) {
            diretorio.mkdirs();
        }

        if (!arquivo.exists()) {
            try (FileWriter writer = new FileWriter(arquivo)) {
                writer.write("[]");
                System.out.println("Arquivo vendas.json criado com sucesso.");
            } catch (IOException e) {
                System.err.println("Erro ao criar o arquivo vendas.json: " + e.getMessage());
            }
        }
    }

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
        Map<String, Object> novaVenda = new HashMap<>();

        novaVenda.put("id_venda", venda.getIdVenda());
        novaVenda.put("id_comprador", venda.getIdComprador());
        novaVenda.put("data", venda.getDataVenda().toString());

        List<List<Object>> listaProdutos = new ArrayList<>();
        double valorTotal = 0;

        for (int i = 0; i < venda.getIdsProdutosVendidos().size(); i++) {
            String idProduto = venda.getIdsProdutosVendidos().get(i);
            int quantidade = venda.getQuantidades().get(i);
            double valorUnitario = venda.getValoresUnitarios().get(i);

            valorTotal += quantidade * valorUnitario;

            List<Object> produtoInfo = Arrays.asList(idProduto, quantidade, valorUnitario);
            listaProdutos.add(produtoInfo);
        }

        novaVenda.put("produtos", listaProdutos);
        novaVenda.put("valor total", valorTotal);

        return novaVenda;
    }

    public void registrarVenda(Venda venda) {
        garantirArquivoDeVendas();
        List<Map<String, Object>> vendasExistentes = carregarVendasRegistradas();
        Map<String, Object> novaVendaMap = construirVendaParaRegistro(venda);

        boolean vendaJaExiste = vendasExistentes.stream()
                .anyMatch(v -> Objects.equals(v.get("id_venda"), novaVendaMap.get("id_venda")));

        if (vendaJaExiste) {
            System.err.println("❌ Venda com ID " + venda.getIdVenda() + " já registrada.");
            return;
        }

        vendasExistentes.add(novaVendaMap);

        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(ARQUIVO_VENDAS), vendasExistentes);
            System.out.println("✅ Venda registrada com sucesso.");
        } catch (IOException e) {
            System.err.println("❌ Erro ao registrar a venda: " + e.getMessage());
        }
    }

    public void registrarVendasPorLoja(Venda vendaOriginal) {
        garantirArquivoDeVendas();
        List<Map<String, Object>> vendasExistentes = carregarVendasRegistradas();

        ProdutoController produtoController = new ProdutoController();
        List<Produto> todosProdutos = produtoController.carregarProdutos();

        Map<String, List<Integer>> produtosPorLoja = new HashMap<>();

        for (int i = 0; i < vendaOriginal.getIdsProdutosVendidos().size(); i++) {
            String idProduto = vendaOriginal.getIdsProdutosVendidos().get(i);

            Produto produto = todosProdutos.stream()
                    .filter(p -> p.getId().equals(idProduto))
                    .findFirst()
                    .orElseThrow(
                            () -> new IllegalArgumentException("Produto com ID " + idProduto + " não encontrado."));

            String idLoja = produto.getIdLoja();

            produtosPorLoja.computeIfAbsent(idLoja, k -> new ArrayList<>()).add(i);
        }

        int contador = 1;

        for (Map.Entry<String, List<Integer>> entry : produtosPorLoja.entrySet()) {
            String idLoja = entry.getKey();
            List<Integer> indices = entry.getValue();

            List<String> idsSeparados = new ArrayList<>();
            List<Double> valoresSeparados = new ArrayList<>();
            List<Integer> quantidadesSeparadas = new ArrayList<>();

            for (int idx : indices) {
                idsSeparados.add(vendaOriginal.getIdsProdutosVendidos().get(idx));
                valoresSeparados.add(vendaOriginal.getValoresUnitarios().get(idx));
                quantidadesSeparadas.add(vendaOriginal.getQuantidades().get(idx));
            }

            String novoIdVenda = "V" + String.format("%02d", contador++); // Gera V01, V02, etc.

            Venda novaVenda = new Venda(
                    novoIdVenda,
                    vendaOriginal.getIdComprador(),
                    LocalDate.now(),
                    idsSeparados,
                    valoresSeparados,
                    quantidadesSeparadas);

            Map<String, Object> vendaMap = construirVendaParaRegistro(novaVenda);
            vendaMap.put("id_loja", idLoja);
            vendaMap.put("id_vendaGeral", vendaOriginal.getIdVenda()); // Referência à venda original

            boolean vendaJaExiste = vendasExistentes.stream()
                    .anyMatch(v -> v.get("id_venda").equals(novoIdVenda));

            if (!vendaJaExiste) {
                vendasExistentes.add(vendaMap);
                removerProdutosDoEstoque(novaVenda);
            }
        }

        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(ARQUIVO_VENDAS), vendasExistentes);
            System.out.println("✅ Todas as vendas foram registradas com sucesso.");
        } catch (IOException e) {
            System.err.println("❌ Erro ao salvar as vendas: " + e.getMessage());
        }
    }

    public void removerVenda(String idVenda) {
        garantirArquivoDeVendas();

        List<Map<String, Object>> vendas = carregarVendasRegistradas();

        boolean vendaEncontrada = false;
        for (Map<String, Object> venda : vendas) {
            if (idVenda.equals(venda.get("id_venda"))) {
                vendas.remove(venda); // Remove a venda
                vendaEncontrada = true;
                break;
            }
        }

        if (!vendaEncontrada) {
            System.out.println("❌ Venda com ID " + idVenda + " não encontrada.");
            return;
        }

        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(ARQUIVO_VENDAS), vendas);
            System.out.println("✅ Venda com ID " + idVenda + " removida com sucesso.");
        } catch (IOException e) {
            System.err.println("❌ Erro ao salvar o arquivo de vendas: " + e.getMessage());
        }
    }
}
