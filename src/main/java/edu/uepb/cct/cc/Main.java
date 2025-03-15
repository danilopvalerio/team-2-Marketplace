/*package edu.uepb.cct.cc;

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

        // Loja loja2 = new Loja("Loja Exemplo4", "loja@exemplo2.com", "senha123",
        // "12.345.678/9000-01",
        // "Rua Exemplo, 123");

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

        String cpfCnpj = "12.345.678/9000-01";

        // Criando uma nova loja com dados atualizados
        Loja lojaAtualizada = new Loja();
        lojaAtualizada.setCpfCnpj(cpfCnpj);
        lojaAtualizada.setNome("Loja Atualizada");
        lojaAtualizada.setEmail("loja@atualizada.com");
        lojaAtualizada.setEndereco("Rua Atualizada, 123");
        lojaAtualizada.setSenha("novaSenha");

        try {
            // Atualizando a loja
            Loja loja = LojaController.atualizarLoja(cpfCnpj, lojaAtualizada);
            System.out.println("Loja atualizada: " + loja);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }

        // CADASTRANDO PRODUTO
        /*
         * ProdutoController produtoController = new ProdutoController();
         * 
         * // Teste de cadastro de produto
         * String resultado = produtoController.cadastrarProduto("Short", 49.90f,
         * "Vestuário", 100, "Nike", "Camiseta esportiva de algodão");
         * System.out.println(resultado); // Espera-se "Produto cadastrado com sucesso.
         */

    /*}

}*/



//MAIN PARA CADASTRO E EXIBIÇÃO DE COMPRADOR (TESTE)
package edu.uepb.cct.cc;

import edu.uepb.cct.cc.controller.CompradorController;
import edu.uepb.cct.cc.model.Comprador;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Criando alguns compradores para teste
        Comprador comprador1 = new Comprador("João Silva", "joao@email.com", "senha123", "123.456.789-00", "Rua A, 123");
        Comprador comprador2 = new Comprador("Maria Oliveira", "maria@email.com", "senha456", "987.654.321-00", "Rua B, 456");

        // Adicionando compradores ao sistema
        System.out.println("Adicionando compradores...");
        CompradorController.create(comprador1);
        CompradorController.create(comprador2);

        // Listando todos os compradores
        System.out.println("Lista de compradores cadastrados:");
        List<Comprador> compradores = CompradorController.getTodosCompradores();
        if (compradores.isEmpty()) {
            System.out.println("Nenhum comprador cadastrado.");
        } else {
            for (Comprador c : compradores) {
                System.out.println(c);
            }
        }

        // Buscando um comprador pelo CPF
        System.out.println("Buscando comprador pelo CPF:");
        Comprador compradorEncontrado = CompradorController.getCompradorPorCpf("123.456.789-00");
        if (compradorEncontrado != null) {
            System.out.println("Comprador encontrado: " + compradorEncontrado);
        } else {
            System.out.println("Comprador não encontrado.");
        }
    }
}








