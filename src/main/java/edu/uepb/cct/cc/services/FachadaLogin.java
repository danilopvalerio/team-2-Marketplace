package edu.uepb.cct.cc.services;

import java.util.Scanner;

public class FachadaLogin {

    private static void imprimirLinha() {
        System.out.println("=========================================");
    }

    private static void imprimirCabecalho(String titulo) {
        imprimirLinha();
        System.out.println("         " + titulo);
        imprimirLinha();
    }

    public static void realizarLogin(Scanner scanner) {
        String id, senha;
        boolean logadoADM = false, logadoComprador = false, logadoLoja = false;

        String tipoUsuario = "";
        while (!tipoUsuario.equals("0")) {
            imprimirCabecalho("LOGIN");
            System.out.println("1️⃣  Comprador");
            System.out.println("2️⃣  Loja");
            System.out.println("3️⃣  Admin");
            System.out.println("0️⃣  Sair");
            imprimirLinha();
            System.out.print("Escolha uma opção: ");

            tipoUsuario = scanner.nextLine();

            if (tipoUsuario.equals("0")) {
                System.out.println("👋 Saindo...");
                return;
            }

            if (!tipoUsuario.matches("[123]") ) {
                System.out.println("⚠️ Opção inválida! Escolha uma opção válida.");
                continue;
            }

            System.out.print("🔑 Digite seu identificador: ");
            id = scanner.nextLine();
            System.out.print("🔒 Digite sua senha: ");
            senha = scanner.nextLine();

            switch (tipoUsuario) {
                case "3" -> {
                    if (ValidadorLogin.loginADM(id, senha)) {
                        logadoADM = true;
                        logadoLoja = false; logadoComprador = false;
                        System.out.println("✅ Login como Admin realizado com sucesso!");
                        FachadaMenus.MenuSelecionador(scanner, logadoADM, logadoComprador, logadoLoja, id, senha);
                    } else {
                        System.out.println("❌ Credenciais de Admin inválidas.");
                    }
                }
                case "2" -> {
                    if (ValidadorLogin.loginLoja(id, senha)) {
                        logadoLoja = true;
                        logadoADM = false; logadoComprador = false;
                        System.out.println("✅ Login como Loja realizado com sucesso!");
                        FachadaMenus.MenuSelecionador(scanner, logadoADM, logadoComprador, logadoLoja, id, senha);
                    } else {
                        System.out.println("❌ Credenciais de Loja inválidas!");
                    }
                }
                case "1" -> {
                    if (ValidadorLogin.loginComprador(id, senha)) {
                        logadoComprador = true;
                        logadoLoja = false; logadoADM = false;
                        System.out.println("✅ Login como Comprador realizado com sucesso!");
                        FachadaMenus.MenuSelecionador(scanner, logadoADM, logadoComprador, logadoLoja, id, senha);
                    } else {
                        System.out.println("❌ Credenciais de Comprador inválidas!");
                    }
                }
            }
        }
    }
}