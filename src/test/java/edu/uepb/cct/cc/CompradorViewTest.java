package edu.uepb.cct.cc;

import edu.uepb.cct.cc.view.CompradorView;
import edu.uepb.cct.cc.controller.CompradorController;
import edu.uepb.cct.cc.model.Comprador;
import org.junit.jupiter.api.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class CompradorViewTest {
    private CompradorView compradorView;
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void setUp() {
        compradorView = new CompradorView();
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        System.setIn(originalIn);

        // Limpar todos os compradores antes de cada teste
        for (Comprador comprador : CompradorController.getTodosCompradores()) {
            CompradorController.deleteCompradorPorCpf(comprador.getCpf());
        }
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    public void testCadastrarComprador() {
        String input = "João Silva\njoao@email.com\nsenha123\n111.222.333-44\nRua A, 123\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        compradorView.cadastrarComprador();

        Comprador comprador = CompradorController.getCompradorPorCpf("111.222.333-44");
        assertNotNull(comprador);
        assertEquals("João Silva", comprador.getNome());
        assertTrue(outputStream.toString().contains("Cadastro realizado com sucesso!"));
    }


    @Test
    public void testCadastrarCompradorJaExistente() {
        Comprador comprador = new Comprador("Maria Oliveira", "maria@email.com", "senha123", "111.222.333-44", "Rua B, 456");
        CompradorController.create(comprador);

        String input = "Maria Oliveira\nmaria@email.com\nsenha123\n111.222.333-44\nRua B, 456\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        compradorView.cadastrarComprador();
        assertTrue(outputStream.toString().contains("Não foi possível adicionar o comprador"));
    }

    @Test
    public void testListarCompradores() {
        Comprador comprador = new Comprador("Carlos Souza", "carlos@email.com", "senha789", "123.456.789-00", "Rua C, 789");
        CompradorController.create(comprador);

        System.setIn(new ByteArrayInputStream("0\n".getBytes()));
        compradorView.listarCompradores();

        assertTrue(outputStream.toString().contains("Carlos Souza"));
    }

    @Test
    public void testListarCompradoresSemNenhumCadastrado() {
        compradorView.listarCompradores();
        assertTrue(outputStream.toString().contains("Nenhum comprador cadastrado."));
    }

    @Test
    public void testAtualizarComprador() {
        Comprador comprador = new Comprador("Ana Paula", "ana@email.com", "senha456", "222.333.444-55", "Rua D, 123");
        CompradorController.create(comprador);

        String input = "222.333.444-55\nAna Atualizada\nana_atualizado@email.com\nsenhaNova\nRua Nova, 456\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        compradorView.atualizarComprador();

        Comprador atualizado = CompradorController.getCompradorPorCpf("222.333.444-55");
        assertEquals("Ana Atualizada", atualizado.getNome());
        assertEquals("ana_atualizado@email.com", atualizado.getEmail());
        assertTrue(outputStream.toString().contains("Comprador atualizado com sucesso!"));
    }

    @Test
    public void testAtualizarCompradorInexistente() {
        String input = "999.888.777-66\nNovo Nome\nnova@email.com\nnovaSenha\nNovo Endereco\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        compradorView.atualizarComprador();

        assertTrue(outputStream.toString().contains("Comprador não encontrado."));
    }

    @Test
    public void testDeletarComprador() {
        Comprador comprador = new Comprador("Bruno Lima", "bruno@email.com", "senha789", "333.444.555-66", "Rua E, 321");
        CompradorController.create(comprador);

        String input = "333.444.555-66\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        compradorView.deletarComprador();

        assertNull(CompradorController.getCompradorPorCpf("333.444.555-66"));
        assertTrue(outputStream.toString().contains("Comprador removido com sucesso."));
    }

    @Test
    public void testDeletarCompradorInexistente() {
        String input = "999.888.777-66\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        compradorView.deletarComprador();

        assertTrue(outputStream.toString().contains("Nenhum comprador cadastrado para deletar."));
    }
}
