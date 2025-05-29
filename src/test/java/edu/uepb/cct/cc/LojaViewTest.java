package edu.uepb.cct.cc;

import edu.uepb.cct.cc.view.LojaView;
import edu.uepb.cct.cc.controller.LojaController;
import edu.uepb.cct.cc.model.Loja;
import org.junit.jupiter.api.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class LojaViewTest {
    private LojaView lojaView;
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void setUp() {
        lojaView = new LojaView();
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        System.setIn(originalIn);

        // Limpar todas as lojas antes de cada teste
        for (Loja loja : LojaController.getTodasLojas()) {
            LojaController.deleteLojaPorCpfCnpj(loja.getCpfCnpj());
        }
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    public void testCadastrarLoja() {
        
        String input = "Loja Teste\nlojateste@gmail.com\nsenhateste\n122.345.678-89\nRua Principal, 100\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        lojaView.cadastrarLoja();

        Loja loja = LojaController.getLojaPorCpfCnpj("122.345.678-89");
        assertNotNull(loja);
        assertEquals("Loja Teste", loja.getNome());
        assertTrue(outputStream.toString().contains("Cadastro realizado com sucesso!"));
    }

    @Test
    public void testCadastrarLojaJaExistente() {
        
        String input = "Loja Teste\nlojateste@gmail.com\nsenhateste\n122.345.678-89\nRua Principal, 100\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        lojaView.cadastrarLoja();

        input = "Loja Teste2\nlojateste@gmail.com\nsenhateste\n122.345.678-89\nRua Principal, 100\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        lojaView.cadastrarLoja();
        assertTrue(outputStream.toString().contains("Erro: Loja já cadastrada no sistema."));
    }

    @Test
    public void testListarLojas() {
        String input = "Loja Teste Listar\nlojateste@gmail.com\nsenhateste\n122.345.678-89\nRua Principal, 100\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        lojaView.cadastrarLoja();

        lojaView.listarLojas();

        assertTrue(outputStream.toString().contains("122.345.678-89"));
        assertTrue(outputStream.toString().contains("Loja Teste Listar"));
    }

    @Test
    public void testListarLojasSemNenhumaCadastrada() {
        lojaView.listarLojas();
        assertTrue(outputStream.toString().contains("Nenhuma loja cadastrada."));
    }

    @Test
    public void testAtualizarLoja() {
        String input = "Loja Teste Listar\nlojateste@gmail.com\nsenhateste\n122.345.678-89\nRua Principal, 100\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        lojaView.cadastrarLoja();

        input = "122.345.678-89\nLoja Teste Atualizada\nlojateste@gmail.com\nsenhateste\nRua Principal, 100\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        lojaView.atualizarLoja();

        Loja atualizada = LojaController.getLojaPorCpfCnpj("122.345.678-89");
        assertEquals("Loja Teste Atualizada", atualizada.getNome());
        assertTrue(outputStream.toString().contains("Loja atualizada com sucesso!"));
    }

    @Test
    public void testAtualizarLojaInexistente() {
        String input = "122.345.678-89\nLoja Teste Atualizada\nlojateste@gmail.com\nsenhateste\nRua Principal, 100\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        lojaView.atualizarLoja();

        assertTrue(outputStream.toString().contains("Loja não encontrada."));
    }

    @Test
    public void testDeletarLoja() {
        String input = "Loja Teste Deletar\nlojateste@gmail.com\nsenhateste\n122.345.678-89\nRua Principal, 100\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        lojaView.cadastrarLoja();
        Loja loja = LojaController.getLojaPorCpfCnpj("122.345.678-89");
        assertNotNull(loja);

        input = "122.345.678-89\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        lojaView.deletarLoja();

        assertNull(LojaController.getLojaPorCpfCnpj("122.345.678-89"));
        assertTrue(outputStream.toString().contains("Loja deletada com sucesso!"));
    }

    @Test
    public void testDeletarLojaInexistente() {
        String input = "99.888.777/0001-66\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        lojaView.deletarLoja();

        assertTrue(outputStream.toString().contains("Erro: Loja não encontrada."));
    }

    @Test
    public void buscarLojaExistente() {
        String input = "Loja Teste buscar\nlojateste@gmail.com\nsenhateste\n122.345.678-89\nRua Principal, 100\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        lojaView.cadastrarLoja();
        Loja loja = LojaController.getLojaPorCpfCnpj("122.345.678-89");
        assertNotNull(loja);

        input = "122.345.678-89\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        lojaView.buscarLoja();
        
        assertTrue(outputStream.toString().contains("Loja Teste buscar"));
    }

    @Test
    public void buscarLojaInxistente() {
        String input = "122.345.678-89\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        lojaView.buscarLoja();
        
        assertTrue(outputStream.toString().contains("Loja não encontrada."));
    }


    @Test
    public void exibeConceitoDeveExibirTodosOsConceitosEmOrdem() {
        lojaView.conceitoLoja();
        String output = outputStream.toString();

        // Verifica se contém e se está na ordem correta
        int idxRuim = output.indexOf("Ruim");
        int idxMedio = output.indexOf("Médio");
        int idxBom = output.indexOf("Bom");
        int idxExcelente = output.indexOf("Excelente");

        assertTrue(idxRuim >= 0, "Deveria conter 'Ruim'");
        assertTrue(idxMedio > idxRuim, "'Médio' deve aparecer após 'Ruim'");
        assertTrue(idxBom > idxMedio, "'Bom' deve aparecer após 'Médio'");
        assertTrue(idxExcelente > idxBom, "'Excelente' deve aparecer após 'Bom'");
    }

    @Test
    public void exibeConceitoLojaVazia() {
        lojaView.conceitoLoja();
        String output = outputStream.toString();
        assertTrue(output.contains("Nenhuma loja cadastrada."));

    }

}


