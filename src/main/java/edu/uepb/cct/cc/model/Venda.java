package edu.uepb.cct.cc.model;

import java.time.LocalDate;
import java.util.List;

public class Venda {
    private String idVenda;
    private String idComprador;
    private LocalDate dataVenda;
    private List<String> idsProdutosVendidos;
    private List<Double> valoresUnitarios;
    private List<Integer> quantidades;
    private double valorTotal;

    // Construtor completo
    public Venda(String idVenda, String idComprador, LocalDate dataVenda, List<String> idsProdutosVendidos,
            List<Double> valoresUnitarios, List<Integer> quantidades) {

        if (idVenda == null || idVenda.isBlank()) {
            throw new IllegalArgumentException("O ID da venda não pode ser nulo ou vazio.");
        }
        if (idComprador == null || idComprador.isBlank()) {
            throw new IllegalArgumentException("O ID do comprador não pode ser nulo ou vazio.");
        }
        if (dataVenda == null) {
            throw new IllegalArgumentException("A data da venda não pode ser nula.");
        }
        if (idsProdutosVendidos == null || valoresUnitarios == null || quantidades == null) {
            throw new IllegalArgumentException("Os detalhes dos produtos não podem ser nulos.");
        }
        if (idsProdutosVendidos.size() != valoresUnitarios.size() || idsProdutosVendidos.size() != quantidades.size()) {
            throw new IllegalArgumentException("Os tamanhos das listas de produtos devem ser iguais.");
        }
        if (valoresUnitarios.stream().anyMatch(v -> v < 0) || quantidades.stream().anyMatch(q -> q <= 0)) {
            throw new IllegalArgumentException("Os valores dos produtos devem ser positivos.");
        }

        this.idVenda = idVenda;
        this.idComprador = idComprador;
        this.dataVenda = dataVenda;
        this.idsProdutosVendidos = idsProdutosVendidos;
        this.valoresUnitarios = valoresUnitarios;
        this.quantidades = quantidades;
        this.valorTotal = calcularValorTotal();
    }

    private double calcularValorTotal() {
        double total = 0;
        for (int i = 0; i < valoresUnitarios.size(); i++) {
            total += valoresUnitarios.get(i) * quantidades.get(i);
        }
        return total;
    }

    // Getters
    public String getIdVenda() {
        return idVenda;
    }

    public String getIdComprador() {
        return idComprador;
    }

    public LocalDate getDataVenda() {
        return dataVenda;
    }

    public List<String> getIdsProdutosVendidos() {
        return idsProdutosVendidos;
    }

    public List<Double> getValoresUnitarios() {
        return valoresUnitarios;
    }

    public List<Integer> getQuantidades() {
        return quantidades;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    // toString atualizado (opcional)
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("🧾 ID Venda: ").append(idVenda).append("\n");
        sb.append("👤 Comprador: ").append(idComprador).append("\n");
        sb.append("🗓️ Data: ").append(dataVenda).append("\n");
        sb.append("📦 Produtos vendidos:\n");
        for (int i = 0; i < idsProdutosVendidos.size(); i++) {
            sb.append("   🔹 Produto: ").append(idsProdutosVendidos.get(i))
                    .append(" | Quantidade: ").append(quantidades.get(i))
                    .append(" | Valor unitário: R$").append(valoresUnitarios.get(i)).append("\n");
        }
        sb.append("💰 Total: R$").append(valorTotal).append("\n");
        return sb.toString();
    }
}
