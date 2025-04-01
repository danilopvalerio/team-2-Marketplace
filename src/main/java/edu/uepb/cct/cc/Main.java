package edu.uepb.cct.cc;

import java.util.Scanner;

import edu.uepb.cct.cc.services.MenuRegistro;

public class Main extends MenuRegistro {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        MenuRegistro.menuInicial(scanner);
        scanner.close();
    }
}
