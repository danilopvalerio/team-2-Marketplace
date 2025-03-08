import java.util.Scanner;

public class FachadaLoja {
    public static Loja criarLoja(String nome, String email, String senha, String cpfCnpj, String endereco) {
        try {
            return new Loja(nome, email, senha, cpfCnpj, endereco);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao criar loja: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        
        System.out.print("E-mail: ");
        String email = scanner.nextLine();
        
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        
        System.out.print("CPF/CNPJ: ");
        String cpfCnpj = scanner.nextLine();
        
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        
        Loja loja = criarLoja(nome, email, senha, cpfCnpj, endereco);
        
        if (loja != null) {
            System.out.println("Loja criada com sucesso: " + loja.toString());
        }
        
        scanner.close();
    }
}
