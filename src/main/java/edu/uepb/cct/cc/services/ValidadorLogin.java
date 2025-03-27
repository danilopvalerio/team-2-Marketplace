package edu.uepb.cct.cc.services;

import edu.uepb.cct.cc.controller.*;
import edu.uepb.cct.cc.model.*;

public class ValidadorLogin {

    public static boolean loginLoja(String id, String senha){
        try {
            Loja loja = LojaController.getLojaPorCpfCnpj(id);
            return loja != null && loja.getSenha().equals(senha);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
            return false;
        }
    }

    public static boolean loginComprador(String id, String senha){
        try {
            Comprador comprador = CompradorController.getCompradorPorCpf(id);
            return comprador != null && comprador.getSenha().equals(senha);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
            return false;
        }
    }

    public static boolean loginADM(String id, String senha){
        try {
            return "biscoito".equals(id) && "YTB-8@".equals(senha);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
            return false;
        }
    }
}
