package edu.uepb.cct.cc;

import edu.uepb.cct.cc.view.ProdutoView;
import edu.uepb.cct.cc.controller.ProdutoController;
import edu.uepb.cct.cc.model.Produto;
import org.junit.jupiter.api.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class ProdutoViewTest {
    private ProdutoView produtoView;
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void setUp() {
        produtoView = new ProdutoView();
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        System.setIn(originalIn);

        // Limpar todos os produtos antes de cada teste
        for (Produto produto : ProdutoController.getTodosProdutos()) {
            ProdutoController.deleteProdutoPorID(produto.getId());
        }
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    public void testCadastrarProduto() {
        String input = "Produto A\n10.0\nTipo X\n100\nMarca Y\nProduto de teste\nP001\n123.456.789-00\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        produtoView.cadastrarProduto("Admin");

        Produto produto = ProdutoController.getProdutoPorID("P001");
        assertNotNull(produto);
        assertEquals("Produto A", produto.getNome());
        assertTrue(outputStream.toString().contains("Produto cadastrado com sucesso!"));
    }

    @Test
    public void testCadastrarProdutoComValorInvalido() {
        String input = "Produto B\n-10.0\nTipo Y\n50\nMarca Z\nProduto inválido\nP002\n123.456.789-00\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        produtoView.cadastrarProduto("Admin");

        assertTrue(outputStream.toString().contains("Erro: O valor deve ser maior que zero."));
    }

    @Test
    public void testCadastrarProdutoComQuantidadeNegativa() {
        String input = "Produto C\n20.0\nTipo Z\n-5\nMarca A\nProduto com quantidade negativa\nP003\n123.456.789-00\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        produtoView.cadastrarProduto("Admin");

        assertTrue(outputStream.toString().contains("Erro: A quantidade não pode ser negativa."));
    }

    @Test
    public void testListarProdutosPorLoja() {
        Produto produto = new Produto("Produto D", (float) 30.0, "Tipo W", 10, "Marca B", "Descrição", "P004", "123.456.789-00");
        ProdutoController.create(produto);

        System.setIn(new ByteArrayInputStream("123.456.789-00\n".getBytes()));
        ProdutoView.listarProdutosPorLoja("123.456.789-00");

        assertTrue(outputStream.toString().contains("Produto D"));
    }

    @Test
    public void testListarProdutosPorLojaSemProdutos() {
        ProdutoView.listarProdutosPorLoja("123.456.789/00");
        assertTrue(outputStream.toString().contains("Nenhum produto encontrado para a loja com CNPJ/CPF: 123.456.789/00"));
    }
    
    @Test
    public void testCadastrarProdutoJaExistente() {
        Produto produto = new Produto("Produto E", (float) 15.0, "Tipo V", 30, "Marca C", "Descrição", "P005", "123.456.789-00");
        ProdutoController.create(produto);

        String input = "Produto E\n15.0\nTipo V\n30\nMarca C\nDescrição\nP005\n123.456.789-00\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        produtoView.cadastrarProduto("Admin");

        assertTrue(outputStream.toString().contains("Erro: Produto já cadastrado no sistema com o mesmo ID."));
    }
}
