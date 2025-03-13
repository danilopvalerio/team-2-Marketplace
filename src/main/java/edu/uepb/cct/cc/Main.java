package edu.uepb.cct.cc;

import edu.uepb.cct.cc.controller.LojaController;
import edu.uepb.cct.cc.model.Loja;

public class Main {
    public static void main(String[] args) {
        // Criação de uma nova loja com 5 parâmetros, incluindo a senha
        Loja loja1 = new Loja("Loja Exemplo4", "loja@exemplo2.com", "senha123", "11.345.678/0001-94",
                "Rua Exemplo, 123");

        // Adiciona a loja ao arquivo JSON
        LojaController.create(loja1);
    }
}
