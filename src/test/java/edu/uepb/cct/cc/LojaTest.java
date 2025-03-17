package edu.uepb.cct.cc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import edu.uepb.cct.cc.model.Loja;

public class LojaTest {

    @Test
    void testCriacaoLojaValida() {
        Loja loja = new Loja("Minha Loja", "email@dominio.com", "senha123", "123.456.789-01", "Rua A, 123");
        assertEquals("Minha Loja", loja.getNome());
        assertEquals("email@dominio.com", loja.getEmail());
        assertEquals("123.456.789-01", loja.getCpfCnpj());
        assertEquals("Rua A, 123", loja.getEndereco());
    }

    @Test
    void testEmailInvalido() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Loja("Loja Teste", "email-invalido", "senha123", "123.456.789-01", "Rua A, 123");
        });
        assertEquals("E-mail inválido", exception.getMessage());
    }

    @Test
    void testCpfCnpjInvalido() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Loja("Loja Teste", "email@dominio.com", "senha123", "123456789", "Rua A, 123");
        });
        assertEquals("CPF ou CNPJ inválido", exception.getMessage());
    }

    @Test
    void testSetEmailValido() {
        Loja loja = new Loja();
        loja.setEmail("novo@teste.com");
        assertEquals("novo@teste.com", loja.getEmail());
    }

    @Test
    void testSetEmailInvalido() {
        Loja loja = new Loja();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            loja.setEmail("emailinvalido");
        });
        assertEquals("E-mail inválido", exception.getMessage());
    }

    @Test
    void testSetCpfCnpjValido() {
        Loja loja = new Loja();
        loja.setCpfCnpj("12.345.678/0001-99");
        assertEquals("12.345.678/0001-99", loja.getCpfCnpj());
    }

    @Test
    void testSetCpfCnpjInvalidoNaoFormatado() {
        Loja loja = new Loja();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            loja.setCpfCnpj("00000000");
        });
        assertEquals("CPF ou CNPJ inválido", exception.getMessage());
    }

    @Test
    void testSetCpfCnpjInvalidoComLetras() {
        Loja loja = new Loja();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            loja.setCpfCnpj("1b.345.678/0001-a9");
        });
        assertEquals("CPF ou CNPJ inválido", exception.getMessage());
    }
}
