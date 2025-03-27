package edu.uepb.cct.cc.services;


import java.util.Scanner;

public class FachadaLogin {

    public static void realizarLogin(Scanner scanner) {
        String id = "", senha = "";
        boolean logadoADM = false, logadoComprador = false, logadoLoja = false;

        String tipoUsuario = "5";  // Mudança para String
        while (!tipoUsuario.equals("0")) {
            System.out.println("\n=== Escolha o Tipo de Usuário ===");
            System.out.println("1. Comprador");
            System.out.println("2. Loja");
            System.out.println("3. Admin");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            tipoUsuario = scanner.nextLine();  // Mudança para nextLine()

            if (tipoUsuario.equals("0")) {
                System.out.println("Saindo...");
                return;
            }

            if (!tipoUsuario.equals("1") && !tipoUsuario.equals("2") && !tipoUsuario.equals("3") && !tipoUsuario.equals("0")) {
                System.out.println("Opção inválida! Por favor, escolha uma opção válida.");
                continue; // Volta para o início do loop e exibe o menu novamente
            }

            // Pedir o identificador e a senha
            System.out.print("Digite seu identificador: ");
            id = scanner.nextLine();
            System.out.print("Digite sua senha: ");
            senha = scanner.nextLine();

            // Validando login
            if (tipoUsuario.equals("3")) { // Login como Admin
                if (ValidadorLogin.loginADM(id, senha)) {
                    logadoADM = true;
                    logadoComprador = false;
                    logadoLoja = false;
                    System.out.println("Login como Admin realizado com sucesso!");
                    FachadaMenus.MenuSelecionador(scanner, logadoADM, logadoComprador, logadoLoja, id, senha);
                } else {
                    System.out.println("Credenciais de Admin inválidas!");
                }
            } else if (tipoUsuario.equals("2")) { // Login como Loja
                if (ValidadorLogin.loginLoja(id, senha)) {
                    logadoADM = false;
                    logadoComprador = false;
                    logadoLoja = true;
                    System.out.println("Login como Loja realizado com sucesso!");
                    FachadaMenus.MenuSelecionador(scanner, logadoADM, logadoComprador, logadoLoja, id, senha);
                } else {
                    System.out.println("Credenciais de Loja inválidas!");
                }
            } else if (tipoUsuario.equals("1")) { // Login como Comprador
                if (ValidadorLogin.loginComprador(id, senha)) {
                    logadoADM = false;
                    logadoComprador = true;
                    logadoLoja = false; // Corrigido para logadoLoja ser falso
                    System.out.println("Login como Comprador realizado com sucesso!");
                    FachadaMenus.MenuSelecionador(scanner, logadoADM, logadoComprador, logadoLoja, id, senha);
                } else {
                    System.out.println("Credenciais de Comprador inválidas!");
                }
            }
        }
    }
}
