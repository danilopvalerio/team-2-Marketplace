package edu.uepb.cct.cc;

import edu.uepb.cct.cc.controller.VendaController;
import edu.uepb.cct.cc.model.Loja;
import edu.uepb.cct.cc.model.Venda;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VendaLojaTest {

    @Test
    void testVendaEhRegistradaNoHistoricoDaLoja() {
        Loja loja = new Loja("Loja Teste", "teste@email.com", "senha123", "00.000.000/0001-91", "Rua Exemplo");
        VendaController controller = new VendaController();

        List<String> idsProdutos = Arrays.asList("P01", "P02");
        List<Double> valoresUnitarios = Arrays.asList(10.0, 20.0);
        List<Integer> quantidades = Arrays.asList(1, 2);

        Venda venda = new Venda("VTESTE01", "C01", LocalDate.now(), idsProdutos, valoresUnitarios, quantidades);

        controller.registrarVenda(venda);
        loja.adicionarVendaAoHistorico(venda.getIdVenda());

        assertTrue(loja.getHistoricoVendas().contains("VTESTE01"),
                "O histórico de vendas da loja deve conter o ID da venda registrada.");
    }
}
