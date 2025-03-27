package edu.uepb.cct.cc.view;

import edu.uepb.cct.cc.controller.ProdutoController;
import edu.uepb.cct.cc.model.Produto;
import java.util.Scanner;

public class ProdutoView {
    private static final Scanner scanner = new Scanner(System.in);

    public void cadastrarProduto() {
        System.out.println("");
        System.out.println("=== Cadastro de Produto ===");

        try {
            System.out.print("Nome: ");
            String nome = scanner.nextLine();

            System.out.print("Valor: ");
            float valor = Float.parseFloat(scanner.nextLine());
            if (valor <= 0)
                throw new IllegalArgumentException("O valor deve ser maior que zero.");

            System.out.print("Tipo: ");
            String tipo = scanner.nextLine();

            System.out.print("Quantidade: ");
            int quantidade = Integer.parseInt(scanner.nextLine());
            if (quantidade < 0)
                throw new IllegalArgumentException("A quantidade não pode ser negativa.");

            System.out.print("Marca: ");
            String marca = scanner.nextLine();

            System.out.print("Descrição: ");
            String descricao = scanner.nextLine();

            System.out.print("ID do Produto: ");
            String id = scanner.nextLine();

            System.out.print("ID da Loja (CNPJ/CPF): ");
            String idLoja = scanner.nextLine();

            Produto novoProduto = new Produto(nome, valor, tipo, quantidade, marca, descricao, id, idLoja);
            ProdutoController.create(novoProduto);

            System.out.println("Produto cadastrado com sucesso!");
        } catch (NumberFormatException e) {
            System.out.println("Erro: Entrada numérica inválida.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado ao cadastrar o produto.");
        }
    }
}
