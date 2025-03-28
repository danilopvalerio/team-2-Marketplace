package edu.uepb.cct.cc.view;

import edu.uepb.cct.cc.controller.ProdutoController;
import java.util.List;
import java.util.Scanner;

public class ProdutoView {

    public static void cadastrarProduto(String idLoja) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("\n=== Cadastro de Produto ===");

            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            while (nome.isEmpty()) {
                System.out.print("Nome não pode ser vazio. Insira novamente: ");
                nome = scanner.nextLine();
            }

            System.out.print("Valor: ");
            float valor = Float.parseFloat(scanner.nextLine());
            while (valor <= 0) {
                System.out.print("O valor deve ser maior que zero. Insira novamente: ");
                valor = Float.parseFloat(scanner.nextLine());
            }

            System.out.print("Tipo: ");
            String tipo = scanner.nextLine();
            while (tipo.isEmpty()) {
                System.out.print("Tipo não pode ser vazio. Insira novamente: ");
                tipo = scanner.nextLine();
            }

            System.out.print("Quantidade: ");
            int quantidade = Integer.parseInt(scanner.nextLine());
            while (quantidade < 0) {
                System.out.print("A quantidade não pode ser negativa. Insira novamente: ");
                quantidade = Integer.parseInt(scanner.nextLine());
            }

            System.out.print("Marca: ");
            String marca = scanner.nextLine();

            System.out.print("Descrição: ");
            String descricao = scanner.nextLine();

            System.out.print("ID do Produto: ");
            String id = scanner.nextLine();

            System.out.print("ID da Loja (CNPJ/CPF): ");
            idLoja = (idLoja.equals("Admin")) ? scanner.nextLine() : idLoja;

            ProdutoController.create(nome, valor, tipo, quantidade, marca, descricao, id, idLoja);
            System.out.println("Produto cadastrado com sucesso!");
        } catch (NumberFormatException e) {
            System.out.println("Erro: Entrada numérica inválida.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado ao cadastrar o produto.");
        }
    }

    public static void listarProdutosPorLoja(String cnpjCpfLoja) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("\n=== Produtos da Loja ===");

            cnpjCpfLoja = (cnpjCpfLoja.equals("Admin")) ? scanner.nextLine() : cnpjCpfLoja;
            List<String> produtosDaLoja = ProdutoController.getProdutosPorLoja(cnpjCpfLoja);

            if (produtosDaLoja.isEmpty()) {
                System.out.println("Nenhum produto encontrado para a loja com CNPJ/CPF: " + cnpjCpfLoja);
            } else {
                for (String produtoFormatado : produtosDaLoja) {
                    System.out.println(produtoFormatado);
                    System.out.println("------------------------------------");
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado ao listar os produtos da loja.");
        }
    }

    public static void buscarProdutoPorID(String id) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("\n=== Buscar Produto por ID ===");

            id = (id.equals("Admin")) ? scanner.nextLine() : id;

            String produto = ProdutoController.getProdutoPorID(id);
            if (produto == null) {
                System.out.println("Produto não encontrado.");
                return;
            } else {
                System.out.println(produto);
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
            return;
        } catch (Exception e) {
            System.out.println("Erro inesperado ao buscar o produto.");
            return;
        }
    }

    public static void listarTodosProdutos() {
        try {
            System.out.println("\n=== Listar Todos os Produtos ===");
            List<String> produtos = ProdutoController.getTodosProdutos();

            if (produtos.isEmpty()) {
                System.out.println("Nenhum produto cadastrado.");
            } else {
                for (String produto : produtos) {
                    System.out.println(produto);
                    System.out.println("------------------------------------");
                }
            }
        } catch (Exception e) {
            System.out.println("Erro inesperado ao listar os produtos.");
        }
    }

    public static void deletarProduto() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("\n=== Deletar Produto ===");
            System.out.print("ID do Produto: ");
            String id = scanner.nextLine();

            boolean deletado = ProdutoController.deleteProdutoPorID(id);
            if (deletado) {
                System.out.println("Produto removido com sucesso!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado ao deletar o produto.");
        }
    }

    public static void atualizarProduto() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("\n=== Atualizar Produto ===");
            System.out.print("ID do Produto: ");
            String id = scanner.nextLine();

            String produtoExistente = ProdutoController.getProdutoPorID(id);
            if (produtoExistente == null) {
                System.out.println("Produto não encontrado.");
                return;
            }

            System.out.println("Produto Atual:\n" + produtoExistente);
            System.out.println("Deixe em branco para manter o mesmo valor.");

            System.out.print("Novo Nome (atual: " + produtoExistente.split("\n")[1].split(": ")[1] + "): ");
            String nome = scanner.nextLine();
            nome = nome.isEmpty() ? produtoExistente.split("\n")[1].split(": ")[1] : nome;

            System.out.print("Novo Valor (atual: " + produtoExistente.split("\n")[5].split(": ")[1] + "): ");
            String valorInput = scanner.nextLine();
            float valor = valorInput.isEmpty() ? Float.parseFloat(produtoExistente.split("\n")[5].split(": ")[1])
                    : Float.parseFloat(valorInput);
            if (valor <= 0)
                throw new IllegalArgumentException("O valor deve ser maior que zero.");

            System.out.print("Novo Tipo (atual: " + produtoExistente.split("\n")[2].split(": ")[1] + "): ");
            String tipo = scanner.nextLine();
            tipo = tipo.isEmpty() ? produtoExistente.split("\n")[2].split(": ")[1] : tipo;

            System.out.print("Nova Quantidade (atual: " + produtoExistente.split("\n")[6].split(": ")[1] + "): ");
            String quantidadeInput = scanner.nextLine();
            int quantidade = quantidadeInput.isEmpty()
                    ? Integer.parseInt(produtoExistente.split("\n")[6].split(": ")[1])
                    : Integer.parseInt(quantidadeInput);
            if (quantidade < 0)
                throw new IllegalArgumentException("A quantidade não pode ser negativa.");

            System.out.print("Nova Marca (atual: " + produtoExistente.split("\n")[3].split(": ")[1] + "): ");
            String marca = scanner.nextLine();
            marca = marca.isEmpty() ? produtoExistente.split("\n")[3].split(": ")[1] : marca;

            System.out.print("Nova Descrição (atual: " + produtoExistente.split("\n")[4].split(": ")[1] + "): ");
            String descricao = scanner.nextLine();
            descricao = descricao.isEmpty() ? produtoExistente.split("\n")[4].split(": ")[1] : descricao;
            


            System.out.print("ID da Loja (atual: " + produtoExistente.split("\n")[7].split(": ")[1] + "): ");
            String idLoja = scanner.nextLine();
            idLoja = idLoja.isEmpty() ? produtoExistente.split("\n")[7].split(": ")[1] : idLoja;
            
            boolean atualizado = ProdutoController.atualizarProduto(nome, valor, tipo, quantidade, marca, descricao, id,
                    idLoja);

            if (atualizado) {
                System.out.println("Produto atualizado com sucesso!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: Entrada numérica inválida.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado ao atualizar o produto: " + e);
        }
    }
}
