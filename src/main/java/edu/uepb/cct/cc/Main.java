package edu.uepb.cct.cc;

import edu.uepb.cct.cc.view.CompradorView;
import edu.uepb.cct.cc.view.LojaView;

import java.util.Scanner;

public class Main {
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

            switch (escolha) {
                case 1 -> compradorView.cadastrarComprador();
                case 2 -> compradorView.listarCompradores();
                case 3 -> compradorView.atualizarComprador();
                case 4 -> compradorView.deletarComprador();
                case 0 -> { return; }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    public static void menuLoja() {
        LojaView lojaView = new LojaView();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Menu Loja ===");
            System.out.println("1. Cadastrar Loja");
            System.out.println("2. Deletar Loja");
            System.out.println("3. Listar Lojas");
            System.out.println("4. Buscar Loja");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            int escolha = scanner.nextInt();
            scanner.nextLine();

            switch (escolha) {
                case 1 -> lojaView.cadastrarLoja();
                case 2 -> lojaView.deletarLoja();
                case 3 -> lojaView.listarLojas();
                case 4 -> lojaView.buscarLoja();
                case 0 -> { return; }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Menu Principal ===");
            System.out.println("1. Menu Comprador");
            System.out.println("2. Menu Loja");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            int escolha = scanner.nextInt();
            scanner.nextLine();

            switch (escolha) {
                case 1 -> menuComprador();
                case 2 -> menuLoja();
                case 0 -> {
                    System.out.println("Saindo...");
                    return;
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}
