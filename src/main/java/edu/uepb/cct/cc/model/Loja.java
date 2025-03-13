package edu.uepb.cct.cc.model;
import java.util.regex.Pattern;

public class Loja {
    private String nome;
    private String email;
    private String senha;
    private String cpfCnpj;
    private String endereco;

    // Regex para validação de e-mail e CPF/CNPJ
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern CPF_PATTERN = 
        Pattern.compile("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}");
    private static final Pattern CNPJ_PATTERN = 
        Pattern.compile("\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}");

    public Loja(String nome, String email, String senha, String cpfCnpj, String endereco) {
        this.nome = nome;
        setEmail(email);
        this.senha = senha;
        setCpfCnpj(cpfCnpj);
        this.endereco = endereco;
    }

    public Loja() {
        
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("E-mail inválido");
        }
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        if (!CPF_PATTERN.matcher(cpfCnpj).matches() && !CNPJ_PATTERN.matcher(cpfCnpj).matches()) {
            throw new IllegalArgumentException("CPF ou CNPJ inválido");
        }
        this.cpfCnpj = cpfCnpj;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return "Loja {" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", cpfCnpj='" + cpfCnpj + '\'' +
                ", endereco='" + endereco + '\'' +
                '}';
    }
}
