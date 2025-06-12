package edu.uepb.cct.cc;

import edu.uepb.cct.cc.controller.CompradorController;
import edu.uepb.cct.cc.model.Comprador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CompradorControllerTest {

    private static final String ARQUIVO_COMPRADORES = "src/main/resources/data/compradores.json";

    @BeforeEach
    void limparArquivo() {
        try (Writer writer = new FileWriter(ARQUIVO_COMPRADORES)) {
            writer.write("[]"); // Resetando os dados antes de cada teste
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateComprador() {
        Comprador comprador = new Comprador("Ana", "ana@email.com", "senha123", "111.222.333-44", "Rua 1");
        CompradorController.create(comprador);

        Comprador compradorEncontrado = CompradorController.getCompradorPorCpf("111.222.333-44");

        assertNotNull(compradorEncontrado);
        assertEquals("Ana", compradorEncontrado.getNome());
    }

    @Test
    public void testCreateCompradorJaCadastrado() {
        Comprador comprador = new Comprador("Carlos", "carlos@email.com", "senha123", "999.888.777-66", "Rua 2");
        CompradorController.create(comprador);

        Comprador compradorDuplicado = new Comprador("Marcos", "marcos@email.com", "senha456", "999.888.777-66", "Rua 3");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CompradorController.create(compradorDuplicado);
        });

        assertEquals("Não foi possível adicionar o comprador pois ele já está cadastrado no sistema.", exception.getMessage());
    }

    @Test
    public void testGetCompradorPorCpf() {
        Comprador comprador = new Comprador("João", "joao@email.com", "senha123", "123.456.789-00", "Rua A, 123");
        CompradorController.create(comprador);

        Comprador compradorEncontrado = CompradorController.getCompradorPorCpf("123.456.789-00");

        assertNotNull(compradorEncontrado);
        assertEquals("João", compradorEncontrado.getNome());
    }

    @Test
    public void testDeleteCompradorPorCpf() {
        Comprador comprador = new Comprador("Maria", "maria@email.com", "senha123", "222.333.444-55", "Rua B");
        CompradorController.create(comprador);

        String resultado = CompradorController.deleteCompradorPorCpf("222.333.444-55");

        assertEquals("Comprador removido com sucesso.", resultado);
        assertNull(CompradorController.getCompradorPorCpf("222.333.444-55"));
    }

    @Test
    public void testDeleteCompradorInexistente() {
        String resultado = CompradorController.deleteCompradorPorCpf("000.000.000-00");
        assertEquals("Comprador não encontrado.", resultado);
    }

    @Test
    public void testAtualizarComprador() {
        Comprador comprador = new Comprador("Pedro", "pedro@email.com", "senha123", "777.666.555-44", "Rua C");
        CompradorController.create(comprador);

        Comprador compradorAtualizado = new Comprador("Pedro Silva", "pedrosilva@email.com", "novaSenha", "777.666.555-44", "Rua D");
        CompradorController.atualizarComprador("777.666.555-44", compradorAtualizado);

        Comprador compradorModificado = CompradorController.getCompradorPorCpf("777.666.555-44");

        assertNotNull(compradorModificado);
        assertEquals("Pedro Silva", compradorModificado.getNome());
        assertEquals("pedrosilva@email.com", compradorModificado.getEmail());
        assertEquals("novaSenha", compradorModificado.getSenha());
        assertEquals("Rua D", compradorModificado.getEndereco());
    }

    @Test
    public void testAtualizarCompradorInexistente() {
        Comprador compradorAtualizado = new Comprador("Lucas", "lucas@email.com", "senha123", "555.444.333-22", "Rua E");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CompradorController.atualizarComprador("555.444.333-22", compradorAtualizado);
        });

        assertEquals("Comprador não encontrado.", exception.getMessage());
    }

    @Test
    public void testGetTodosCompradoresOrdenados() {
        Comprador comprador1 = new Comprador("Zara", "zara@email.com", "senha123", "333.222.111-00", "Rua X");
        Comprador comprador2 = new Comprador("Ana", "ana@email.com", "senha123", "444.555.666-77", "Rua Y");
        Comprador comprador3 = new Comprador("Carlos", "carlos@email.com", "senha123", "555.666.777-88", "Rua Z");

        CompradorController.create(comprador1);
        CompradorController.create(comprador2);
        CompradorController.create(comprador3);

        List<Comprador> compradores = CompradorController.getTodosCompradores();

        assertEquals(3, compradores.size());
        assertEquals("Ana", compradores.get(0).getNome());
        assertEquals("Carlos", compradores.get(1).getNome());
        assertEquals("Zara", compradores.get(2).getNome());
    }

    @Test
    public void testAdicionarScore() {
        String cpf = "333.222.111-99";

        // cria comprador novo
        Comprador comprador1 = new Comprador("João", "joao@email.com", "senha123", cpf, "Rua Testes, 997");
        CompradorController.create(comprador1);

        // testa adição de score
        CompradorController.adicionarScore(cpf,3);

        Comprador compCriado = CompradorController.getCompradorPorCpf(cpf);
        assertEquals(3,compCriado.getScore());
    }

    @Test
    public void testCarregarCompradores() {
        // cria comprador novo
        Comprador comprador1 = new Comprador("João", "joao@email.com", "senha123", "222.678.000-00", "Rua Testes, 997");
        CompradorController.create(comprador1);

        List<Comprador> compradores = CompradorController.getTodosCompradores();
        List<Comprador> compradoresTest = CompradorController.carregarCompradores();

        // dados extraidos para comparar
        String toStringExpected = compradores.get(0).toString();
        String toStringActual = compradoresTest.get(0).toString();

        assertEquals(toStringExpected,toStringActual);
    }

    @Test
    public void testCarregarCompradoresComListaVazia() {
        List<Comprador> compradores = CompradorController.carregarCompradores();
        assertTrue(compradores.isEmpty());
    }

    @Test
    public void testSalvarCompradores() {
        Comprador comprador1 = new Comprador("João", "joao@email.com", "senha123", "222.678.000-99", "Rua Testes, 997");
        Comprador comprador2 = new Comprador("Maria","maria@email.com","senha123", "333.678.000-99", "Rua Testes, 996");
        Comprador comprador3 = new Comprador("José", "jose@email.com", "senha123", "444.678.000-99", "Rua Testes, 995");

        List<Comprador> compradores = new ArrayList<Comprador>();
        compradores.add(comprador1);
        compradores.add(comprador2);
        compradores.add(comprador3);

        CompradorController.salvarCompradores(compradores);

        List<Comprador> compradoresTest = CompradorController.carregarCompradores();

        for (int i = 0; i < compradoresTest.size(); i++) {
            String compExpected = compradores.get(i).toString();
            String compActual = compradoresTest.get(i).toString();

            assertEquals(compExpected,compActual);
        }
    }

    @Test
    public void testSalvarComradoresComListaVazia() {
        CompradorController.salvarCompradores(new ArrayList<Comprador>());
        List<Comprador> compradores = CompradorController.carregarCompradores();
        assertTrue(compradores.isEmpty());
    }
}
