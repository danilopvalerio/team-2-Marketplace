package edu.uepb.cct.cc;

import edu.uepb.cct.cc.model.Produto;
import edu.uepb.cct.cc.controller.LojaController;
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
        Produto produto = new Produto("Produto Teste", 100.0f, "Eletrônicos", 10, "Marca Teste", "Descrição do produto", "1", "123.456.789-00");
        Produto createdProduto = ProdutoController.create(produto);

        assertNotNull(createdProduto);
        assertEquals("Produto Teste", createdProduto.getNome());
        assertEquals("Eletrônicos", createdProduto.getTipo());
        assertEquals(100.0f, createdProduto.getValor());
    }

    @Test
    public void testAtualizarProduto() {
        
        Produto produtoAtualizado = new Produto("Produto Teste Atualizado", 150.0f, "Eletrônicos", 5, "Marca Teste", "Descrição atualizada", "1", "123.456.789-00");
        ProdutoController.atualizarProduto("1", produtoAtualizado);

        Produto produtoVerificado = ProdutoController.getProdutoPorID("1");
        assertNotNull(produtoVerificado);
        assertEquals("Produto Teste Atualizado", produtoVerificado.getNome());
        assertEquals(150.0f, produtoVerificado.getValor());
    }

    @Test
    public void testCreateProdutoInvalido() {
        

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ProdutoController.create(new Produto("Produto Teste", -10.0f, "Eletrônicos", 10, "Marca Teste", "Descrição do produto", "1", "123.456.789-00"));
        });

        assertEquals("O valor do produto deve ser maior que zero.", exception.getMessage());
    }

    @Test
    public void testGetProdutoPorID() {
        
        Produto produtoEncontrado = ProdutoController.getProdutoPorID("1");
        assertNotNull(produtoEncontrado);
        assertEquals("Produto Teste Atualizado", produtoEncontrado.getNome());
    }

    @Test
    public void testGetProdutoPorIDNaoEncontrado() {
        Produto produto = ProdutoController.getProdutoPorID("9ADSFF99");
        assertNull(produto);
    }


    @Test
    public void testDeleteProduto() {
        
        ProdutoController.deleteProdutoPorID("1");

        Produto produtoVerificado = ProdutoController.getProdutoPorID("1");
        assertNull(produtoVerificado);
    }

    @Test
    public void testDeleteProdutoNaoEncontrado() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ProdutoController.deleteProdutoPorID("999");
        });
        assertEquals("Produto não encontrado.", exception.getMessage());
    }
}
