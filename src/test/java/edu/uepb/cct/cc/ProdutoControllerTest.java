package edu.uepb.cct.cc;

import edu.uepb.cct.cc.model.Produto;
import edu.uepb.cct.cc.controller.ProdutoController;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ProdutoControllerTest {
    

    @Test
    public void testCreateProduto() {
        try {
            ProdutoController.deleteProdutoPorID("1");
        } catch (Exception e) {

        }

        String createdProduto = ProdutoController.create("Produto Teste", 100.0f, "Eletrônicos", 10, "Marca Teste",
                "Descrição do produto", "1", "123.456.789-00");

        assertTrue(createdProduto.contains("Produto Teste"));
        String verificador = ProdutoController.getProdutoPorID("1");
        assertTrue(verificador.contains("Produto Teste"));
    }

    @Test
    public void testAtualizarProduto() {
        // Atualiza o produto e verifica se a atualização foi bem-sucedida
        boolean resultado = ProdutoController.atualizarProduto("Produto Teste Atualizado", 150.0f, "Eletrônicos", 5,
                "Marca Teste",
                "Descrição atualizada", "1", "123.456.789-00");
        assertTrue(resultado); // Verifica se o produto foi atualizado corretamente

        String produtoVerificado = ProdutoController.getProdutoPorID("1");
        assertNotNull(produtoVerificado);
        assertTrue(produtoVerificado.contains("Produto Teste Atualizado"));
    }

    @Test
    public void testCreateProdutoInvalido() {

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ProdutoController.create("Produto Teste", -10.0f, "Eletrônicos", 10, "Marca Teste", "Descrição do produto",
                    "1", "123.456.789-00");
        });

        assertEquals("Produto ou dados inválidos.", exception.getMessage());
    }

    @Test
    public void testGetProdutoPorID() {

        String produtoEncontrado = ProdutoController.getProdutoPorID("1");
        assertNotNull(produtoEncontrado);
        assertTrue(produtoEncontrado.contains("Produto Teste"));
    }

    @Test
    public void testGetProdutoPorIDNaoEncontrado() {
        String produto = ProdutoController.getProdutoPorID("9ADSFF99");
        assertNull(produto);
    }

    @Test
    public void testDeleteProduto() {
        // Deleta o produto e verifica se a remoção foi bem-sucedida
        boolean resultado = ProdutoController.deleteProdutoPorID("1");
        assertTrue(resultado);

        String produtoVerificado = ProdutoController.getProdutoPorID("1");
        assertNull(produtoVerificado);
    }

    @Test
    public void testDeleteProdutoNaoEncontrado() {
        // Tenta deletar um produto que não existe e verifica se a exceção é lançada
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ProdutoController.deleteProdutoPorID("999");
        });
        assertEquals("Produto não encontrado.", exception.getMessage());
    }
}
