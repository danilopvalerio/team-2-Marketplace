package edu.uepb.cct.cc;

import edu.uepb.cct.cc.model.Produto;
import edu.uepb.cct.cc.controller.ProdutoController;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.List;

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
        boolean resultado = ProdutoController.atualizarProduto("Produto Teste Atualizado", 150.0f, "Eletrônicos", 5,
                "Marca Teste",
                "Descrição atualizada", "1", "123.456.789-00");
        assertTrue(resultado);

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
        boolean resultado = ProdutoController.deleteProdutoPorID("1");
        assertTrue(resultado);

        String produtoVerificado = ProdutoController.getProdutoPorID("1");
        assertNull(produtoVerificado);
    }

    @Test
    public void testDeleteProdutoNaoEncontrado() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ProdutoController.deleteProdutoPorID("999");
        });
        assertEquals("Produto não encontrado.", exception.getMessage());
    }

    @Test
    public void testAdicionarAvaliacaoSemAvaliacoesPrevias() {
        ProdutoController.create("Produto Avaliacao", 100.0f, "Categoria", 10, "Marca", "Descricao", "A1",
                "123.456.789-00");

        ProdutoController.adicionar_avaliacao("A1", 4);

        Produto produto = ProdutoController.buscarProdutoPorID("A1");
        assertNotNull(produto);
        List<Integer> avaliacoes = produto.getAvaliacoes();
        assertNotNull(avaliacoes);
        assertEquals(1, avaliacoes.size());
        assertEquals(4, avaliacoes.get(0));
        assertEquals(4.0f, produto.getMediaAvaliacoes());

        ProdutoController.deleteProdutoPorID("A1");
    }

    @Test
    public void testAdicionarAvaliacaoComAvaliacoesAnteriores() {
        ProdutoController.create("Produto Avaliacao", 100.0f, "Categoria", 10, "Marca", "Descricao", "A2",
                "123.456.789-00");

        ProdutoController.adicionar_avaliacao("A2", 5);
        ProdutoController.adicionar_avaliacao("A2", 3);

        Produto produto = ProdutoController.buscarProdutoPorID("A2");
        assertNotNull(produto);
        List<Integer> avaliacoes = produto.getAvaliacoes();
        assertEquals(2, avaliacoes.size());
        assertEquals(5, avaliacoes.get(0));
        assertEquals(3, avaliacoes.get(1));

        ProdutoController.adicionar_avaliacao("A2", 4);

        produto = ProdutoController.buscarProdutoPorID("A2");
        assertEquals(3, produto.getAvaliacoes().size());
        assertEquals(4, produto.getAvaliacoes().get(2));

        float mediaEsperada = (5 + 3 + 4) / 3.0f;
        assertEquals(mediaEsperada, produto.getMediaAvaliacoes());

        ProdutoController.deleteProdutoPorID("A2");
    }

    @Test
    public void testAdicionarAvaliacaoInvalidaMenorQue1() {
        ProdutoController.create("Produto Avaliacao", 100.0f, "Categoria", 10, "Marca", "Descricao", "A3",
                "123.456.789-00");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ProdutoController.adicionar_avaliacao("A3", 0);
        });
        assertEquals("A nota deve ser um valor entre 1 e 5.", exception.getMessage());

        Produto produto = ProdutoController.buscarProdutoPorID("A3");
        assertTrue(produto.getAvaliacoes() == null || produto.getAvaliacoes().isEmpty());

        ProdutoController.deleteProdutoPorID("A3");
    }

    @Test
    public void testAdicionarAvaliacaoInvalidaMaiorQue5() {
        ProdutoController.create("Produto Avaliacao", 100.0f, "Categoria", 10, "Marca", "Descricao", "A4",
                "123.456.789-00");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ProdutoController.adicionar_avaliacao("A4", 6);
        });
        assertEquals("A nota deve ser um valor entre 1 e 5.", exception.getMessage());

        Produto produto = ProdutoController.buscarProdutoPorID("A4");
        assertTrue(produto.getAvaliacoes() == null || produto.getAvaliacoes().isEmpty());

        ProdutoController.deleteProdutoPorID("A4");
    }

    @Test
    public void testPersistenciaAdicionarAvaliacao() {
        ProdutoController.create("Produto Avaliacao", 100.0f, "Categoria", 10, "Marca", "Descricao", "A5",
                "123.456.789-00");

        ProdutoController.adicionar_avaliacao("A5", 5);

        Produto produto = ProdutoController.buscarProdutoPorID("A5");
        assertNotNull(produto);
        assertEquals(1, produto.getAvaliacoes().size());
        assertEquals(5, produto.getAvaliacoes().get(0));

        ProdutoController.deleteProdutoPorID("A5");
    }
}
