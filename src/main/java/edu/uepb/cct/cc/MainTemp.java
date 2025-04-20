package edu.uepb.cct.cc;

import edu.uepb.cct.cc.controller.LojaController;
import edu.uepb.cct.cc.controller.VendaController;
import edu.uepb.cct.cc.model.Venda;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MainTemp {
    public static void main(String[] args) {
        String cpfCnpjLoja = "123.456.789-10"; // Substitua pelo CPF/CNPJ da loja que deseja consultar

        List<Object> pedidos = LojaController.getHistoricoPedidosDaLoja(cpfCnpjLoja);

        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido encontrado para a loja " + cpfCnpjLoja);
        } else {
            System.out.println("Histórico de pedidos da loja " + cpfCnpjLoja + ":");
            for (Object pedido : pedidos) {
                @SuppressWarnings("unchecked")
                Map<String, Object> venda = (Map<String, Object>) pedido;
                System.out.println("ID Venda: " + venda.get("id_venda"));
                System.out.println("Data: " + venda.get("data"));
                System.out.println("ID Comprador: " + venda.get("id_comprador"));
                System.out.println("Valor Total: R$" + venda.get("valor total"));
                System.out.println("Produtos: " + venda.get("produtos"));
                System.out.println("------------------------------");
            }
        }
    }
}
