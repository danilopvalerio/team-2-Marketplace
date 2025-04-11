package edu.uepb.cct.cc;

import edu.uepb.cct.cc.model.Comprador;
import edu.uepb.cct.cc.model.Produto;
import edu.uepb.cct.cc.model.Venda;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class CompradorVendaTest {

    @Test
    void testAdicionarProdutoAoCarrinho() {
        Comprador comprador = new Comprador();
        Produto produto = new Produto("Produto Teste", 10.0f, "Eletrônicos", 10, "Marca Teste", "Descrição do produto", "P001", "123.456.789-00");

        comprador.adicionarAoCarrinho(produto, 2);

        List<Comprador.ItemCarrinho> carrinho = comprador.listarCarrinho();
        assertEquals(1, carrinho.size());
        assertEquals("P001", carrinho.get(0).getProduto().getId());
        assertEquals(2, carrinho.get(0).getQuantidade());
    }

    @Test
    void testAtualizarQuantidadeProdutoNoCarrinho() {
        Comprador comprador = new Comprador();
        Produto produto = new Produto("Produto Teste", 100.0f, "Eletrônicos", 10, "Marca Teste", "Descrição do produto", "P002", "123.456.789-00");

        comprador.adicionarAoCarrinho(produto, 1);
        comprador.atualizarQuantidadeProduto(produto, 5);

        Comprador.ItemCarrinho item = comprador.listarCarrinho().get(0);
        assertEquals(5, item.getQuantidade());
    }

    @Test
    void testRemoverProdutoDoCarrinho() {
        Comprador comprador = new Comprador();
        Produto produto = new Produto("Produto Teste", 100.0f, "Eletrônicos", 10, "Marca Teste", "Descrição do produto", "P003", "123.456.789-00");

        comprador.adicionarAoCarrinho(produto, 1);
        assertEquals(1, comprador.listarCarrinho().size());

        comprador.removerProdutoDoCarrinho(produto);
        assertEquals(0, comprador.listarCarrinho().size());
    }

    @Test
    void testSimulacaoDeVendaRetornaVendaCorreta() {
        Comprador comprador = new Comprador("Maria", "maria@email.com", "123456", "111.222.333-44", "Rua A");
        Produto produto = new Produto("Produto Teste", 100.0f, "Eletrônicos", 10, "Marca Teste", "Descrição do produto", "P004", "123.456.789-00");

        comprador.adicionarAoCarrinho(produto, 3);

        List<Comprador.ItemCarrinho> carrinho = comprador.listarCarrinho();

        List<String> ids = new ArrayList<>();
        List<Double> valores = new ArrayList<>();
        List<Integer> quantidades = new ArrayList<>();

        for (Comprador.ItemCarrinho item : carrinho) {
            ids.add(item.getProduto().getId());
            valores.add((double) item.getProduto().getValor()); // Cast necessário
            quantidades.add(item.getQuantidade());
        }

        Venda venda = new Venda("V001", comprador.getCpf(), LocalDate.now(), ids, valores, quantidades);

        assertEquals("V001", venda.getIdVenda());
        assertEquals("111.222.333-44", venda.getIdComprador());
        assertEquals(1, venda.getIdsProdutosVendidos().size());
        assertEquals(3, venda.getQuantidades().get(0));
    }

    @Test
    void testVendaAtualizaCarrinhoEEstoqueCorretamente() {
        Comprador comprador = new Comprador("João", "joao@email.com", "senha123", "999.888.777-66", "Rua X");
        Produto produto = new Produto("Teclado Gamer", 150.0f, "Periféricos", 5, "MarcaX", "RGB", "T123", "999.888.777-66");
        comprador.adicionarAoCarrinho(produto, 2);

        List<Comprador.ItemCarrinho> carrinho = comprador.listarCarrinho();

        List<String> ids = new ArrayList<>();
        List<Double> valores = new ArrayList<>();
        List<Integer> quantidades = new ArrayList<>();

        for (Comprador.ItemCarrinho item : carrinho) {
            ids.add(item.getProduto().getId());
            valores.add((double) item.getProduto().getValor());
            quantidades.add(item.getQuantidade());
        }

        Venda venda = new Venda("V010", comprador.getCpf(), LocalDate.now(), ids, valores, quantidades);

        for (Comprador.ItemCarrinho item : new ArrayList<>(comprador.listarCarrinho())) {
            comprador.removerProdutoDoCarrinho(item.getProduto());
        }

        assertEquals(0, comprador.listarCarrinho().size(), "Carrinho deveria estar vazio após a venda.");
        assertEquals("T123", venda.getIdsProdutosVendidos().get(0));
        assertEquals(2, venda.getQuantidades().get(0));
    }



}
