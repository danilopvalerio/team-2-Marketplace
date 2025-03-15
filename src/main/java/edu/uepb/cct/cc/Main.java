package edu.uepb.cct.cc;

import edu.uepb.cct.cc.controller.CompradorController;
import edu.uepb.cct.cc.model.Comprador;

import edu.uepb.cct.cc.controller.LojaController;
import edu.uepb.cct.cc.model.Loja;

import edu.uepb.cct.cc.controller.ProdutoController;

public class Main {
    public static void main(String[] args) {

        // Teste de execução de lojas.
        // // Criação de uma nova loja com 5 parâmetros, incluindo a senha
        // Loja loja1 = new Loja("Loja Exemplo4", "loja@exemplo2.com", "senha123",
        // "11.345.678/0001-94",
        // "Rua Exemplo, 123");

        Loja loja2 = new Loja("Loja Exemplo4", "loja@exemplo2.com", "senha123",
                "12.345.678/9000-01",
                "Rua Exemplo, 123");

        // // Adiciona a loja ao arquivo JSON
        // LojaController.create(loja1);
        // LojaController.create(loja2);

        // System.out.println("Todas as lojas:");
        // for (Loja loja : LojaController.getTodasLojas()) {
        // System.out.println(loja.getNome() + " - " + loja.getCpfCnpj());
        // }

        // System.out.println("\nBuscando loja pelo CNPJ: 12.345.678/9000-01");
        // Loja lojaEncontrada = LojaController.getLojaPorCpfCnpj("12.345.678/9000-01");
        // if (lojaEncontrada != null) {
        // System.out.println("Loja encontrada: " + lojaEncontrada.getNome());
        // } else {
        // System.out.println("Loja não encontrada.");
        // }

        // LojaController.deleteLojaPorCpfCnpj("12.345.678/9000-01");

        // CADASTRANDO PRODUTO
        /*
         * ProdutoController produtoController = new ProdutoController();
         * 
         * // Teste de cadastro de produto
         * String resultado = produtoController.cadastrarProduto("Short", 49.90f,
         * "Vestuário", 100, "Nike", "Camiseta esportiva de algodão");
         * System.out.println(resultado); // Espera-se "Produto cadastrado com sucesso.
         */

        // CADASTRANDO COMPRADOR

        // Comprador comprador = new Comprador(
        // "Maria Silva",
        // "maria.silva@email.com",
        // "senha456",
        // "127.456.797-40",
        // "Rua das Flores, 128"
        // );
        // // Adicionar o comprador ao sistema
        // CompradorController.create(comprador);

    }

}
