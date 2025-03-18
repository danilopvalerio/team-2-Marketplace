package edu.uepb.cct.cc;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uepb.cct.cc.model.Comprador;
import edu.uepb.cct.cc.controller.CompradorController;
import org.junit.jupiter.api.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CompradorControllerTest {
    private static final String TEST_FILE_PATH = "src/main/resources/data/compradores_test.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() throws IOException {
        // Limpa o arquivo antes de cada teste
        new FileWriter(TEST_FILE_PATH, false).close();
    }

    @AfterAll
    static void cleanUp() {
        new File(TEST_FILE_PATH).delete();
    }

    @Test
    void testCreateComprador() {
        Comprador comprador = new Comprador("João", "joao@email.com", "senha123", "123.456.789-00", "Rua A");
        CompradorController.create(comprador);

        List<Comprador> compradores = CompradorController.getTodosCompradores();
        assertFalse(compradores.isEmpty());
        assertEquals("João", compradores.get(0).getNome());
    }

    @Test
    void testGetCompradorPorCpf() {
        Comprador comprador = new Comprador("Maria", "maria@email.com", "senha456", "987.654.321-00", "Rua B");
        CompradorController.create(comprador);

        Comprador encontrado = CompradorController.getCompradorPorCpf("987.654.321-00");
        assertNotNull(encontrado);
        assertEquals("Maria", encontrado.getNome());
    }

    @Test
    void testDeleteCompradorPorCpf() {
        Comprador comprador = new Comprador("Carlos", "carlos@email.com", "senha789", "111.222.333-44", "Rua C");
        CompradorController.create(comprador);

        String resultado = CompradorController.deleteCompradorPorCpf("111.222.333-44");
        assertEquals("Comprador removido com sucesso.", resultado);
    }
}