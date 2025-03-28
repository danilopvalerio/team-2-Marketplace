package edu.uepb.cct.cc;
import edu.uepb.cct.cc.view.ProdutoView;
import org.junit.jupiter.api.*;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    @Order(1)
    public void testCadastrarProduto_entradaValida() {
        String input = "Produto Teste134\n100.0\nEletrônicos\n10\nMarca X\nProduto de teste\nP12345\n123.456.789-01\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        produtoView.cadastrarProduto("123.456.789-01");

        String output = outputStream.toString();
        assertTrue(output.contains("Produto cadastrado com sucesso!"));
    }

    @Test
    @Order(2)
    public void testAtualizarProduto_produtoAtualizado() {
        String input = "P12345\nNovo Produto p22\n150.0\nAcessórios\n20\nMarca Y\nNovo produto\n123.456.789-01\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ProdutoView.atualizarProduto();
        ProdutoView.buscarProdutoPorID("P12345");
        String output = outputStream.toString();
        assertTrue(output.contains("Produto atualizado com sucesso!"));
    }

    @Test
    @Order(3)
    public void testCadastrarProduto_valorInvalido() {
        String input = "Produto Teste\n-10.0\nEletrônicos\n10\nMarca X\nProduto de teste\nP12345\n12345678901234\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ProdutoView.cadastrarProduto("133.222.344-98");

        String output = outputStream.toString();
        assertTrue(output.contains("O valor deve ser maior que zero. Insira novamente: "));
    }

    @Test
    @Order(4)
    public void testCadastrarProduto_quantidadeNegativa() {
        String input = "Produto Teste\n100.0\nEletrônicos\n-5\nMarca X\nProduto de teste\nP12345\n12345678901234\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        produtoView.cadastrarProduto("133.222.344-98");

        String output = outputStream.toString();
        assertTrue(output.contains("A quantidade não pode ser negativa. Insira novamente: "));
    }

    @Test
    @Order(5)
    public void testListarProdutosPorLoja_produtosEncontrados() {
        String input = "123.456.789-01\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ProdutoView.listarProdutosPorLoja("12345678901234");

        String output = outputStream.toString();
        assertTrue(output.contains("Produtos da Loja"));
    }

    @Test
    @Order(6)
    public void testListarProdutosPorLoja_semProdutos() {
        String input = "12345678901234\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ProdutoView.listarProdutosPorLoja("12345678901234");

        String output = outputStream.toString();
        assertTrue(output.contains("Nenhum produto encontrado para a loja"));
    }

    @Test
    @Order(7)
    public void testBuscarProdutoPorID_produtoEncontrado() {
        String input = "P12345\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ProdutoView.buscarProdutoPorID("P12345");

        String output = outputStream.toString();
        assertTrue(output.contains("Novo Produto p22"));
    }

    @Test
    @Order(8)
    public void testBuscarProdutoPorID_produtoNaoEncontrado() {
        String input = "P99999\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ProdutoView.buscarProdutoPorID("P99999");

        String output = outputStream.toString();
        System.out.println(output);
        assertTrue(output.contains("Produto não encontrado."));
    }

    @Test
    @Order(9)
    public void testAtualizarProduto_produtoNaoEncontrado() {
        String input = "P99999\nNovo Produto\n150.0\nAcessórios\n20\nMarca Y\nNovo produto\nP99999\n123.456.789-01\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ProdutoView.atualizarProduto();

        String output = outputStream.toString();
        assertTrue(output.contains("Produto não encontrado."));
    }

    @Test
    @Order(10)
    public void testDeletarProduto_produtoDeletado() {
        String input = "P12345\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ProdutoView.deletarProduto();

        String output = outputStream.toString();
        assertTrue(output.contains("Produto removido com sucesso!"));
    }

    @Test
    @Order(11)
    public void testDeletarProduto_produtoNaoEncontrado() {
        String input = "P99999\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ProdutoView.deletarProduto();

        String output = outputStream.toString();
        assertTrue(output.contains("Erro: Produto não encontrado."));
    }
}
