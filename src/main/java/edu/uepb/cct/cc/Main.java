package edu.uepb.cct.cc;

import edu.uepb.cct.cc.view.CompradorView;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CompradorView compradorView = new CompradorView();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Menu Comprador ===");
            System.out.println("1. Cadastrar Comprador");
            System.out.println("2. Listar Compradores");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            int escolha = scanner.nextInt();
            scanner.nextLine();  // Limpa o buffer do scanner

            switch (escolha) {
                case 1:
                    compradorView.cadastrarComprador();
                    break;
                case 2:
                    compradorView.listarCompradores();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}
