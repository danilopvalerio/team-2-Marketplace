package edu.uepb.cct.cc;

import edu.uepb.cct.cc.controller.CompradorController;
import edu.uepb.cct.cc.controller.LojaController;
import edu.uepb.cct.cc.controller.ProdutoController;
import edu.uepb.cct.cc.controller.VendaController;
import edu.uepb.cct.cc.model.Comprador;
import edu.uepb.cct.cc.model.Produto;
import edu.uepb.cct.cc.model.Venda;
import edu.uepb.cct.cc.view.ProdutoView;
import edu.uepb.cct.cc.view.VendaView;

import java.io.*;
import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MainTemp {

    public static void main(String[] args) {
        List<String> idsProdutos = Arrays.asList("BEGEJ", "P001");
        List<Double> valores = Arrays.asList(10.0, 20.0);
        List<Integer> quantidades = Arrays.asList(2, 1);

        Venda venda = new Venda("YPYY", "222.222.222-22", LocalDate.of(2024, 4, 7), idsProdutos, valores, quantidades);
        VendaController vendaController = new VendaController();
        // vendaController.registrarVenda(venda);
        for (String item : venda.getIdsProdutosVendidos()) {
            Produto produto = ProdutoController.getProdutoPorIDOBJ(item);
            if (produto != null) {
                String cpfCnpjLoja = produto.getIdLoja();
                System.out.println(cpfCnpjLoja + "-" + venda.getIdVenda());
                LojaController.setIdVendaHistoricoLoja(cpfCnpjLoja, venda.getIdVenda());
            }
        }

    }
}
