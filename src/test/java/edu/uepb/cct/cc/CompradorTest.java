package edu.uepb.cct.cc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import edu.uepb.cct.cc.model.Comprador;
import edu.uepb.cct.cc.model.Produto;

public class CompradorTest {

    @Test
    void testCriacaoCompradorValido() {
        Comprador comprador = new Comprador("João", "joao@email.com", "senha123", "123.456.789-00", "Rua A, 123");
        assertEquals("João", comprador.getNome());
        assertEquals("joao@email.com", comprador.getEmail());
        assertEquals("123.456.789-00", comprador.getCpf());
        assertEquals("Rua A, 123", comprador.getEndereco());
    }

    @Test
    void testEmailInvalido() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Comprador("João", "email-invalido", "senha123", "123.456.789-00", "Rua A, 123");
        });
        assertEquals("E-mail inválido", exception.getMessage());
    }

    @Test
    void testCpfInvalido() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Comprador("João", "joao@email.com", "senha123", "12345678900", "Rua A, 123");
        });
        assertEquals("CPF inválido", exception.getMessage());
    }

    @Test
    void testSetEmailValido() {
        Comprador comprador = new Comprador();
        comprador.setEmail("novo@email.com");
        assertEquals("novo@email.com", comprador.getEmail());
    }

    @Test
    void testSetEmailInvalido() {
        Comprador comprador = new Comprador();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            comprador.setEmail("emailinvalido");
        });
        assertEquals("E-mail inválido", exception.getMessage());
    }

    @Test
    void testSetCpfValido() {
        Comprador comprador = new Comprador();
        comprador.setCpf("123.456.789-00");
        assertEquals("123.456.789-00", comprador.getCpf());
    }

    @Test
    void testSetCpfInvalidoNaoFormatado() {
        Comprador comprador = new Comprador();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            comprador.setCpf("12345678900");
        });
        assertEquals("CPF inválido", exception.getMessage());
    }

    @Test
    void testSetCpfInvalidoComLetras() {
        Comprador comprador = new Comprador();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            comprador.setCpf("123.456.abc-00");
        });
        assertEquals("CPF inválido", exception.getMessage());
    }

    @Test
    void testSetSenhaValida() {
        Comprador comprador = new Comprador();
        comprador.setSenha("senhaSegura123");
        assertEquals("senhaSegura123", comprador.getSenha());
    }

    @Test
    void testSetSenhaInvalida() {
        Comprador comprador = new Comprador();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            comprador.setSenha("123");
        });
        assertEquals("A senha deve ter pelo menos 6 caracteres", exception.getMessage());
    }

    @Test
    void testAdicionarProdutoAoCarrinhoQuantidadeValida() {
        Comprador comprador = new Comprador();
        Produto produto = new Produto("Mouse", 50.0f, "Periférico", 10, "Logitech", "Mouse USB óptico", "1",
                "12.345.678/0001-90");
        comprador.adicionarAoCarrinho(produto, 2);

        assertEquals(1, comprador.listarCarrinho().size());
        assertEquals(2, comprador.listarCarrinho().get(0).getQuantidade());
    }

    @Test
    void testAdicionarProdutoRepetidoAtualizaQuantidade() {
        Comprador comprador = new Comprador();
        Produto produto = new Produto("Mouse", 50.0f, "Periférico", 10, "Logitech", "Mouse USB óptico", "1",
                "12.345.678/0001-90");

        comprador.adicionarAoCarrinho(produto, 1);
        comprador.adicionarAoCarrinho(produto, 3);

        assertEquals(1, comprador.listarCarrinho().size());
        assertEquals(4, comprador.listarCarrinho().get(0).getQuantidade());
    }

    @Test
    void testAdicionarProdutoQuantidadeInvalida() {
        Comprador comprador = new Comprador();
        Produto produto = new Produto("Mouse", 50.0f, "Periférico", 10, "Logitech", "Mouse USB óptico", "1",
                "12.345.678/0001-90");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            comprador.adicionarAoCarrinho(produto, 0);
        });
        assertEquals("A quantidade deve ser maior que zero.", exception.getMessage());
    }

    @Test
    void testRemoverProdutoDoCarrinhoExistente() {
        Comprador comprador = new Comprador();
        Produto produto = new Produto("Teclado", 100.0f, "Periférico", 5, "Razer", "Teclado mecânico", "2",
                "12.345.678/0001-90");

        comprador.adicionarAoCarrinho(produto, 1);
        comprador.removerProdutoDoCarrinho(produto);

        assertEquals(0, comprador.listarCarrinho().size());
    }

    @Test
    void testRemoverProdutoNaoExistenteNoCarrinho() {
        Comprador comprador = new Comprador();
        Produto produto1 = new Produto("Teclado", 100.0f, "Periférico", 5, "Razer", "Teclado mecânico", "2",
                "12.345.678/0001-90");
        Produto produto2 = new Produto("Monitor", 800.0f, "Periférico", 2, "Dell", "Monitor 24\"", "3",
                "12.345.678/0001-90");

        comprador.adicionarAoCarrinho(produto1, 1);
        comprador.removerProdutoDoCarrinho(produto2);

        assertEquals(1, comprador.listarCarrinho().size());
    }

}
