package edu.uepb.cct.cc.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.uepb.cct.cc.model.Loja;
import edu.uepb.cct.cc.model.Produto;
import edu.uepb.cct.cc.model.Venda;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class VendaController {

    private static final String ARQUIVO_VENDAS = "src/main/resources/data/vendas.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void removerProdutosDoEstoque(Venda venda) {
        // Carrega todos os produtos atualizados
        List<Produto> produtos = ProdutoController.carregarProdutos();

        // Cria um mapa para acesso rápido aos produtos por ID
        Map<String, Produto> produtoMap = produtos.stream()
                .collect(Collectors.toMap(Produto::getId, p -> p));

        for (int i = 0; i < venda.getIdsProdutosVendidos().size(); i++) {
            String idProduto = venda.getIdsProdutosVendidos().get(i);
            int quantidadeVendida = venda.getQuantidades().get(i);

            Produto produto = produtoMap.get(idProduto);

            if (produto == null) {
                throw new IllegalArgumentException("Produto com ID " + idProduto + " não encontrado.");
            }

            if (produto.getQuantidade() < quantidadeVendida) {
                throw new IllegalStateException("Estoque insuficiente para o produto " + idProduto +
                        ". Disponível: " + produto.getQuantidade() + ", Solicitado: " + quantidadeVendida);
            }

            // Atualiza a quantidade no estoque
            produto.setQuantidade(produto.getQuantidade() - quantidadeVendida);
        }

        // Salva as alterações no estoque
        ProdutoController.salvarProdutos(new ArrayList<>(produtoMap.values()));
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

        // Primeiro remover produtos do estoque para garantir que há disponibilidade
        try {
            removerProdutosDoEstoque(venda);
            vendasExistentes.add(novaVendaMap);

            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(ARQUIVO_VENDAS), vendasExistentes);
            System.out.println("✅ Venda registrada com sucesso.");

            for (String item : venda.getIdsProdutosVendidos()) {
                Produto produto = ProdutoController.getProdutoPorIDOBJ(item);
                if (produto != null) {
                    String cpfCnpjLoja = produto.getIdLoja();
                    try {
                        LojaController.setIdVendaHistoricoLoja(cpfCnpjLoja, venda.getIdVenda());
                    } catch (Exception e) {
                        // TODO: handle exception
                    }

                }
            }
        } catch (IllegalStateException e) {
            System.err.println("❌ Erro ao processar a venda: " + e.getMessage());
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
        List<Venda> vendasParaRegistrar = new ArrayList<>();

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

            vendasParaRegistrar.add(novaVenda);

            Map<String, Object> vendaMap = construirVendaParaRegistro(novaVenda);
            vendaMap.put("id_loja", idLoja);
            vendaMap.put("id_vendaGeral", vendaOriginal.getIdVenda()); // Referência à venda original

            boolean vendaJaExiste = vendasExistentes.stream()
                    .anyMatch(v -> v.get("id_venda").equals(novoIdVenda));

            if (!vendaJaExiste) {
                vendasExistentes.add(vendaMap);
            }
        }

        // Primeiro verificamos e removemos produtos do estoque para todas as vendas
        try {
            for (Venda venda : vendasParaRegistrar) {
                removerProdutosDoEstoque(venda);
            }

            // Após remover todos os produtos com sucesso, salvamos as vendas
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(ARQUIVO_VENDAS), vendasExistentes);
            System.out.println("✅ Todas as vendas foram registradas com sucesso.");
        } catch (IllegalStateException e) {
            System.err.println("❌ Erro ao processar as vendas: " + e.getMessage());
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

    public static Map<String, List<Venda>> filtrarEVendasPorCPF(String cpf) {
        ObjectMapper mapper = new ObjectMapper();
        List<Venda> todasAsVendas = new ArrayList<>();

        try {
            // Usa o mesmo caminho que os outros métodos
            File arquivo = new File(ARQUIVO_VENDAS);

            if (!arquivo.exists() || arquivo.length() == 0) {
                return Collections.emptyMap();
            }

            List<Map<String, Object>> vendasJson = mapper.readValue(arquivo,
                    new TypeReference<List<Map<String, Object>>>() {
                    });

            for (Map<String, Object> vendaJson : vendasJson) {
                try {
                    String idVenda = (String) vendaJson.get("id_venda");
                    String idComprador = (String) vendaJson.get("id_comprador");

                    // Filtra apenas vendas do CPF informado
                    if (!idComprador.equals(cpf))
                        continue;

                    LocalDate dataVenda = LocalDate.parse((String) vendaJson.get("data"));
                    List<List<Object>> produtos = (List<List<Object>>) vendaJson.get("produtos");

                    List<String> idsProdutos = new ArrayList<>();
                    List<Integer> quantidades = new ArrayList<>();
                    List<Double> valoresUnitarios = new ArrayList<>();

                    for (List<Object> produto : produtos) {
                        idsProdutos.add((String) produto.get(0));
                        quantidades.add((Integer) produto.get(1));
                        valoresUnitarios.add(((Number) produto.get(2)).doubleValue());
                    }

                    Venda venda = new Venda(idVenda, idComprador, dataVenda,
                            idsProdutos, valoresUnitarios, quantidades);
                    todasAsVendas.add(venda);
                } catch (Exception e) {
                    System.err.println("Erro ao processar venda: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo de vendas: " + e.getMessage());
            return Collections.emptyMap();
        }

        // Agrupa por ID de venda (pedido)
        return todasAsVendas.stream()
                .collect(Collectors.groupingBy(Venda::getIdVenda));
    }
}