package edu.uepb.cct.cc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import edu.uepb.cct.cc.model.Produto;

public class ProdutoTest {

    
    @Test
    void testCriacaoProdutoValido() {
        Produto produto = new Produto("Produto Teste", 100.0f, "Eletrônicos", 10, "Marca Teste", "Descrição do produto", "1", "123.456.789-00");
        assertEquals("Produto Teste", produto.getNome());
        assertEquals(100.0f, produto.getValor());
        assertEquals("Eletrônicos", produto.getTipo());
        assertEquals(10, produto.getQuantidade());
        assertEquals("Marca Teste", produto.getMarca());
        assertEquals("Descrição do produto", produto.getDescricao());
    }

    @Test
    void testValorProdutoInvalido() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Produto("Produto Teste", -10.0f, "Eletrônicos", 10, "Marca Teste", "Descrição do produto", "1", "123.456.789-00");
        });
        assertEquals("O valor do produto deve ser maior que zero.", exception.getMessage());
    }

    @Test
    void testQuantidadeProdutoNegativa() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Produto("Produto Teste", 100.0f, "Eletrônicos", -1, "Marca Teste", "Descrição do produto", "1", "123.456.789-00");
        });
        assertEquals("A quantidade não pode ser negativa.", exception.getMessage());
    }

    @Test
    void testSetDescricaoInvalida() {
        Produto produto = new Produto();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            produto.setDescricao("");
        });
        assertEquals("Descrição não pode ser vazia ou nula.", exception.getMessage());
    }

    @Test
    void testSetNomeInvalido() {
        Produto produto = new Produto();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            produto.setNome("");
        });
        assertEquals("Nome não pode ser vazio ou nulo.", exception.getMessage());
    }

    @Test
    void testSetTipoInvalido() {
        Produto produto = new Produto();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            produto.setTipo("");
        });
        assertEquals("Tipo não pode ser vazio ou nulo.", exception.getMessage());
    }
}
