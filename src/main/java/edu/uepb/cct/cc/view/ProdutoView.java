package edu.uepb.cct.cc.view;

import edu.uepb.cct.cc.controller.LojaController;
import edu.uepb.cct.cc.controller.ProdutoController;
import java.util.List;
import java.util.Scanner;
import edu.uepb.cct.cc.model.Produto;

public class ProdutoView {

    private static Scanner scanner = new Scanner(System.in);

    // Método para cadastrar um produto
    public static void cadastrarProduto(String idLoja) {
        try {
            System.out.println("\n=== Cadastro de Produto ===");

            String nome = getStringInput("Nome: ");
            float valor = getFloatInput("Valor: ");
            String tipo = getStringInput("Tipo: ");
            int quantidade = getIntInput("Quantidade: ");
            String marca = getStringInput("Marca: ");
            String descricao = getStringInput("Descrição: ");
            String idProduto = getStringInput("ID do Produto: ");

            idLoja = idLoja.equals("Admin") ? getStringInput("ID da Loja (CNPJ/CPF): ") : idLoja;

            if (LojaController.getLojaPorCpfCnpj(idLoja) == null) {
                System.out.println("Loja não cadastrada. Verifique o CPF/CNPJ inserido e tente novamente.");
                return;
            }

            if (ProdutoController.getProdutoPorID(idProduto) != null) {
                System.out.println("Já existe um produto com esse ID.");
            } else {
                ProdutoController.create(nome, valor, tipo, quantidade, marca, descricao, idProduto, idLoja);
                System.out.println("Produto cadastrado com sucesso!");
            }
        } catch (Exception e) {
            System.out.println("Erro inesperado ao cadastrar o produto: " + e.getMessage());
        }
    }

    // Método para listar produtos de uma loja
    public static void listarProdutosPorLoja(String cnpjCpfLoja) {
        try {
            System.out.println("\n=== Produtos da Loja ===");

            cnpjCpfLoja = cnpjCpfLoja.equals("Admin") ? getStringInput("Digite o CNPJ/CPF da loja: ") : cnpjCpfLoja;
            List<Produto> produtosDaLoja = ProdutoController.getProdutosPorLoja(cnpjCpfLoja);

            if (produtosDaLoja.isEmpty()) {
                System.out.println("Nenhum produto encontrado para a loja com CNPJ/CPF: " + cnpjCpfLoja);
            } else {
                for (Produto produto : produtosDaLoja) {
                    System.out.println(ProdutoController.formatarProduto(produto));
                    System.out.println("------------------------------------");
                }
            }
        } catch (Exception e) {
            System.out.println("Erro inesperado ao listar os produtos da loja: " + e.getMessage());
        }
    }

    // Método para buscar um produto pelo ID
    public static void buscarProdutoPorID(String id) {
        try {
            System.out.println("\n=== Buscar Produto por ID ===");

            String idProduto = getStringInput("Digite o ID do produto a ser buscado: ");

            if (!id.equals("Admin")) {
                List<Produto> produtos = ProdutoController.getProdutosPorLoja(id);
                boolean encontrado = false;
                for (Produto produto : produtos) {
                    if (produto.getId().equalsIgnoreCase(idProduto)) {
                        System.out.println(ProdutoController.formatarProduto(produto));
                        encontrado = true;
                        break;
                    }
                }
                if (!encontrado) {
                    System.out.println("Produto não encontrado para esta loja.");
                }
            } else {
                Produto produto = ProdutoController.buscarProdutoPorID(idProduto);
                if (produto == null) {
                    System.out.println("Produto não encontrado.");
                } else {
                    System.out.println(ProdutoController.formatarProduto(produto));
                }
            }
        } catch (Exception e) {
            System.out.println("Erro inesperado ao buscar o produto: " + e.getMessage());
        }
    }

    // Método para listar todos os produtos
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
            System.out.println("Erro inesperado ao listar os produtos: " + e.getMessage());
        }
    }

    // Método para deletar um produto
    public static void deletarProduto(String idLoja) {
        try {
            System.out.println("\n=== Deletar Produto ===");
            String idProduto = getStringInput("ID do Produto: ");

            Produto produtoExistente = ProdutoController.buscarProdutoPorID(idProduto);
            if (produtoExistente == null) {
                System.out.println("Produto não encontrado.");
                return;
            }

            if (!idLoja.equals("Admin") && !produtoExistente.getIdLoja().equals(idLoja)) {
                System.out.println("Este produto não pertence à loja com ID " + idLoja + ". Não é possível Deletar.");
                return;
            }

            boolean deletado = ProdutoController.deleteProdutoPorID(idProduto);
            if (deletado) {
                System.out.println("Produto removido com sucesso!");
            }
        } catch (Exception e) {
            System.out.println("Erro inesperado ao deletar o produto: " + e.getMessage());
        }
    }

    // Método para atualizar um produto
    public static void atualizarProduto(String idLoja) {
        try {
            System.out.println("\n=== Atualizar Produto ===");
            String idProduto = getStringInput("ID do Produto: ");

            Produto produtoExistente = ProdutoController.buscarProdutoPorID(idProduto);
            if (produtoExistente == null) {
                System.out.println("Produto não encontrado.");
                return;
            }

            if (!idLoja.equals("Admin") && !produtoExistente.getIdLoja().equals(idLoja)) {
                System.out.println("Este produto não pertence à loja com ID " + idLoja + ". Não é possível atualizar.");
                return;
            }

            System.out.println("Produto Atual:\n" + ProdutoController.formatarProduto(produtoExistente));
            System.out.println("Deixe em branco para manter o mesmo valor.");

            String nome = getStringInputWithDefault("Novo Nome", produtoExistente.getNome());
            float valor = getFloatInputWithDefault("Novo Valor", produtoExistente.getValor());
            String tipo = getStringInputWithDefault("Novo Tipo", produtoExistente.getTipo());
            int quantidade = getIntInputWithDefault("Nova Quantidade", produtoExistente.getQuantidade());
            String marca = getStringInputWithDefault("Nova Marca", produtoExistente.getMarca());
            String descricao = getStringInputWithDefault("Nova Descrição", produtoExistente.getDescricao());

            boolean atualizado = ProdutoController.atualizarProduto(nome, valor, tipo, quantidade, marca, descricao, idProduto, produtoExistente.getIdLoja());
            if (atualizado) {
                System.out.println("Produto atualizado com sucesso!");
            }
        } catch (Exception e) {
            System.out.println("Erro inesperado ao atualizar o produto: " + e.getMessage());
        }
    }

    // Métodos auxiliares para ler entradas
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static String getStringInputWithDefault(String prompt, String defaultValue) {
        System.out.print(prompt + " (atual: " + defaultValue + "): ");
        String input = scanner.nextLine();
        return input.isEmpty() ? defaultValue : input;
    }

    private static float getFloatInput(String prompt) {
        System.out.print(prompt);
        return Float.parseFloat(scanner.nextLine());
    }

    private static float getFloatInputWithDefault(String prompt, float defaultValue) {
        System.out.print(prompt + " (atual: " + defaultValue + "): ");
        String input = scanner.nextLine();
        return input.isEmpty() ? defaultValue : Float.parseFloat(input);
    }

    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        return Integer.parseInt(scanner.nextLine());
    }

    private static int getIntInputWithDefault(String prompt, int defaultValue) {
        System.out.print(prompt + " (atual: " + defaultValue + "): ");
        String input = scanner.nextLine();
        return input.isEmpty() ? defaultValue : Integer.parseInt(input);
    }
}
