package edu.uepb.cct.cc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Loja {
    private String nome;
    private String email;
    private String senha;
    private String cpfCnpj;
    private String endereco;
    private List<String> historicoVendas = new ArrayList<>();

    // Regex para validação de e-mail e CPF/CNPJ
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern CPF_PATTERN = Pattern.compile("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}"); // o "\d" é para
                                                                                                   // aceitar apenas
                                                                                                   // digitos numéricos
    private static final Pattern CNPJ_PATTERN = Pattern.compile("\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}");

    public Loja(String nome, String email, String senha, String cpfCnpj, String endereco) {
        this.nome = nome;
        setEmail(email);
        this.senha = senha;
        setCpfCnpj(cpfCnpj);
        this.endereco = endereco;
    }

    public Loja() {
    }

    public List<String> getHistoricoVendas() {
        return historicoVendas;
    }

    public void adicionarVendaAoHistorico(String vendaId) {
        if (vendaId == null || vendaId.isEmpty()) {
            throw new IllegalArgumentException("ID da venda inválido.");
        }
        historicoVendas.add(vendaId);
    }

    public String getNome() {
        return nome;
    }

    public Loja setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Loja setEmail(String email) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("E-mail inválido");
        }
        this.email = email;
        return this;
    }

    public String getSenha() {
        return senha;
    }

    public Loja setSenha(String senha) {
        this.senha = senha;
        return this;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public Loja setCpfCnpj(String cpfCnpj) {
        if (!CPF_PATTERN.matcher(cpfCnpj).matches() && !CNPJ_PATTERN.matcher(cpfCnpj).matches()) {
            throw new IllegalArgumentException("CPF ou CNPJ inválido");
        }
        this.cpfCnpj = cpfCnpj;
        return this;
    }

    public String getEndereco() {
        return endereco;
    }

    public Loja setEndereco(String endereco) {
        this.endereco = endereco;
        return this;
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
