package edu.uepb.cct.cc;

import edu.uepb.cct.cc.controller.CompradorController;
import edu.uepb.cct.cc.controller.LojaController;
import edu.uepb.cct.cc.controller.ProdutoController;
import edu.uepb.cct.cc.model.Comprador;
import edu.uepb.cct.cc.model.Loja;
import edu.uepb.cct.cc.model.Produto;

import java.util.List;

public class Main {
    public static void printOrganizadoDeProduto(Produto produto) {
        System.out.println(
                "Nome: " + produto.getNome() + "\n" +
                        "Valor: R$ " + produto.getValor() + "\n" +
                        "Categoria: " + produto.getTipo() + "\n" +
                        "Quantidade: " + produto.getQuantidade() + "\n" +
                        "Marca: " + produto.getMarca() + "\n" +
                        "Descrição: " + produto.getDescricao() + "\n" +
                        "ID Produto: " + produto.getId() + "\n" +
                        "ID Loja: " + produto.getIdLoja() + "\n" +
                        "------------------------------------");
    }

    public static void printOrganizadoDeLoja(Loja loja) {
        System.out.println(
                "Nome: " + loja.getNome() + "\n" +
                        "Email: " + loja.getEmail() + "\n" +
                        "Senha: " + loja.getSenha() + "\n" +
                        "CPF/CNPJ: " + loja.getCpfCnpj() + "\n" +
                        "Endereço: " + loja.getEndereco() + "\n" +
                        "------------------------------------");
    }

    public static void printOrganizadoDeComprador(Comprador comprador) {
        System.out.println(
                "Nome: " + comprador.getNome() + "\n" +
                        "Email: " + comprador.getEmail() + "\n" +
                        "CPF: " + comprador.getCpf() + "\n" +
                        "Endereço: " + comprador.getEndereco() + "\n" +
                        "------------------------------------");
    }

