package edu.uepb.cct.cc.view;

import edu.uepb.cct.cc.controller.CompradorController;
import edu.uepb.cct.cc.controller.VendaController;
import edu.uepb.cct.cc.model.Comprador;
import edu.uepb.cct.cc.model.Venda;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class VendaView {

    public static String gerarIdPedido() {
        return UUID.randomUUID().toString()
                .replace("-", "") // Remove hifens
                .substring(0, 5) // Pega os 5 primeiros caracteres
                .toUpperCase(); // Converte para maiúsculas
    }

    public void finalizarCarrinho(String cpfComprador) {
        Scanner scanner = new Scanner(System.in);

        // Obter o comprador pelo CPF
        Comprador comprador = CompradorController.getCompradorPorCpf(cpfComprador);
        if (comprador == null) {
            System.out.println("⚠️ Comprador não encontrado!");
            return;
        }

        // Verificar se o carrinho está vazio
        if (comprador.listarCarrinho().isEmpty()) {
            System.out.println("🛒 Seu carrinho está vazio! Não há itens para finalizar.");
            return;
        }

        // Exibir itens no carrinho
        System.out.println("\nItens no carrinho:");
        comprador.visualizarCarrinho();

        // Solicitar confirmação
        System.out.print("\nDeseja confirmar a compra? (S/N): ");
        String confirmacao = scanner.nextLine();

        if (!confirmacao.equalsIgnoreCase("S")) {
            System.out.println("🛑 Compra cancelada. O carrinho permanece intacto.");
            return;
        }

        // Processar a venda
        try {
            // Criar objeto Venda a partir do carrinho do comprador
            Venda venda = new Venda(
                    gerarIdPedido(), // Gera um ID único para a venda
                    comprador.getCpf(),
                    LocalDate.now(),
                    comprador.listarCarrinho().stream().map(item -> item.getProduto().getId()).toList(),
                    comprador.listarCarrinho().stream().map(item -> Double.valueOf(item.getProduto().getValor()))
                            .toList(),
                    comprador.listarCarrinho().stream().map(item -> item.getQuantidade()).toList());

            VendaController vendaController = new VendaController();
            vendaController.registrarVenda(venda); // Registrar a venda no sistema
            VendaController controller = new VendaController();


            // Esvaziar o carrinho
            comprador.getCarrinho().clear();
            CompradorController.atualizarCarrinhoDoComprador(comprador.getCpf(), new ArrayList<>());

            System.out.println("✅ Compra realizada com sucesso! Obrigado pela preferência.");
        } catch (Exception e) {
            System.out.println("❌ Ocorreu um erro ao processar a venda: " + e.getMessage());
        }
    }

}