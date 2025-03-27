package edu.uepb.cct.cc;

import edu.uepb.cct.cc.controller.ProdutoController;
import edu.uepb.cct.cc.model.Produto;
import edu.uepb.cct.cc.view.ProdutoView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class ProdutoViewTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private ProdutoView produtoView;

    @BeforeEach
    public void setUp() {
        // Configura o fluxo de saída para capturar o que é impresso no console
        System.setOut(new PrintStream(outputStreamCaptor));
        produtoView = new ProdutoView();
    }

    @Test
    public void testCadastrarProdutoValido() {
        // Simulando a entrada de dados do usuário
        String simulatedInput = "Produto Teste\n10.0\nTipo A\n5\nMarca X\nDescrição Teste\n123\n123456789\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Chama o metodo para cadastrar o produto
        produtoView.cadastrarProduto("Admin");

        // Verifica se a saída contém a mensagem esperada
        assertTrue(outputStreamCaptor.toString().contains("Produto cadastrado com sucesso!"));
    }

    @Test
    public void testCadastrarProdutoComValorInvalido() {
        // Simulando a entrada de dados com um valor inválido
        String simulatedInput = "Produto Teste\n-10.0\nTipo A\n5\nMarca X\nDescrição Teste\n123\n123456789\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Chama o metodo para cadastrar o produto
        produtoView.cadastrarProduto("Admin");

        // Verifica se a saída contém a mensagem de erro
        assertTrue(outputStreamCaptor.toString().contains("Erro: O valor deve ser maior que zero."));
    }
}
