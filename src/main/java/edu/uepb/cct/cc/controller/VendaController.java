package edu.uepb.cct.cc.controller;

import edu.uepb.cct.cc.model.Produto;
import edu.uepb.cct.cc.model.Venda;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class VendaController {

    public void removerProdutosDoEstoque(Venda venda) {
        ProdutoController produtoController = new ProdutoController();
        List<Produto> produtos = produtoController.carregarProdutos();

        for (int i = 0; i < venda.getIdsProdutosVendidos().size(); i++) {
            String idProduto = venda.getIdsProdutosVendidos().get(i);
            int quantidadeVendida = venda.getQuantidades().get(i);

            // Buscar o produto correspondente
            Produto produtoEncontrado = null;
            for (Produto p : produtos) {
                if (p.getId().equals(idProduto)) {
                    produtoEncontrado = p;
                    break;
                }
            }

            // Validar existência e estoque
            if (produtoEncontrado == null) {
                throw new IllegalArgumentException("Produto com ID " + idProduto + " não encontrado.");
            }
            if (produtoEncontrado.getQuantidade() < quantidadeVendida) {
                throw new IllegalStateException("Estoque insuficiente para o produto " + idProduto + ".");
            }

            // Atualizar quantidade
            produtoEncontrado.setQuantidade(produtoEncontrado.getQuantidade() - quantidadeVendida);
        }

        // Salvar alterações no produtos.json
        produtoController.salvarProdutos(produtos);
    }
}
