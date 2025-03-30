package edu.uepb.cct.cc;

import edu.uepb.cct.cc.view.MenuRegistro;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        MenuRegistro.menuInicial(scanner);
        scanner.close();
    }
}
