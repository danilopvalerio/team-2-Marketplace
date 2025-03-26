package edu.uepb.cct.cc.view;

import edu.uepb.cct.cc.controller.LojaController;
import edu.uepb.cct.cc.model.Loja;

// import java.util.List;
import java.util.Scanner;

public class LojaView {

    public void cadastrarLoja() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("=== Cadastro de Loja ===");
            String nome, email, senha, cpfCnpj, endereco;

            System.out.print("Nome: ");
            nome = scanner.nextLine();
            while (nome.isEmpty()) {
                System.out.print("Nome não pode ser vazio. Insira novamente: ");
                nome = scanner.nextLine();
            }

            System.out.print("E-mail: ");
            email = scanner.nextLine();
            while (email.isEmpty() || !email.contains("@")) {
                System.out.print("E-mail inválido. Insira um e-mail válido: ");
                email = scanner.nextLine();
            }

            System.out.print("Senha (mínimo 6 caracteres): ");
            senha = scanner.nextLine();
            while (senha.length() < 6) {
                System.out.print("A senha deve ter pelo menos 6 caracteres. Insira novamente: ");
                senha = scanner.nextLine();
            }

            System.out.print("CPF/CNPJ: ");
            cpfCnpj = scanner.nextLine();
            while (!cpfCnpj.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}|\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}")) {
                System.out.print("CPF/CNPJ inválido. Insira um CPF/CNPJ válido: ");
                cpfCnpj = scanner.nextLine();
            }

            System.out.print("Endereço: ");
            endereco = scanner.nextLine();
            while (endereco.isEmpty()) {
                System.out.print("Endereço não pode ser vazio. Insira novamente: ");
                endereco = scanner.nextLine();
            }

            Loja loja = new Loja(nome, email, senha, cpfCnpj, endereco);
            LojaController.create(loja);

            System.out.println("Cadastro realizado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado.");
        }
    }

    // public void listarLojas() {
    //     List<Loja> lojas = LojaController.getTodasLojas();
    //     System.out.println("=== Lista de Lojas Cadastradas ===");
    //     if (lojas.isEmpty()) {
    //         System.out.println("Nenhuma loja cadastrada.");
    //         return;
    //     }

    //     for (int i = 0; i < lojas.size(); i++) {
    //         Loja loja = lojas.get(i);
    //         System.out.println((i + 1) + ". " + loja.getNome() + " - " + loja.getCpfCnpj());
    //     }
    // }

    public void deletarLoja() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Deletar Loja ===");

        System.out.print("Digite o CPF/CNPJ da loja a ser deletada: ");
        String cpfCnpj = scanner.nextLine();
        while (!cpfCnpj.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}|\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}")) {
            System.out.print("CPF/CNPJ inválido. Insira um CPF/CNPJ válido: ");
            cpfCnpj = scanner.nextLine();
        }

        try {
            LojaController.deleteLojaPorCpfCnpj(cpfCnpj);
            System.out.println("Loja deletada com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}