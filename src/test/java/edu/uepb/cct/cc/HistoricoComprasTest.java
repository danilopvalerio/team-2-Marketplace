package edu.uepb.cct.cc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import edu.uepb.cct.cc.controller.CompradorController;
import edu.uepb.cct.cc.controller.ProdutoController;
import edu.uepb.cct.cc.controller.VendaController;
import edu.uepb.cct.cc.model.Comprador;
import edu.uepb.cct.cc.model.Venda;

public class HistoricoComprasTest {

    @Test
    void testVerHistoricoVazio() {
        String cpf = "222.222.222-22";
        Comprador comprador = new Comprador("João", "joao@email.com", "senha123", cpf, "Rua A, 123");
        CompradorController.create(comprador);

        Map<String, List<Venda>> vendasPorCPF = VendaController.filtrarEVendasPorCPF(cpf);

        // Verifica se o histórico está vazio
        assertTrue(vendasPorCPF.isEmpty(), "O histórico de compras deveria estar vazio.");
        CompradorController.deleteCompradorPorCpf(cpf);
    }

    @Test
    void testVerificaHistoricoComPedidos() {
        String cpf = "222.222.222-22";
        Comprador comprador = new Comprador("Mario", "joao@email.com", "senha123", cpf, "Rua A, 123");
        CompradorController.create(comprador);

        ProdutoController.create("Smartphone", 2500.0f, "Eletrônico", 5, "Samsung", "Última geração",
                "0000",
                "12.345.678/0001-99");
        ProdutoController.create("Smartphone", 2500.0f, "Eletrônico", 5, "Samsung", "Última geração",
                "000A",
                "12.345.678/0001-99");

        List<String> idsProdutos = Arrays.asList("0000", "000A");
        List<Double> valores = Arrays.asList(10.0, 20.0);
        List<Integer> quantidades = Arrays.asList(2, 1);

        Venda venda = new Venda("YYYY", "222.222.222-22", LocalDate.of(2024, 4, 7), idsProdutos, valores, quantidades);
        VendaController vendaController = new VendaController();
        vendaController.registrarVenda(venda);
        VendaController.filtrarEVendasPorCPF(cpf);
        String vendasToString = VendaController.filtrarEVendasPorCPF(cpf).toString();
        assertTrue(
                vendasToString.contains("YYYY") && vendasToString.contains("000A") && vendasToString.contains("0000"));

        CompradorController.deleteCompradorPorCpf(cpf);
        ProdutoController.deleteProdutoPorID("0000");
        ProdutoController.deleteProdutoPorID("000A");
        vendaController.removerVenda(venda.getIdVenda());
    }
}
