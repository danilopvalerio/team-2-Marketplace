package edu.uepb.cct.cc;

import edu.uepb.cct.cc.controller.CompradorController;
import edu.uepb.cct.cc.controller.ProdutoController;
import edu.uepb.cct.cc.model.Comprador;
import edu.uepb.cct.cc.model.Produto;
import edu.uepb.cct.cc.services.FachadaMenus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class MenuCarrinhoTest {

    private final String CPF = "123.456.789-00";
    private final String ID_PRODUTO = "P001";
    private PrintStream originalOut;


    @BeforeEach
    void prepararAmbiente() {
        try (Writer w1 = new FileWriter("src/main/resources/data/compradores.json");
             Writer w2 = new FileWriter("src/main/resources/data/produtos.json")) {
            w1.write("[]");
            w2.write("[]");
        } catch (IOException e) {
            fail("Erro ao limpar arquivos de teste");
        }

        Comprador comprador = new Comprador("João", "joao@email.com", "senha123", CPF, "Rua A");
        CompradorController.create(comprador);

        Produto produto = new Produto("Produto Teste", 100.0f, "Eletrônicos", 10, "Marca Teste", "Descrição do produto", ID_PRODUTO, CPF);
        ProdutoController.create(produto.getNome(), produto.getValor(), produto.getTipo(),
                produto.getQuantidade(), produto.getMarca(), produto.getDescricao(),
                produto.getId(), produto.getIdLoja());
    }

    @Test
    void deveRejeitarSeCompradorNaoExistir() {
        Scanner scanner = new Scanner("0\n");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(saida));

        try {
            FachadaMenus.exibirMenuCarrinho(scanner, "000.000.000-00");
        } finally {
            System.setOut(originalOut);
        }

        assertTrue(saida.toString().contains("Comprador não encontrado!"));
    }

    @Test
    void deveInformarErroSeProdutoNaoExistir() {
        Scanner scanner = new Scanner("2\nINVALIDO\n0\n");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(saida));

        try {
            FachadaMenus.exibirMenuCarrinho(scanner, CPF);
        } finally {
            System.setOut(originalOut);
        }

        assertTrue(saida.toString().contains("Produto não encontrado."));
    }

    @Test
    void deveRejeitarOpcoesInvalidas() {
        Scanner scanner = new Scanner("9\n0\n");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(saida));

        try {
            FachadaMenus.exibirMenuCarrinho(scanner, CPF);
        } finally {
            System.setOut(originalOut);
        }

        assertTrue(saida.toString().contains("⚠️ Opção inválida. Tente novamente."));
    }

    @Test
    void deveAdicionarProdutoAoCarrinhoComSucesso() {
        Scanner scanner = new Scanner("2\n" + ID_PRODUTO + "\n2\n0\n");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(saida));

        try {
            FachadaMenus.exibirMenuCarrinho(scanner, CPF);
        } finally {
            System.setOut(originalOut);
        }

        assertTrue(saida.toString().contains("Produto adicionado ao carrinho."));
    }

    @Test
    void deveRemoverProdutoDoCarrinhoComSucesso() {
        Comprador comprador = CompradorController.getCompradorPorCpf(CPF);
        Produto produto = ProdutoController.buscarProdutoPorID(ID_PRODUTO);
        comprador.adicionarAoCarrinho(produto, 1);
        CompradorController.atualizarCarrinhoDoComprador(CPF, comprador.listarCarrinho());

        Scanner scanner = new Scanner("3\n" + ID_PRODUTO + "\n0\n");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(saida));

        try {
            FachadaMenus.exibirMenuCarrinho(scanner, CPF);
        } finally {
            System.setOut(originalOut);
        }

        assertTrue(saida.toString().contains("Produto removido do carrinho."));
    }

    @Test
    void deveImpedirFinalizacaoComCarrinhoVazio() {
        Scanner scanner = new Scanner("4\n0\n");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(saida));

        try {
            FachadaMenus.exibirMenuCarrinho(scanner, CPF);
        } finally {
            System.setOut(originalOut);
        }

        assertTrue(saida.toString().contains("Carrinho vazio! Não é possível finalizar a compra."));
    }

    @Test
    void deveListarItensDoCarrinho() {
        Comprador comprador = CompradorController.getCompradorPorCpf(CPF);
        Produto produto = ProdutoController.buscarProdutoPorID(ID_PRODUTO);
        comprador.adicionarAoCarrinho(produto, 2);
        CompradorController.atualizarCarrinhoDoComprador(CPF, comprador.listarCarrinho());

        Scanner scanner = new Scanner("1\n0\n");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(saida));

        try {
            FachadaMenus.exibirMenuCarrinho(scanner, CPF);
        } finally {
            System.setOut(originalOut);
        }

        assertTrue(saida.toString().contains("Produto Teste"));
    }

}
