package edu.uepb.cct.cc;

import edu.uepb.cct.cc.view.CompradorView;
import edu.uepb.cct.cc.view.LojaView;
import edu.uepb.cct.cc.view.ProdutoView;

import java.util.Scanner;

public class Main {
    private static boolean isADM, logadoADM, isLoja, logadoLoja, logadoComprador;
    private static String id;
    private static String senha;
    private static final int ADM = 3, LOJA = 2, COMPRADOR = 1;

    public static void menuComprador() {
        CompradorView compradorView = new CompradorView();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Menu Comprador ===");
            System.out.println("1. Cadastrar Comprador");
            System.out.println("2. Listar Compradores");
            System.out.println("3. Atualizar Comprador");
            System.out.println("4. Deletar Comprador");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            int escolha = scanner.nextInt();
            scanner.nextLine();

            if(logadoADM){
                switch (escolha) {
                    case 1 -> compradorView.cadastrarComprador();
                    case 2 -> compradorView.listarCompradores();
                    case 3 -> compradorView.atualizarComprador();
                    case 4 -> compradorView.deletarComprador();
                    case 0 -> {
                        return;
                    }
                    default -> System.out.println("Opção inválida. Tente novamente.");
                }
            }
        }
    }

    // Somente o ADMIN tem acesso a este menu
    public static void menuLoja(String id, String senha) {
        LojaView lojaView = new LojaView();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Menu Loja ===");
            System.out.println("1. Cadastrar Loja");
            System.out.println("2. Deletar Loja");
            System.out.println("3. Listar Lojas");
            System.out.println("4. Buscar Loja");
            System.out.println("5. Atualizar Loja");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            int escolha = scanner.nextInt();
            scanner.nextLine();

            switch (escolha) {
                case 1 -> {
                    if (logadoADM) {
                        lojaView.cadastrarLoja();
                    } else {
                        System.out.println(
                                "Somente administradores podem cadastrar lojas, entre em contato com o ADM.\n" + //
                                        "Por favor entre em contato com o Administrador do sistema.");
                    }
                }

                case 2 -> {
                    if (logadoADM) {
                        lojaView.deletarLoja();
                    } else {
                        System.out.println(
                                "Somente administradores podem deletar lojas.\n Por favor entre em contato com o Administrador do sistema.");
                    }
                }

                case 3 -> lojaView.listarLojas();

                case 4 -> lojaView.buscarLoja();

                case 5 -> {
                    if (logadoADM) {
                        lojaView.atualizarLoja();
                    } else {
                        System.out.println("Somente administradores podem atualizar lojas. \n" + //
                                "Por favor entre em contato com o Administrador do sistema.");
                    }
                }

                case 0 -> {
                    return;
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    // OBS:
    //      Todos tem acesso a esse menu mas somente o ADMIN tem acesso total. 
    //      Lojas somente podem alterar ou adicionar produtos de seus próprios comércios.
    
    public static void menuProduto(String id, String senha) {
        ProdutoView produtoView = new ProdutoView();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Menu Produto ===");
            System.out.println("1. Cadastrar Produto");
            System.out.println("2. Listar produtos");
            System.out.println("3. Listar produtos");
            System.out.println("4. Atualizar Produto");
            System.out.println("5. Deletar produto");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            int escolha = scanner.nextInt();
            scanner.nextLine();

            switch (escolha) {
                case 1 -> {
                    if (isLoja) {
                        produtoView.cadastrarProduto();
                    } else {
                        System.out.println("Você não tem permissão para cadastrar produtos.");
                    }
                }

                case 2 -> System.out.println("Ainda em desenvolvimento.");

                case 3 -> System.out.println("Ainda em desenvolvimento.");

                case 4 -> System.out.println("Ainda em desenvolvimento.");

                case 5 -> System.out.println("Ainda em desenvolvimento.");

                case 0 -> {
                    return;
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            logadoADM = false;
            logadoComprador = false;
            logadoLoja = false;
            id = "";
            senha = "";
            System.out.println("\n=== Escolha o Tipo de Usuário ===");
            System.out.println("1. Comprador");
            System.out.println("2. Loja");
            System.out.println("3. Admin");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            int tipoUsuario = scanner.nextInt();
            scanner.nextLine();

            if (tipoUsuario == 0) {
                System.out.println("Saindo...");
                return;
            }

            // Pedir o identificador e a senha
            System.out.print("Digite seu identificador: ");
            id = scanner.nextLine();

            System.out.print("Digite sua senha: ");
            senha = scanner.nextLine();

            // Validar login

            // Login como ADM
            if (tipoUsuario == ADM) {
                if (id.equals("biscoito") && senha.equals("YTB-8@")) {
                    logadoADM = true;
                    logadoComprador = false;
                    logadoLoja = false;
                    System.out.println("Login como Admin realizado com sucesso!");
                } else {
                    System.out.println("Credenciais de Admin inválidas!");
                    continue;
                }
            } else if (tipoUsuario == LOJA) {
                // if (id.matches("\\d{11}|\\d{14}") && senha.equals("loja_senha")) {
                // System.out.println("Login como Loja realizado com sucesso!");
                // logadoLoja = true;
                // logadoADM = false;
                // logadoComprador = false;
                // } else {
                // System.out.println("Credenciais de Loja inválidas!");
                // continue;
                // }
            } else if (tipoUsuario == COMPRADOR) {
                // if (id.matches("\\d{11}") && senha.equals("comprador_senha")) {
                logadoComprador = true;
                logadoLoja = false;
                logadoADM = false;
                // System.out.println("Login como Comprador realizado com sucesso!");
                // } else {
                // System.out.println("Credenciais de Comprador inválidas!");
                // continue;
                // }
            }
            int escolha = 5;

            while (escolha != 0) {
                if (logadoLoja) {
                    System.out.println("\n=== Menu Principal ===");
                    System.out.println("1. Menu Loja");
                    System.out.println("2. Menu Produto");
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

                escolha = scanner.nextInt();
                scanner.nextLine();
                if (logadoLoja) {
                    switch (escolha) {
                        case 1 -> menuLoja(id, senha);
                        case 2 -> menuProduto(id, senha);
                        case 0 -> {
                            System.out.println("Fazendo logout...");
                            break;
                        }
                        default -> System.out.println("Opção inválida. Tente novamente.");
                    }
                } else if (logadoComprador) {
                    switch (escolha) {
                        case 1 -> menuProduto(id, senha);
                        case 2 -> menuLoja(id, senha);
                        case 0 -> {
                            System.out.println("Fazendo logout...");
                            break;
                        }
                        default -> System.out.println("Opção inválida. Tente novamente.");
                    }
                } else if (logadoADM) {
                    switch (escolha) {
                        case 1 -> menuLoja("0", "0");
                        case 2 -> menuComprador();
                        case 3 -> menuProduto("0", "0");
                        case 0 -> {
                            System.out.println("Fazendo logout...");
                            break;
                        }
                        default -> System.out.println("Opção inválida. Tente novamente.");
                    }
                }
            }
        }
    }
}
