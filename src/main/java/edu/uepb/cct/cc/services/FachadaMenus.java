package edu.uepb.cct.cc.services;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

import edu.uepb.cct.cc.view.*;
import edu.uepb.cct.cc.model.Comprador;
import edu.uepb.cct.cc.model.Produto;
import edu.uepb.cct.cc.controller.CompradorController;
import edu.uepb.cct.cc.controller.ProdutoController;

public class FachadaMenus {

    // Menus de COMPRADOR para ADMIN
    public static void menuComprador(Scanner scanner) {
        CompradorView compradorView = new CompradorView();

        while (true) {
            System.out.println("\n================ MENU COMPRADOR ================");
            System.out.println("1️⃣  Cadastrar Comprador");
            System.out.println("2️⃣  Listar Compradores");
            System.out.println("3️⃣  Atualizar Comprador");
            System.out.println("4️⃣  Deletar Comprador");
            System.out.println("0️⃣  Voltar");
            System.out.print("🔹 Escolha uma opção: ");

            String escolha = scanner.next();
            scanner.nextLine();

            switch (escolha) {
                case "1" -> compradorView.cadastrarComprador();
                case "2" -> compradorView.listarCompradores();
                case "3" -> compradorView.atualizarComprador();
                case "4" -> compradorView.deletarComprador();
                case "0" -> {
                    return;
                }
                default -> System.out.println("⚠️ Opção inválida. Tente novamente.");
            }
        }
    }

    // Menus de LOJA com diferentes permissões para ADMIN, COMPRADOR
    public static void menuLojaCompleto(Scanner scanner) {
        LojaView lojaView = new LojaView();

        while (true) {
            System.out.println("\n================ MENU LOJA ================");
            System.out.println("1️⃣  Cadastrar Loja");
            System.out.println("2️⃣  Deletar Loja");
            System.out.println("3️⃣  Listar Lojas");
            System.out.println("4️⃣  Buscar Loja");
            System.out.println("5️⃣  Atualizar Loja");
            System.out.println("0️⃣  Voltar");
            System.out.print("🔹 Escolha uma opção: ");

            String escolha = scanner.next();
            scanner.nextLine();

            switch (escolha) {
                case "1" -> lojaView.cadastrarLoja();
                case "2" -> lojaView.deletarLoja();
                case "3" -> lojaView.listarLojas();
                case "4" -> lojaView.buscarLoja();
                case "5" -> lojaView.atualizarLoja();
                case "0" -> {
                    return;
                }
                default -> System.out.println("⚠️ Opção inválida. Tente novamente.");
            }
        }
    }

    public static void menuLojaParaCompradores(Scanner scanner) {
        LojaView lojaView = new LojaView();

        while (true) {
            System.out.println("\n================ MENU LOJA ================");
            System.out.println("1️⃣ Listar Lojas");
            System.out.println("2️⃣ Buscar Loja");
            System.out.println("0️⃣ Voltar");
            System.out.print("🔹 Escolha uma opção: ");

            String escolha = scanner.next();
            scanner.nextLine();

            switch (escolha) {
                case "1" -> lojaView.listarLojas();
                case "2" -> lojaView.buscarLoja();
                case "0" -> {
                    return;
                }
                default -> System.out.println("⚠️ Opção inválida. Tente novamente.");
            }
        }
    }

    // Menus de PRODUTOS com diferentes permissões para ADMIN, LOJISTA E COMPRADOR
    public static void menuProdutoParaAdmin(Scanner scanner) {

        while (true) {
            System.out.println("\n================ MENU PRODUTO ================");
            System.out.println("1️⃣ Cadastrar Produto");
            System.out.println("2️⃣ Listar produtos");
            System.out.println("3️⃣ Buscar produtos por id");
            System.out.println("4️⃣ Buscar produtos por loja");
            System.out.println("5️⃣ Atualizar Produto");
            System.out.println("6️⃣ Deletar produto");
            System.out.println("0️⃣ Voltar");
            System.out.print("🔹 Escolha uma opção: ");

            String escolha = scanner.next();
            scanner.nextLine();

            // Tratar na view e no controller para caso o id seja "Admin" liste todos os
            // produtos de todas as lojas
            // Caso o id não seja "Admin", ou seja, caso o usuário seja

            switch (escolha) {
                case "1" -> ProdutoView.cadastrarProduto("Admin");
                case "2" -> ProdutoView.listarTodosProdutos();
                case "3" -> ProdutoView.buscarProdutoPorID("Admin");
                case "4" -> ProdutoView.listarProdutosPorLoja("Admin");
                case "5" -> ProdutoView.atualizarProduto("Admin");
                case "6" -> ProdutoView.deletarProduto("Admin");
                case "0" -> {
                    return;
                }
                default -> System.out.println("⚠️ Opção inválida. Tente novamente.");
            }
        }
    }

