package edu.uepb.cct.cc;

import edu.uepb.cct.cc.controller.ProdutoController;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProdutoControllerTest {
    private ProdutoController controller;

    @BeforeEach
    void setUp() {
        controller = new ProdutoController();
    }

    @Test
    void testCadastrarProduto() {
        try {
            controller.excluirProduto(null)
        } catch (Exception e) {
            // Ignora a exceção caso o produto já exista
        }
        String resultado = controller.cadastrarProduto("Mouse", 150.00f, "Periférico", 20, "Logitech", "Mouse sem fio");
        assertEquals("Produto cadastrado com sucesso.", resultado);
    }

    @Test
    void testCadastrarProdutoDuplicado() {
        controller.cadastrarProduto("Mouse", 150.00f, "Periférico", 20, "Logitech", "Mouse sem fio");
        String resultado = controller.cadastrarProduto("Mouse", 150.00f, "Periférico", 20, "Logitech", "Mouse sem fio");
        assertEquals("Produto já existe no sistema.", resultado);
    }

    @Test
    void testVisualizarProduto() {
        controller.cadastrarProduto("Teclado", 200.00f, "Periférico", 10, "Razer", "Teclado mecânico");
        String resultado = controller.visualizarProduto("Teclado");
        assertTrue(resultado.contains("Teclado"));
    }

    @Test
    void testExcluirProduto() {
        controller.cadastrarProduto("Monitor", 800.00f, "Display", 5, "LG", "Monitor Full HD");
        String resultado = controller.excluirProduto("Monitor");
        assertEquals("Produto removido com sucesso.", resultado);
    }

    @Test
    void testAtualizarProduto() {
        controller.cadastrarProduto("Cadeira", 1000.00f, "Mobiliário", 3, "DXRacer", "Cadeira gamer");
        String resultado = controller.atualizarProduto("Cadeira", "valor", "1200.00");
        assertEquals("Produto atualizado com sucesso.", resultado);
    }
}
