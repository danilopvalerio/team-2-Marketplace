package edu.uepb.cct.cc;

import edu.uepb.cct.cc.model.Loja;
import edu.uepb.cct.cc.controller.LojaController;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



public class LojaControllerTest {

    @Test
    public void testCreateLoja() {
        Loja loja = new Loja("Loja Teste", "teste@email.com", "senha123", "123.456.789-00", "Endereço Teste");
        Loja createdLoja = LojaController.create(loja);
        assertNotNull(createdLoja);
        assertEquals("Loja Teste", createdLoja.getNome());
        assertEquals("teste@email.com", createdLoja.getEmail());
    }

    @Test
    public void testCreateLojaJaCadastrada() {
        Loja loja = new Loja("Loja Teste", "teste@email.com", "senha123", "123.456.789-00", "Endereço Teste");
        LojaController.create(loja);
        
        Loja loja2 = new Loja("Loja Teste 2", "teste2@email.com", "senha456", "123.456.789-00", "Endereço Teste 2");
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            LojaController.create(loja2);
        });
        
        assertEquals("Loja já cadastrada no sistema.", exception.getMessage());
    }

    @Test
    public void testGetLojaPorCpfCnpj() {
        Loja loja = new Loja("Loja Teste", "teste@email.com", "senha123", "123.456.789-00", "Endereço Teste");
        LojaController.create(loja);
        
        Loja lojaEncontrada = LojaController.getLojaPorCpfCnpj("123.456.789-00");
        
        assertNotNull(lojaEncontrada);
        assertEquals("Loja Teste", lojaEncontrada.getNome());
    }

    @Test
    public void testGetLojaPorCpfCnpjNaoEncontrada() {
        Loja loja = LojaController.getLojaPorCpfCnpj("999.999.999-99");
        assertNull(loja);
    }

    @Test
    public void testAtualizarLoja() {
        Loja loja = new Loja("Loja Teste", "teste@email.com", "senha123", "123.456.789-00", "Endereço Teste");
        LojaController.create(loja);
        
        Loja lojaAtualizada = new Loja("Loja Teste Atualizada", "teste@email.com", "novaSenha123", "123.456.789-00", "Novo Endereço Teste");
        LojaController.atualizarLoja("123.456.789-00", lojaAtualizada);
        
        Loja lojaVerificada = LojaController.getLojaPorCpfCnpj("123.456.789-00");
        assertNotNull(lojaVerificada);
        assertEquals("Loja Teste Atualizada", lojaVerificada.getNome());
    }

    @Test
    public void testDeleteLoja() {
        Loja loja = new Loja("Loja Teste", "teste@email.com", "senha123", "123.456.789-00", "Endereço Teste");
        LojaController.create(loja);
        
        List<Loja> lojasAntes = LojaController.getTodasLojas();
        assertEquals(1, lojasAntes.size());
        
        LojaController.deleteLojaPorCpfCnpj("123.456.789-00");
        
        List<Loja> lojasDepois = LojaController.getTodasLojas();
        assertEquals(0, lojasDepois.size());
    }

    @Test
    public void testDeleteLojaNaoEncontrada() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            LojaController.deleteLojaPorCpfCnpj("999.999.999-99");
        });
        assertEquals("Loja não encontrada.", exception.getMessage());
    }
}
