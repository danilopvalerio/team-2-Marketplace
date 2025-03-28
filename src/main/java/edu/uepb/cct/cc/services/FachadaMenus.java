package edu.uepb.cct.cc.services;

import java.util.Scanner;

import edu.uepb.cct.cc.view.*;

public class FachadaMenus {

    // Menus de COMPRADOR para ADMIN
    public static void menuComprador(Scanner scanner) {
        CompradorView compradorView = new CompradorView();
        

        while (true) {
            System.out.println("\n=== Menu Comprador ===");
            System.out.println("1. Cadastrar Comprador");
            System.out.println("2. Listar Compradores");
            System.out.println("3. Atualizar Comprador");
            System.out.println("4. Deletar Comprador");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

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
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    // Menus de LOJA com diferentes permissões para ADMIN, COMPRADOR
    public static void menuLojaCompleto(Scanner scanner) {
        LojaView lojaView = new LojaView();
        

        while (true) {
            System.out.println("\n=== Menu Loja ===");
            System.out.println("1. Cadastrar Loja");
            System.out.println("2. Deletar Loja");
            System.out.println("3. Listar Lojas");
            System.out.println("4. Buscar Loja");
            System.out.println("5. Atualizar Loja");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

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
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    public static void menuLojaParaCompradores(Scanner scanner) {
        LojaView lojaView = new LojaView();

        while (true) {
            System.out.println("\n=== Menu Loja ===");
            System.out.println("1. Listar Lojas");
            System.out.println("2. Buscar Loja");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            String escolha = scanner.next();
            scanner.nextLine();

            switch (escolha) {
                case "1" -> lojaView.listarLojas();
                case "2" -> lojaView.buscarLoja();
                case "0" -> {
                    return;
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    // Menus de PRODUTOS com diferentes permissões para ADMIN, LOJISTA E COMPRADOR
    public static void menuProdutoParaAdmin(Scanner scanner) {


        while (true) {
            System.out.println("\n=== Menu Produto ===");
            System.out.println("1. Cadastrar Produto");
            System.out.println("2. Listar produtos");
            System.out.println("3. Buscar produtos por id");
            System.out.println("4. Buscar produtos por loja");
            System.out.println("5. Atualizar Produto");
            System.out.println("6. Deletar produto");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            String escolha = scanner.next();
            scanner.nextLine();

            // Tratar na view e no controller para caso o id seja "Admin" liste todos os produtos de todas as lojas
            // Caso o id não seja "Admin", ou seja, caso o usuário seja
            
            switch (escolha) {
                case "1" -> ProdutoView.cadastrarProduto("Admin");
                case "2" -> ProdutoView.listarTodosProdutos();
                case "3" -> ProdutoView.buscarProdutoPorID("Admin");
                case "4" -> ProdutoView.listarProdutosPorLoja("Admin");
                case "5" -> ProdutoView.atualizarProduto();
                case "6" -> ProdutoView.deletarProduto();
                case "0" -> {
                    return;
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    public static void menuProdutoParaLojas(Scanner scanner, String id ) {
        ProdutoView produtoView = new ProdutoView();

        while (true) {
            System.out.println("\n=== Menu Produto ===");
            System.out.println("1. Cadastrar Produto");
            System.out.println("2. Listar produtos");
            System.out.println("3. Buscar produtos");
            System.out.println("4. Atualizar Produto");
            System.out.println("5. Deletar produto");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            String escolha = scanner.next();
            scanner.nextLine();

            switch (escolha) {
                case "1" -> produtoView.cadastrarProduto(id);

                            // Tratar na view e no controller para caso o id seja "Admin" liste todos os produtos de todas as lojas
                            // Caso o id não seja "Admin", ou seja, caso o usuário seja

                            // O método de listar deve filtrar somente os produtos do id do parâmetro.
                case "2" -> System.out.println("Indisponível pois ainda está em desenvolvimento.");

                            // O método de buscar deve filtrar somente os produtos do id do parâmetro.
                case "3" -> System.out.println("Indisponível pois ainda está ainda em desenvolvimento.");

                            // O método de deletar deve filtrar somente os produtos do id do parâmetro.
                case "4" -> System.out.println("Indisponível pois ainda está ainda em desenvolvimento.");

                            // O método de atualizar deve filtrar somente os produtos do id do parâmetro.
                case "5" -> System.out.println("Indisponível pois ainda está ainda em desenvolvimento.");

                case "0" -> {
                    return;
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    public static void menuProdutoParaComprador(Scanner scanner, String id ) {

        while (true) {
            System.out.println("\n=== Menu Produto ===");
            System.out.println("1. Listar produtos por loja");
            System.out.println("2. Listar todos os produtos");
            System.out.println("3. Buscar produtos");
            System.out.println("0. Voltar");
            System.out.println("Escolha uma opção: ");

            String escolha = scanner.next();
            scanner.nextLine();

            switch (escolha) {
                case "1" -> {
                    System.out.println("Digite o ID da loja (CNPJ/CPF) para listar os produtos: ");
                    String idLoja = scanner.nextLine();
                    ProdutoView.listarProdutosPorLoja(idLoja);
                }

                case "2" -> ProdutoView.listarTodosProdutos();
                case "3" ->{
                    System.out.println("Digite o ID do produto: ");
                    String idProd = scanner.nextLine(); 
                    ProdutoView.buscarProdutoPorID(idProd);}

                case "0" -> {
                    return;
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    // Menu geral para. OBS: Ao entrar neste menu o usuário já deve ter feito o login.
    public static void MenuSelecionador(Scanner scanner, boolean logadoADM, boolean logadoComprador, boolean logadoLoja, String id, String senha){
        
        String escolha = "5";

        while (!escolha.equals("0")) {
            if (logadoLoja) {
                System.out.println("\n=== Menu Principal ===");
                System.out.println("1. Menu Produto");
                System.out.println("0. Logout");
                System.out.print("Escolha uma opção: ");

            } else if (logadoComprador) {
                System.out.println("\n=== Menu Principal ===");
                System.out.println("1. Menu Produto");
                System.out.println("2. Menu Loja");
                System.out.println("0. Logout");
                System.out.print("Escolha uma opção: ");
            } else if (logadoADM) {
                System.out.println("\n=== Menu Principal ===");
                System.out.println("1. Menu Loja");
                System.out.println("2. Menu Comprador");
                System.out.println("3. Menu Produto");
                System.out.println("0. Logout");
                System.out.print("Escolha uma opção: ");
            }

            escolha = scanner.next();
            scanner.nextLine();
            
            if (logadoLoja) {
                switch (escolha) {
                    case "1" -> FachadaMenus.menuProdutoParaLojas(scanner, id);
                    case "0" -> {
                        System.out.println("Fazendo logout...");
                        break;
                    }
                    default -> System.out.println("Opção inválida. Tente novamente.");
                }
            } else if (logadoADM) {

                switch (escolha) {
                    case "1" -> FachadaMenus.menuLojaCompleto(scanner); 
                    case "2" -> FachadaMenus.menuComprador(scanner);
                    case "3" -> FachadaMenus.menuProdutoParaAdmin(scanner);
                    case "0" -> {
                        System.out.println("Fazendo logout...");
                        break;
                    }
                    default -> System.out.println("Opção inválida. Tente novamente.");
                }

            } else if (logadoComprador) {
                switch (escolha) {
                    case "1" -> FachadaMenus.menuProdutoParaComprador(scanner, id);
                    case "2" -> FachadaMenus.menuLojaParaCompradores(scanner);
                    case "0" -> {
                        System.out.println("Fazendo logout...");
                        break;
                    }
                    default -> System.out.println("Opção inválida. Tente novamente.");
                }
            }
        }
    }
}
