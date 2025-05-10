package edu.uepb.cct.cc;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import edu.uepb.cct.cc.controller.VendaController;
import edu.uepb.cct.cc.model.Comprador;
import edu.uepb.cct.cc.model.Produto;
import edu.uepb.cct.cc.model.Venda;

public class VendaViewTest {

    @Test
    public void testFinalizarCompraComConfirmacaoPositiva() {
        Comprador comprador = new Comprador("Leticia", "leticia@email.com", "senha123", "123.456.789-00", "Rua A, 123");

        Produto produto = new Produto("Notebook", 3000.0f, "Eletrônico", 10, "Dell", "Alta performance", "P001",
                "12.345.678/0001-99");

        comprador.adicionarAoCarrinho(produto, 2);

        Venda venda = new Venda("V001", comprador.getCpf(), LocalDate.now(),
                List.of(produto.getId()),
                List.of((double) produto.getValor()),
                List.of(2));

        VendaController vendaController = new VendaController();
        vendaController.registrarVenda(venda);

        assertTrue(vendaController.carregarVendasRegistradas()
                .stream().anyMatch(v -> v.get("id_venda").equals("V001")),
                "A venda deveria ser registrada corretamente.");
    }

    @Test
    public void testFinalizarCompraComConfirmacaoNegativa() {
        Comprador comprador = new Comprador("Carlos", "carlos@email.com", "senha123", "987.654.321-00",
                "Av. Brasil, 500");

        Produto produto = new Produto("Smartphone", 2500.0f, "Eletrônico", 5, "Samsung", "Última geração", "P002",
                "12.345.678/0001-99");

        comprador.adicionarAoCarrinho(produto, 1);

        Venda venda = new Venda("V002", comprador.getCpf(), LocalDate.now(),
                List.of(produto.getId()),
                List.of((double) produto.getValor()),
                List.of(1));

        VendaController vendaController = new VendaController();
        vendaController.registrarVenda(venda);
        vendaController.removerVenda("V002");

        assertFalse(vendaController.carregarVendasRegistradas()
                .stream().anyMatch(v -> v.get("id_venda").equals("V002")),
                "A venda não deveria ser registrada.");
    }
}
