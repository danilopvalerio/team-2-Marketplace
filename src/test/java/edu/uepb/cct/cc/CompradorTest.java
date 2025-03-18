package edu.uepb.cct.cc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import edu.uepb.cct.cc.model.Comprador;

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
}
