package edu.uepb.cct.cc.view;

import java.util.Scanner;

import edu.uepb.cct.cc.services.FachadaLogin;

public class MenuRegistro {
    public static String escolha;
    public static void exibirMenuRegistro(Scanner scanner) {
        escolha = "5";
        while (!(escolha == "0")) {
            System.out.println("\n=== Registro ===");
            System.out.println("1. Registrar como Comprador");
            System.out.println("2. Registrar como Loja");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            String escolha = scanner.next();
            scanner.nextLine();

            switch (escolha) {
                case "1" -> {
                    CompradorView compradorView = new CompradorView();
                    compradorView.cadastrarComprador();
                    return;
                }
                case "2" -> {
                    LojaView lojaView = new LojaView();
                    lojaView.cadastrarLoja();
                    return;
                }
                case "0" -> {
                    return;
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    public static void menuInicial(Scanner scanner) {
        while (true) {
            System.out.println("\n=== Bem-vindo ===");
            System.out.println("1. Login");
            System.out.println("2. Registrar");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            String escolha = scanner.next();
            scanner.nextLine();

            switch (escolha) {
                case "1" -> FachadaLogin.realizarLogin(scanner);
                case "2" -> exibirMenuRegistro(scanner);
                case "0" -> {
                    System.out.println("Saindo...");
                    System.exit(0);
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}

