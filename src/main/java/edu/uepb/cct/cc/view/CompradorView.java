package edu.uepb.cct.cc.view;

import edu.uepb.cct.cc.controller.CompradorController;
import edu.uepb.cct.cc.model.Comprador;

import java.util.List;
import java.util.Scanner;

public class CompradorView {

    public void cadastrarComprador() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("=== Cadastro de Comprador ===");

            String nome, email, senha, cpf, endereco;

            // Validando Nome
            System.out.print("Nome: ");
            nome = scanner.nextLine();
            while (nome == null || nome.isEmpty()) {
                System.out.print("Nome não pode ser vazio. Por favor, insira o nome novamente: ");
                nome = scanner.nextLine();
            }

            // Validando E-mail
            System.out.print("E-mail: ");
            email = scanner.nextLine();
            while (email == null || email.isEmpty() || !email.contains("@")) {
                System.out.print("E-mail inválido. Por favor, insira um e-mail válido: ");
                email = scanner.nextLine();
            }

            // Validando Senha
            System.out.print("Senha (mínimo 6 caracteres): ");
            senha = scanner.nextLine();
            while (senha == null || senha.length() < 6) {
                System.out.print("A senha deve ter pelo menos 6 caracteres. Por favor, insira uma senha válida: ");
                senha = scanner.nextLine();
            }

            // Validando CPF
            System.out.print("CPF (XXX.XXX.XXX-XX): ");
            cpf = scanner.nextLine();
            while (cpf == null || cpf.isEmpty() || !cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
                System.out.print("CPF inválido. O formato deve ser XXX.XXX.XXX-XX. Por favor, insira um CPF válido: ");
                cpf = scanner.nextLine();
            }

            // Validando Endereço
            System.out.print("Endereço: ");
            endereco = scanner.nextLine();
            while (endereco == null || endereco.isEmpty()) {
                System.out.print("Endereço não pode ser vazio. Por favor, insira um endereço válido: ");
                endereco = scanner.nextLine();
            }

            // Criar comprador
            Comprador comprador = new Comprador(nome, email, senha, cpf, endereco);

            // Enviar para o controller
            CompradorController.create(comprador);

            System.out.println("Cadastro realizado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado.");
        }
    }

    public void listarCompradores() {
        List<Comprador> compradores = CompradorController.getTodosCompradores();

        System.out.println("=== Lista de Compradores Cadastrados ===");
        if (compradores.isEmpty()) {
            System.out.println("Nenhum comprador cadastrado.");
            return;
        }

        for (int i = 0; i < compradores.size(); i++) {
            Comprador comprador = compradores.get(i);
            System.out.println((i + 1) + ". " + comprador.getNome() + " - " + comprador.getCpf());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o número do comprador para ver detalhes ou 0 para sair: ");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha > 0 && escolha <= compradores.size()) {
            Comprador compradorSelecionado = compradores.get(escolha - 1);
            System.out.println("Detalhes do Comprador:");
            System.out.println("Nome: " + compradorSelecionado.getNome());
            System.out.println("E-mail: " + compradorSelecionado.getEmail());
            System.out.println("CPF: " + compradorSelecionado.getCpf());
            System.out.println("Endereço: " + compradorSelecionado.getEndereco());
        } else {
            System.out.println("Saindo da visualização de compradores.");
        }
    }
}
