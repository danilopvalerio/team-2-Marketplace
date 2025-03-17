package edu.uepb.cct.cc;

import edu.uepb.cct.cc.model.Produto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProdutoModelTest {

    @Test
    public void testConstrutorValido() {
        Produto produto = new Produto("Produto A", 10.5f, "Categoria A", 5, "Marca A", "Descrição A");

        assertEquals("Produto A", produto.getNome());
        assertEquals(10.5f, produto.getValor());
        assertEquals("Categoria A", produto.getTipo());
        assertEquals(5, produto.getQuantidade());
        assertEquals("Marca A", produto.getMarca());
        assertEquals("Descrição A", produto.getDescricao());
    }

    @Test
    public void testConstrutorInvalidoNomeVazio() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Produto("", 10.5f, "Categoria A", 5, "Marca A", "Descrição A");
        });
        assertEquals("Nome não pode ser vazio ou nulo.", exception.getMessage());
    }

    @Test
    public void testConstrutorInvalidoNomeComCaracteresEspeciais() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Produto("Produto @", 10.5f, "Categoria A", 5, "Marca A", "Descrição A");
        });
        assertEquals("Nome do produto contém caracteres inválidos.", exception.getMessage());
    }

    @Test
    public void testConstrutorInvalidoValorNegativo() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Produto("Produto A", -10.5f, "Categoria A", 5, "Marca A", "Descrição A");
        });
        assertEquals("O valor do produto deve ser maior que zero.", exception.getMessage());
    }

    @Test
    public void testConstrutorInvalidoQuantidadeNegativa() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Produto("Produto A", 10.5f, "Categoria A", -5, "Marca A", "Descrição A");
        });
        assertEquals("A quantidade não pode ser negativa.", exception.getMessage());
    }

    @Test
    public void testConstrutorInvalidoTipoVazio() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Produto("Produto A", 10.5f, "", 5, "Marca A", "Descrição A");
        });
        assertEquals("Tipo não pode ser vazio ou nulo.", exception.getMessage());
    }

    @Test
    public void testConstrutorInvalidoTipoComCaracteresEspeciais() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Produto("Produto A", 10.5f, "Categoria @", 5, "Marca A", "Descrição A");
        });
        assertEquals("Tipo do produto contém caracteres inválidos.", exception.getMessage());
    }

    @Test
    public void testConstrutorInvalidoMarcaVazia() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Produto("Produto A", 10.5f, "Categoria A", 5, "", "Descrição A");
        });
        assertEquals("Marca não pode ser vazia ou nula.", exception.getMessage());
    }

    @Test
    public void testConstrutorInvalidoDescricaoVazia() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Produto("Produto A", 10.5f, "Categoria A", 5, "Marca A", "");
        });
        assertEquals("Descrição não pode ser vazia ou nula.", exception.getMessage());
    }

    @Test
    public void testToString() {
        Produto produto = new Produto("Produto A", 10.5f, "Categoria A", 5, "Marca A", "Descrição A");
        String expected = "Produto: {nome='Produto A', valor=R$ 10.50, tipo='Categoria A', quantidade=5, marca='Marca A', descricao='Descrição A'}";
        assertEquals(expected, produto.toString());
    }
}
