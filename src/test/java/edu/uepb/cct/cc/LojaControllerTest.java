package edu.uepb.cct.cc;

import edu.uepb.cct.cc.controller.ProdutoController;
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

    @Test
    public void testCalcularMedia_TodosProdutosComAvaliacao() {
        String cpf = "911.111.111-11";

        // deletando loja se já existir
        try {LojaController.deleteLojaPorCpfCnpj(cpf);} catch (Exception e) {/**/}

        Loja loja = LojaController.createLoja("Loja A", "a@email.com", "senha", cpf, "Rua A");

        // deletando produtos se já existirem
        try {
            ProdutoController.deleteProdutoPorID("P1ID");
            ProdutoController.deleteProdutoPorID("P2ID");
            ProdutoController.deleteProdutoPorID("P3ID");
        }
        catch (Exception e) {/**/}

        ProdutoController.create("P1", 10, "TipoTeste", 5, "MarcaTeste", "Desc", "P1ID", cpf);
        ProdutoController.create("P2", 15, "TipoTeste", 5, "MarcaTeste", "Desc", "P2ID", cpf);
        ProdutoController.create("P3", 20, "TipoTeste", 5, "MarcaTeste", "Desc", "P3ID", cpf);

        ProdutoController.adicionar_avaliacao("P1ID", 4);
        ProdutoController.adicionar_avaliacao("P2ID", 3);
        ProdutoController.adicionar_avaliacao("P3ID", 5);

        double media = LojaController.calcularMediaAvaliacoesLoja(cpf);
        assertEquals(4.0, media, 0.1);

        Loja lojaAtualizada = LojaController.getLojaPorCpfCnpj(cpf);
        assertEquals(4.0, lojaAtualizada.getMediaAvaliacoesProdutos(), 0.1);

        // limpar produtos e lojas criados
        try {
            ProdutoController.deleteProdutoPorID("P1ID");
            ProdutoController.deleteProdutoPorID("P2ID");
            ProdutoController.deleteProdutoPorID("P3ID");
        } catch (Exception e) {
            // Ignora caso o produto já tenha sido excluído
        }
        try {
            LojaController.deleteLojaPorCpfCnpj(cpf);
        } catch (Exception e) {
            // Ignora caso a loja já tenha sido excluída
        }
    }

    @Test
    public void testCalcularMedia_NenhumProdutoAvaliado() {
        String cpf = "922.222.222-22";
        // deletando loja se já existir
        try {LojaController.deleteLojaPorCpfCnpj(cpf);} catch (Exception e) {/**/}
        Loja loja = LojaController.createLoja("Loja B", "b@email.com", "senha", cpf, "Rua B");

        // deletando produtos se já existirem
        try {
            ProdutoController.deleteProdutoPorID("P4ID");
            ProdutoController.deleteProdutoPorID("P5ID");
        }
        catch (Exception e) {/**/}

        ProdutoController.create("P4", 10, "Tipo", 5, "Marca", "Desc", "P4ID", cpf);
        ProdutoController.create("P5", 15, "Tipo", 5, "Marca", "Desc", "P5ID", cpf);

        // Nenhuma avaliação feita, médias serão 0
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            LojaController.calcularMediaAvaliacoesLoja(cpf);
        });

        assertEquals("A loja não tem produtos avaliados.", exception.getMessage());

        // limpar produtos e lojas criados
        try {
            ProdutoController.deleteProdutoPorID("P4ID");
            ProdutoController.deleteProdutoPorID("P5ID");
        } catch (Exception e) {
            // Ignora caso o produto já tenha sido excluído
        }
        try {
            LojaController.deleteLojaPorCpfCnpj(cpf);
        } catch (Exception e) {
            // Ignora caso a loja já tenha sido excluída
        }
    }

    @Test
    public void testCalcularMedia_ProdutosComEMediaZero() {
        String cpf = "933.333.333-33";
        // deletando loja se já existir
        try {LojaController.deleteLojaPorCpfCnpj(cpf);} catch (Exception e) {/**/}
        Loja loja = LojaController.createLoja("Loja C", "c@email.com", "senha", cpf, "Rua C");

        // deletando produtos se já existirem
        try {
            ProdutoController.deleteProdutoPorID("P6ID");
            ProdutoController.deleteProdutoPorID("P7ID");
            ProdutoController.deleteProdutoPorID("P8ID");
        }
        catch (Exception e) {/**/}

        ProdutoController.create("P6", 10, "Tipo", 5, "Marca", "Desc", "P6ID", cpf);
        ProdutoController.create("P7", 15, "Tipo", 5, "Marca", "Desc", "P7ID", cpf);
        ProdutoController.create("P8", 20, "Tipo", 5, "Marca", "Desc", "P8ID", cpf);

        ProdutoController.adicionar_avaliacao("P7ID", 5);
        ProdutoController.adicionar_avaliacao("P8ID", 3);
        // Produto P6ID não recebe avaliação → média = 0 → deve ser ignorado

        double media = LojaController.calcularMediaAvaliacoesLoja(cpf);
        assertEquals(4.0, media, 0.1); // (5 + 3) / 2 = 4.0

        Loja lojaAtualizada = LojaController.getLojaPorCpfCnpj(cpf);
        assertEquals(4.0, lojaAtualizada.getMediaAvaliacoesProdutos(), 0.1);

        // limpar produtos e lojas criados
        try {
            ProdutoController.deleteProdutoPorID("P6ID");
            ProdutoController.deleteProdutoPorID("P7ID");
            ProdutoController.deleteProdutoPorID("P8ID");
        } catch (Exception e) {
            // Ignora caso o produto já tenha sido excluído
        }
        try {
            LojaController.deleteLojaPorCpfCnpj(cpf);
        } catch (Exception e) {
            // Ignora caso a loja já tenha sido excluída
        }
    }
}