    public static void menuProdutoParaLojas(Scanner scanner, String id) {
        ProdutoView produtoView = new ProdutoView();

        while (true) {
            System.out.println("\n================ MENU PRODUTO ================");
            System.out.println("1️⃣ Cadastrar Produto");
            System.out.println("2️⃣ Listar produtos");
            System.out.println("3️⃣ Buscar produtos");
            System.out.println("4️⃣ Atualizar Produto");
            System.out.println("5️⃣ Deletar produto");
            System.out.println("0️⃣ Voltar");
            System.out.print("🔹 Escolha uma opção: ");

            String escolha = scanner.next();
            scanner.nextLine();

            switch (escolha) {
                case "1" -> ProdutoView.cadastrarProduto(id);
                case "2" -> ProdutoView.listarProdutosPorLoja(id);
                case "3" -> ProdutoView.buscarProdutoPorID(id);
                case "4" -> ProdutoView.atualizarProduto(id);
                case "5" -> ProdutoView.deletarProduto(id);

                case "0" -> {
                    return;
                }
                default -> System.out.println("⚠️ Opção inválida. Tente novamente.");
            }
        }
    }

    public static void menuProdutoParaComprador(Scanner scanner, String id) {
        while (true) {
            System.out.println("\n================ MENU PRODUTO ================");
            System.out.println("1️⃣ Listar produtos por loja");
            System.out.println("2️⃣ Listar todos os produtos");
            System.out.println("3️⃣ Buscar produtos");
            System.out.println("4️⃣ Acessar menu carrinho"); // Nova opção
            System.out.println("0️⃣ Voltar");
            System.out.print("🔹 Escolha uma opção: ");

            String escolha = scanner.next();
            scanner.nextLine();

            switch (escolha) {
                case "1" -> {
                    System.out.println("Digite o ID da loja (CNPJ/CPF) para listar os produtos: ");
                    String idLoja = scanner.nextLine();
                    ProdutoView.listarProdutosPorLoja(idLoja);
                }
                case "2" -> ProdutoView.listarTodosProdutos();
                case "3" -> {
                    System.out.println("Digite o ID do produto: ");
                    String idProd = scanner.nextLine();
                    ProdutoView.buscarProdutoPorID(idProd);
                }
                case "4" -> { // Nova funcionalidade para abrir o menu carrinho
                    exibirMenuCarrinho(scanner, id); // Chamada do método que gerencia o menu do carrinho
                }
                case "0" -> {
                    return;
                }
                default -> System.out.println("⚠️ Opção inválida. Tente novamente.");
            }
        }
    }