    public static void main(String[] args) {

        System.out.println("\n_______________________________________________LOJAS_______________________________________________\n\n");
        // Criando lojas

        Loja loja1 = new Loja("Loja A", "lojaA@email.com", "senha123", "123.456.789-00", "Endereço A");
        Loja loja2 = new Loja("Loja Y", "lojaY@email.com", "senha4D56", "11.222.333/0001-44", "Endereço B");

        // Adicionando lojas
        LojaController.create(loja1);
        LojaController.create(loja2);

        // Listando todas as lojas
        List<Loja> lojas = LojaController.getTodasLojas();
        System.out.println("\n\n------------------------------------\nLista de lojas:\n" +
                "------------------------------------");
        for (Loja loja : lojas) {
            printOrganizadoDeLoja(loja);
        }

        // Buscando uma loja pelo CPF/CNPJ
        Loja lojaEncontrada = LojaController.getLojaPorCpfCnpj("123.456.789-00");
        System.out.println((lojaEncontrada != null ? "\nLoja encontrada." : "\nLoja não encontrada."));

        // Atualizando uma loja
        Loja lojaAtualizada = new Loja("Loja A Atualizada", "novaLojaA@email.com", "novaSenha123", "123.456.789-00",
                "Novo Endereço A");
        LojaController.atualizarLoja("123.456.789-00", lojaAtualizada);

        // Removendo uma loja
        LojaController.deleteLojaPorCpfCnpj("11.222.333/0001-44");
        System.out.println("------------------------------------\n");

        // Listando novamente após a remoção
        lojas = LojaController.getTodasLojas();
        System.out.println("\n------------------------------------\nLista de lojas após remoção:\n" +
                "------------------------------------");
        for (Loja loja : lojas) {
            printOrganizadoDeLoja(loja);
        }
        if (lojas.isEmpty()) {
            System.out.println("Sem lojas para exibir.");
        }

        System.out.println("\n_______________________________________________PRODUTOS_______________________________________________\n\n");

        // Criando produtos
        Produto produto1 = new Produto("Produto A", 100.0f, "Categoria A", 10, "Marca A", "Descrição do Produto A",
                "idProduto1", "123.456.789-00");
        Produto produto2 = new Produto("Produto B", 150.0f, "Categoria B", 5, "Marca B", "Descrição do Produto B",
                "idProduto2", "123.456.789-00");

        // Adicionando produtos
        ProdutoController.create(produto1);
        ProdutoController.create(produto2);

        // Listando todos os produtos
        List<Produto> produtos = ProdutoController.getTodosProdutos();
        System.out.println("\nLista de produtos:\n" + //
                "------------------------------------");
        for (Produto produto : produtos) {
            printOrganizadoDeProduto(produto);
        }

        // Buscando um produto pelo nome
        Produto produtoEncontrado = ProdutoController.getProdutoPorID("idProduto1");
        System.out.println((produtoEncontrado != null ? "\nProduto encontrado." : "\nProduto não encontrado."));

        // Atualizando um produto
        Produto produtoAtualizado = new Produto("Produto A Atualizado", 120.0f, "Categoria A", 12, "Marca A",
                "Nova descrição do Produto A", "idProduto1", "123.456.789-00");
        ProdutoController.atualizarProduto("idProduto1", produtoAtualizado);

        // Removendo um produto
        ProdutoController.deleteProdutoPorID("idProduto1");
        ProdutoController.deleteProdutoPorID("idProduto2");
        System.out.println("------------------------------------\n");

        // Listando novamente após a remoção
        produtos = ProdutoController.getTodosProdutos();
        System.out.println("\n" + //
                "------------------------------------------\nLista de produtos após remoção:");
        for (Produto produto : produtos) {
            printOrganizadoDeProduto(produto);
        }
        if (produtos.isEmpty()) {
            System.out.println("Sem produtos para exibir.");
        }
        System.out.println("\n" + //
                "------------------------------------------");

        LojaController.deleteLojaPorCpfCnpj("123.456.789-00");
        
        System.out.println("\n_______________________________________________COMPRADORES_______________________________________________\n\n");
        
        // Criando compradores
        Comprador comprador1 = new Comprador("João Silva", "joao@email.com", "senha123", "123.456.789-00",
                "Rua A, 123");
        Comprador comprador2 = new Comprador("Maria Oliveira", "maria@email.com", "senha456", "987.654.321-00",
                "Rua B, 456");
        // Adicionando compradores
        CompradorController.create(comprador1);
        CompradorController.create(comprador2);

        // Listando todos os compradores
        List<Comprador> compradores = CompradorController.getTodosCompradores();
        System.out.println("\nLista de compradores:\n" + "------------------------------------");
        for (Comprador comprador : compradores) {
            printOrganizadoDeComprador(comprador);
        }

        // Buscando um comprador pelo CPF
        Comprador compradorEncontrado = CompradorController.getCompradorPorCpf("123.456.789-00");
        System.out.println((compradorEncontrado != null ? "\nComprador encontrado." : "\nComprador não encontrado."));

        // Atualizando um comprador
        Comprador compradorAtualizado = new Comprador("João Souza", "joaosouza@email.com", "novaSenha123",
                "123.456.789-00", "Rua Nova, 789");
        System.out.println("\n------------------------------------\nComprador atualizado:");
        printOrganizadoDeComprador(CompradorController.atualizarComprador("123.456.789-00", compradorAtualizado));

        // Removendo um comprador
        System.out.println("\n\n\n------------------------------------");
        System.out.println(CompradorController.deleteCompradorPorCpf("987.654.321-00"));
        System.out.println(CompradorController.deleteCompradorPorCpf("123.456.789-00"));
        System.out.println("------------------------------------\n");

        // Listando novamente após a remoção
        compradores = CompradorController.getTodosCompradores();
        System.out.println("\nLista de compradores após remoção:\n" + "------------------------------------");
        for (Comprador comprador : compradores) {
            printOrganizadoDeComprador(comprador);
        }
        if (compradores.isEmpty()) {
            System.out.println("Sem compradores para exibir.");
        }
        System.out.println("\n------------------------------------");
    }
}
