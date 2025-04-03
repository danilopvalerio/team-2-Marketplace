package edu.uepb.cct.cc.model;

import java.time.LocalDate;
import java.util.List;

public class Venda {
    private LocalDate dataVenda;
    private List<String> idsProdutosVendidos;
    private List<Double> valoresUnitarios;
    private List<Integer> quantidades;
    private double valorTotal;

    // Construtor com validações
    public Venda(LocalDate dataVenda, List<String> idsProdutosVendidos, List<Double> valoresUnitarios,
            List<Integer> quantidades) {
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

        this.dataVenda = dataVenda;
        this.idsProdutosVendidos = idsProdutosVendidos;
        this.valoresUnitarios = valoresUnitarios;
        this.quantidades = quantidades;
        this.valorTotal = calcularValorTotal();
    }

    // Método para calcular o valor total da venda
    private double calcularValorTotal() {
        double total = 0;
        for (int i = 0; i < valoresUnitarios.size(); i++) {
            total += valoresUnitarios.get(i) * quantidades.get(i);
        }
        return total;
    }

    // Getters
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

    // Setters com validação
    public void setDataVenda(LocalDate dataVenda) {
        if (dataVenda == null) {
            throw new IllegalArgumentException("A data da venda não pode ser nula.");
        }
        this.dataVenda = dataVenda;
    }

    public void setIdsProdutosVendidos(List<String> idsProdutosVendidos) {
        if (idsProdutosVendidos == null || idsProdutosVendidos.isEmpty()) {
            throw new IllegalArgumentException("A lista de produtos não pode ser vazia.");
        }
        this.idsProdutosVendidos = idsProdutosVendidos;
    }

    public void setValoresUnitarios(List<Double> valoresUnitarios) {
        if (valoresUnitarios == null || valoresUnitarios.isEmpty() || valoresUnitarios.stream().anyMatch(v -> v < 0)) {
            throw new IllegalArgumentException("Os valores unitários devem ser positivos e não vazios.");
        }
        this.valoresUnitarios = valoresUnitarios;
        this.valorTotal = calcularValorTotal(); // Recalcula o valor total
    }

    public void setQuantidades(List<Integer> quantidades) {
        if (quantidades == null || quantidades.isEmpty() || quantidades.stream().anyMatch(q -> q <= 0)) {
            throw new IllegalArgumentException("As quantidades devem ser positivas e não vazias.");
        }
        this.quantidades = quantidades;
        this.valorTotal = calcularValorTotal(); // Recalcula o valor total
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("🛒 Venda realizada em: ").append(dataVenda).append("\n");
        sb.append("📦 Produtos vendidos:\n");

        for (int i = 0; i < idsProdutosVendidos.size(); i++) {
            sb.append("   🔹 Produto ID: ").append(idsProdutosVendidos.get(i))
                    .append(" | Valor unitário: R$").append(valoresUnitarios.get(i))
                    .append(" | Quantidade: ").append(quantidades.get(i)).append("\n");
        }

        sb.append("💰 Valor total: R$").append(valorTotal).append("\n");
        return sb.toString();
    }
}
