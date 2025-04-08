package edu.uepb.cct.cc;

import edu.uepb.cct.cc.controller.ProdutoController;
import edu.uepb.cct.cc.controller.VendaController;
import edu.uepb.cct.cc.model.Produto;
import edu.uepb.cct.cc.model.Venda;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class VendaControllerTest {

    private VendaController vendaController;
    private static final String ID_PRODUTO = "VP1";

    @BeforeEach
    public void setup() {
        vendaController = new VendaController();

        try {
            ProdutoController.deleteProdutoPorID(ID_PRODUTO);
        } catch (Exception ignored) {
        }

        ProdutoController.create("Produto Venda", 50.0f, "Categoria Teste", 20,
                "Marca Teste", "Descrição", ID_PRODUTO, "999.999.999-99");
    }

    @AfterEach
    public void cleanup() {
        ProdutoController.deleteProdutoPorID(ID_PRODUTO);
        vendaController.removerVenda("VTESTE-999.999.999-99-VENDA");
    }

    @Test
    public void testRegistrarVendaSimples() {
        Venda venda = new Venda(
                "VTESTE-999.999.999-99-VENDA",
                "999.999.999-99",
                LocalDate.now(),
                List.of(ID_PRODUTO),
                List.of(50.0),
                List.of(2));

        vendaController.registrarVenda(venda);

        List<Map<String, Object>> vendas = vendaController.carregarVendasRegistradas();

        boolean encontrada = vendas.stream()
                .anyMatch(v -> v.get("id_venda").equals("VTESTE-999.999.999-99-VENDA"));

        assertTrue(encontrada);
    }

    @Test
    public void testRemoverVendaExistente() {
        Venda venda = new Venda(
                "VTESTE-999.999.999-99-VENDA",
                "999.999.999-99",
                LocalDate.now(),
                List.of(ID_PRODUTO),
                List.of(50.0),
                List.of(1));

        vendaController.registrarVenda(venda);
        vendaController.removerVenda("VTESTE-999.999.999-99-VENDA");

        List<Map<String, Object>> vendas = vendaController.carregarVendasRegistradas();

        boolean aindaExiste = vendas.stream()
                .anyMatch(v -> v.get("id_venda").equals("VTESTE-999.999.999-99-VENDA"));

        assertFalse(aindaExiste);
    }

    @Test
    public void testRegistrarVendaDuplicada() {
        Venda venda = new Venda(
                "VTESTE-999.999.999-99-VENDA",
                "999.999.999-99",
                LocalDate.now(),
                List.of(ID_PRODUTO),
                List.of(50.0),
                List.of(1));

        vendaController.registrarVenda(venda);

        vendaController.registrarVenda(venda);

        List<Map<String, Object>> vendas = vendaController.carregarVendasRegistradas();

        long count = vendas.stream()
                .filter(v -> v.get("id_venda").equals("VTESTE-999.999.999-99-VENDA"))
                .count();

        assertEquals(1, count);
    }

    @Test
    public void testRemoverVendaInexistente() {
        assertDoesNotThrow(() -> vendaController.removerVenda("VENDA-INEXISTENTE"));
    }

    @Test
    void testRemoverProdutosDoEstoque() throws IOException {
        VendaController vendaController = new VendaController();
        ObjectMapper mapper = new ObjectMapper();
        File arquivo = new File("src/main/resources/data/produtos.json");

        String idProduto = "CP01";

        List<Produto> produtosAntes = Arrays.asList(mapper.readValue(arquivo, Produto[].class));
        int quantidadeOriginal = produtosAntes.stream()
                .filter(p -> p.getId().equals(idProduto))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"))
                .getQuantidade();

        String idVenda = "VENDA_TESTE_001";
        String idComprador = "12345678900";
        List<String> ids = List.of(idProduto);
        List<Double> valores = List.of(10.0);
        List<Integer> quantidades = List.of(1);

        Venda venda = new Venda(idVenda, idComprador, LocalDate.now(), ids, valores, quantidades);

        vendaController.removerProdutosDoEstoque(venda);

        List<Produto> produtosDepois = Arrays.asList(mapper.readValue(arquivo, Produto[].class));
        int quantidadeFinal = produtosDepois.stream()
                .filter(p -> p.getId().equals(idProduto))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"))
                .getQuantidade();

        assertEquals(quantidadeOriginal - 1, quantidadeFinal);
    }
}
