package edu.uepb.cct.cc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Comprador {
    private String nome;
    private String email;
    private String senha;
    private String cpf;
    private String endereco;
    private List<ItemCarrinho> carrinho;

    // Regex para validação de e-mail e CPF
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern CPF_PATTERN =
            Pattern.compile("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}");

    public Comprador() {
        this.carrinho = new ArrayList<>();
    }

    public Comprador(String nome, String email, String senha, String cpf, String endereco) {
        this.nome = nome;
        setEmail(email);
        setSenha(senha);
        setCpf(cpf);
        this.endereco = endereco;
        this.carrinho = new ArrayList<>();
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
        if (senha.length() < 6) {
            throw new IllegalArgumentException("A senha deve ter pelo menos 6 caracteres");
        }
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if (!CPF_PATTERN.matcher(cpf).matches()) {
            throw new IllegalArgumentException("CPF inválido");
        }
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void adicionarProdutoAoCarrinho(Produto produto, int quantidade) {
        for (ItemCarrinho item : carrinho) {
            if (item.getProduto().equals(produto)) {
                item.setQuantidade(item.getQuantidade() + quantidade);
                return;
            }
        }
        carrinho.add(new ItemCarrinho(produto, quantidade));
    }

    public void removerProdutoDoCarrinho(Produto produto) {
        carrinho.removeIf(item -> item.getProduto().equals(produto));
    }

    public void atualizarQuantidadeProduto(Produto produto, int novaQuantidade) {
        for (ItemCarrinho item : carrinho) {
            if (item.getProduto().equals(produto)) {
                item.setQuantidade(novaQuantidade);
                return;
            }
        }
    }

    public List<ItemCarrinho> listarCarrinho() {
        return new ArrayList<>(carrinho);
    }

    @Override
    public String toString() {
        return "Comprador {" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", cpf='" + cpf + '\'' +
                ", endereco='" + endereco + '\'' +
                ", carrinho=" + carrinho +
                '}';
    }

    // Classe interna ItemCarrinho
    private static class ItemCarrinho {
        private Produto produto;
        private int quantidade;

        public ItemCarrinho(Produto produto, int quantidade) {
            this.produto = produto;
            this.quantidade = quantidade;
        }

        public Produto getProduto() {
            return produto;
        }

        public int getQuantidade() {
            return quantidade;
        }

        public void setQuantidade(int quantidade) {
            this.quantidade = quantidade;
        }

        @Override
        public String toString() {
            return "ItemCarrinho{" +
                    "produto=" + produto +
                    ", quantidade=" + quantidade +
                    '}';
        }
    }
}