    public static void exibirMenuCarrinho(Scanner scanner, String cpfComprador) {
        Comprador comprador = CompradorController.getCompradorPorCpf(cpfComprador);
        if (comprador == null) {
            System.out.println("Comprador não encontrado!");
            return;
        }

        while (true) {
            System.out.println("\n================ MENU CARRINHO ================");
            System.out.println("1️⃣ Listar itens do carrinho");
            System.out.println("2️⃣ Adicionar produto ao carrinho");
            System.out.println("3️⃣ Remover produto do carrinho");
            System.out.println("4️⃣ Finalizar compra");
            System.out.println("0️⃣ Voltar");
            System.out.print("🔹 Escolha uma opção: ");

            String escolha = scanner.next();
            scanner.nextLine();

            switch (escolha) {
                case "1" -> {
                    comprador.visualizarCarrinho();
                }
                case "2" -> {
                    System.out.print("Digite o ID do produto que deseja adicionar: ");
                    String idProduto = scanner.nextLine();
                    Produto produto = ProdutoController.buscarProdutoPorID(idProduto);

                    if (produto != null) {
                        System.out.print("Digite a quantidade: ");
                        int quantidade = scanner.nextInt();
                        scanner.nextLine(); // Limpar buffer

                        comprador.adicionarAoCarrinho(produto, quantidade);
                        // Atualiza o carrinho no arquivo
                        CompradorController.atualizarCarrinhoDoComprador(
                                comprador.getCpf(),
                                comprador.listarCarrinho());
                        System.out.println("Produto adicionado ao carrinho.");
                    } else {
                        System.out.println("Produto não encontrado.");
                    }
                }
                case "3" -> {
                    System.out.print("Digite o ID do produto que deseja remover: ");
                    String idProduto = scanner.nextLine();
                    Produto produto = ProdutoController.buscarProdutoPorID(idProduto);

                    if (produto != null) {
                        comprador.removerProdutoDoCarrinho(produto);
                        // Atualiza o carrinho no arquivo
                        CompradorController.atualizarCarrinhoDoComprador(
                                comprador.getCpf(),
                                comprador.listarCarrinho());
                        System.out.println("Produto removido do carrinho.");
                    } else {
                        System.out.println("Produto não encontrado.");
                    }
                }
                case "4" -> {
                    if (comprador.listarCarrinho().isEmpty()) {
                        System.out.println("Carrinho vazio! Não é possível finalizar a compra.");
                    } else {
                        // Aqui você implementaria a lógica de finalização de compra
                        System.out.println("Compra finalizada com sucesso!");
                        comprador.listarCarrinho().clear();
                        CompradorController.atualizarCarrinhoDoComprador(
                                comprador.getCpf(),
                                new ArrayList<>());
                    }
                }
                case "0" -> {
                    return;
                }
                default -> System.out.println("⚠️ Opção inválida. Tente novamente.");
            }
        }
    }

    // Menu geral para. OBS: Ao entrar neste menu o usuário já deve ter feito o
    // login.
    public static void MenuSelecionador(Scanner scanner, boolean logadoADM, boolean logadoComprador, boolean logadoLoja,
            String id, String senha) {
        String escolha = "5";

        while (!escolha.equals("0")) {
            if (logadoLoja) {
                System.out.println("\n================ MENU PRINCIPAL ================");
                System.out.println("1️⃣ Menu Produto");
                System.out.println("0️⃣ Logout");
                System.out.print("🔹 Escolha uma opção: ");

            } else if (logadoComprador) {
                System.out.println("\n================ MENU PRINCIPAL ================");
                System.out.println("1️⃣ Menu Produto");
                System.out.println("2️⃣ Menu Loja");
                System.out.println("0️⃣ Logout");
                System.out.print("🔹 Escolha uma opção: ");
            }

            else if (logadoADM) {
                System.out.println("\n================ MENU PRINCIPAL ================");
                System.out.println("1️⃣ Menu Loja");
                System.out.println("2️⃣ Menu Comprador");
                System.out.println("3️⃣ Menu Produto");
                System.out.println("4️⃣ Menu Produto para Comprador"); // Opção de Produto para Comprador
                System.out.println("0️⃣ Logout");
                System.out.print("🔹 Escolha uma opção: ");
            }

            escolha = scanner.next();
            scanner.nextLine();

            if (logadoLoja) {
                switch (escolha) {
                    case "1" -> FachadaMenus.menuProdutoParaLojas(scanner, id);
                    case "0" -> {
                        System.out.println("👋 Fazendo logout...");
                        break;
                    }
                    default -> System.out.println("⚠️ Opção inválida. Tente novamente.");
                }
            } else if (logadoADM) {
                switch (escolha) {
                    case "1" -> FachadaMenus.menuLojaCompleto(scanner);
                    case "2" -> FachadaMenus.menuComprador(scanner);
                    case "3" -> FachadaMenus.menuProdutoParaAdmin(scanner);
                    case "4" -> FachadaMenus.menuProdutoParaComprador(scanner, id); // Chama o menu Produto para
                                                                                    // Comprador
                    case "0" -> {
                        System.out.println("👋 Fazendo logout...");
                        break;
                    }
                    default -> System.out.println("⚠️ Opção inválida. Tente novamente.");
                }
            } else if (logadoComprador) {
                switch (escolha) {
                    case "1" -> FachadaMenus.menuProdutoParaComprador(scanner, id);
                    case "2" -> FachadaMenus.menuLojaParaCompradores(scanner);
                    case "0" -> {
                        System.out.println("👋 Fazendo logout...");
                        break;
                    }
                    default -> System.out.println("⚠️ Opção inválida. Tente novamente.");
                }
            }
        }
    }

}
