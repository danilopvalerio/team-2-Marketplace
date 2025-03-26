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

}
