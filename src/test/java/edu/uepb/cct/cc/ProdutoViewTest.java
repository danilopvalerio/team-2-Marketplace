package edu.uepb.cct.cc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

import edu.uepb.cct.cc.controller.ProdutoController;
import edu.uepb.cct.cc.controller.VendaController;
import edu.uepb.cct.cc.model.Comprador;
import edu.uepb.cct.cc.model.Produto;
import edu.uepb.cct.cc.model.Venda;

class ProdutoViewTest {

    @Test
    void cadastrarProduto() {
        // testando
        Produto produto = new Produto("Notebook", 3000.0f, "Eletrônico", 10, "Dell", "Alta performance", "P001",
                "12.345.678/0001-99");
    }

    @Test
    void listarProdutosPorLoja() {
    }

    @Test
    void buscarProdutoPorID() {
    }

    @Test
    void listarTodosProdutos() {
    }

    @Test
    void deletarProduto() {
    }

    @Test
    void atualizarProduto() {
    }

    @Test
    void testAdicionarAvaliacaoEObterMedia() {}
}