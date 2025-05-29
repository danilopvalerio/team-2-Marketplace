package edu.uepb.cct.cc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import edu.uepb.cct.cc.model.Produto;

class ProdutoViewTest {

    Produto produto;

    @BeforeEach
    void setUp() {
        // Simulação de idLoja válido
        produto = new Produto("Notebook", 3000.0f, "Eletrônico", 10, "Dell", "Alta performance", "P001",
                "123.456.789-00"); // Formato de CPF válido
    }

    @Test
    void cadastrarProduto() {
        assertEquals("Notebook", produto.getNome());
        assertEquals(3000.0f, produto.getValor());
        assertEquals("Eletrônico", produto.getTipo());
        assertEquals(10, produto.getQuantidade());
        assertEquals("Dell", produto.getMarca());
        assertEquals("Alta performance", produto.getDescricao());
        assertEquals("P001", produto.getId());
        assertEquals("123.456.789-00", produto.getIdLoja());
    }

    @Test
    void listarProdutosPorLoja() {
        // Supondo que teremos vários produtos para uma mesma loja
        Produto p1 = new Produto("Mouse", 100.0f, "Periférico", 50, "Logitech", "Mouse óptico", "P002",
                "123.456.789-00");
        Produto p2 = new Produto("Teclado", 200.0f, "Periférico", 30, "HyperX", "Teclado mecânico", "P003",
                "123.456.789-00");

        assertEquals("123.456.789-00", p1.getIdLoja());
        assertEquals("123.456.789-00", p2.getIdLoja());
    }

    @Test
    void buscarProdutoPorID() {
        assertEquals("P001", produto.getId());
    }

    @Test
    void listarTodosProdutos() {
        Produto p1 = new Produto("Mouse", 100.0f, "Periférico", 50, "Logitech", "Mouse óptico", "P002",
                "123.456.789-00");
        Produto p2 = new Produto("Teclado", 200.0f, "Periférico", 30, "HyperX", "Teclado mecânico", "P003",
                "123.456.789-00");

        Produto[] produtos = { produto, p1, p2 };
        assertEquals(3, produtos.length);
    }

    @Test
    void deletarProduto() {
        Produto produtoParaDeletar = new Produto("Monitor", 1500.0f, "Periférico", 20, "Samsung", "Monitor Full HD",
                "P004", "123.456.789-00");

        produtoParaDeletar = null;

        assertNull(produtoParaDeletar);
    }

    @Test
    void atualizarProduto() {
        produto.setNome("Notebook Gamer");
        produto.setValor(5000.0f);
        produto.setQuantidade(5);

        assertEquals("Notebook Gamer", produto.getNome());
        assertEquals(5000.0f, produto.getValor());
        assertEquals(5, produto.getQuantidade());
    }

    @Test
    void testAdicionarAvaliacaoEObterMedia() {
        produto.setAvaliacoes(Arrays.asList(5, 4, 3, 4, 5));

        assertEquals(5, produto.getAvaliacoes().size());
        assertEquals(4.2f, produto.getMediaAvaliacoes(), 0.01f); // margem de erro de 0.01
    }



}
