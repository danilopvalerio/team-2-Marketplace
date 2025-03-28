package edu.uepb.cct.cc;

import edu.uepb.cct.cc.model.Loja;
import edu.uepb.cct.cc.controller.LojaController;


import static org.junit.jupiter.api.Assertions.*;



import org.junit.jupiter.api.Test;

public class LojaControllerTest {

    // Para esse primeiro teste esta loja não deve existir no sistema.
    @Test
    public void testCreateLoja() {
        try {
            LojaController.deleteLojaPorCpfCnpj("123.456.789-00");
        } catch (Exception e) {
            // Este try catch é necessário para caso a loja exista no sistema, ela deve ser removida.
        }
        Loja createdLoja = LojaController.createLoja("Loja Teste", "teste@email.com", "senha123", "123.456.789-00", "Endereço Teste");

        // Verifica se a loja foi criada corretamente
        assertNotNull(createdLoja);
        assertEquals("Loja Teste", createdLoja.getNome());
        assertEquals("teste@email.com", createdLoja.getEmail());
    }

    @Test
    public void testCreateLojaJaCadastrada() {

        LojaController.createLoja("Loja Teste", "teste@email.com", "senha123", "123.456.789-00", "Endereço Teste");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            LojaController.createLoja("Loja Teste 2", "teste2@email.com", "senha456", "123.456.789-00", "Endereço Teste 2");
        });

        assertEquals("Loja já cadastrada no sistema.", exception.getMessage());
    }

    @Test
    public void testGetLojaPorCpfCnpj() {
        // Para este teste, o teste anterior deve ter sido executado.
        Loja lojaEncontrada = LojaController.getLojaPorCpfCnpj("123.456.789-00");

        assertNotNull(lojaEncontrada);
        // assertEquals("Loja Teste", lojaEncontrada.getNome());
    }

    @Test
    public void testGetLojaPorCpfCnpjNaoEncontrada() {
        Loja loja = LojaController.getLojaPorCpfCnpj("999.999.999-99");
        assertNull(loja);
    }

    @Test
    public void testAtualizarLoja() {

        LojaController.atualizarLoja("123.456.789-00", "Loja Teste Atualizada", "teste@email.com", "novaSenha123",
                "Novo Endereço Teste");

        Loja lojaVerificada = LojaController.getLojaPorCpfCnpj("123.456.789-00");
        assertNotNull(lojaVerificada);
        assertEquals("Loja Teste Atualizada", lojaVerificada.getNome());
    }

    @Test
    public void testDeleteLoja() {

        LojaController.deleteLojaPorCpfCnpj("123.456.789-00");

        assertNull(LojaController.getLojaPorCpfCnpj("123.456.789-00"));
    }

    @Test
    public void testDeleteLojaNaoEncontrada() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            LojaController.deleteLojaPorCpfCnpj("999.999.999-99");
        });
        assertEquals("Loja não encontrada.", exception.getMessage());
    }
}
