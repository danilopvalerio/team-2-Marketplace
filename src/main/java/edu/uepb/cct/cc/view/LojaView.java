package edu.uepb.cct.cc.view;

import edu.uepb.cct.cc.controller.LojaController;
import edu.uepb.cct.cc.controller.ProdutoController;
import edu.uepb.cct.cc.model.*;
import java.util.List;
import java.util.Scanner;

public class LojaView {

    public void cadastrarLoja() {
        Scanner scanner = new Scanner(System.in);
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

        // Passa os dados diretamente para o controller
        LojaController.createLoja(nome, email, senha, cpfCnpj, endereco);

        System.out.println("Cadastro realizado com sucesso!");
    }

    public void listarLojas() {
        List<Loja> lojas = LojaController.getTodasLojas();
        System.out.println("=== Lista de Lojas Cadastradas ===");
        if (lojas.isEmpty()) {
            System.out.println("Nenhuma loja cadastrada.");
            return;
        }

        for (int i = 0; i < lojas.size(); i++) {
            Loja loja = lojas.get(i);
            System.out.println((i + 1) + ". " + loja.getNome() + " - " + loja.getCpfCnpj());
        }
    }

    public static void buscarLoja() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("=== Buscar Loja ===");
            System.out.print("Digite o CPF/CNPJ da loja a ser buscada: ");
            String cpfCnpj = scanner.nextLine();
            while (!cpfCnpj.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}|\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}")) {
                System.out.print("CPF/CNPJ inválido. Insira um CPF/CNPJ válido: ");
                cpfCnpj = scanner.nextLine();
            }

            Loja loja = LojaController.getLojaPorCpfCnpj(cpfCnpj);

            if (loja == null) {
                System.out.println("Loja não encontrada.");
                return;
            } else {
                System.out.println("Nome: " + loja.getNome() + "\nCPF/CNPJ: " + loja.getCpfCnpj() + "\nEmail: "
                    + loja.getEmail() + "\nEndereço: " + loja.getEndereco());
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
            return;
        } catch (Exception e) {
            System.out.println("Erro inesperado ao buscar o produto.");
            return;
        }
    }


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

    public void atualizarLoja() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Atualizar Loja ===");
        System.out.print("Digite o CPF/CNPJ da loja a ser atualizada: ");
        String cpfCnpj = scanner.nextLine();
        while (!cpfCnpj.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}|\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}")) {
            System.out.print("CPF/CNPJ inválido. Insira um CPF/CNPJ válido: ");
            cpfCnpj = scanner.nextLine();
        }

        try {
            Loja lojaExistente = LojaController.getLojaPorCpfCnpj(cpfCnpj);
            if (lojaExistente == null) {
                System.out.println("Loja não encontrada.");
                return;
            }

            System.out.println("Deixe em branco os campos que não deseja alterar.");

            System.out.print("Novo Nome [" + lojaExistente.getNome() + "]: ");
            String nome = scanner.nextLine();
            nome = nome.isEmpty() ? lojaExistente.getNome() : nome;

            System.out.print("Novo E-mail [" + lojaExistente.getEmail() + "]: ");
            String email = scanner.nextLine();
            email = email.isEmpty() ? lojaExistente.getEmail() : email;

            System.out.print("Nova Senha (mínimo 6 caracteres) [*****]: ");
            String senha = scanner.nextLine();
            senha = senha.isEmpty() ? lojaExistente.getSenha() : senha;

            System.out.print("Novo Endereço [" + lojaExistente.getEndereco() + "]: ");
            String endereco = scanner.nextLine();
            endereco = endereco.isEmpty() ? lojaExistente.getEndereco() : endereco;

            LojaController.atualizarLoja(cpfCnpj, nome, email, senha, endereco);

            System.out.println("Loja atualizada com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
