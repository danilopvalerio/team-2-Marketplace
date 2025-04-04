package edu.uepb.cct.cc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import edu.uepb.cct.cc.controller.CompradorController;


public class Comprador {
    private String nome;
    private String email;
    private String senha;
    private String cpf;
    private String endereco;
    private List<ItemCarrinho> carrinho;
    private List<Venda> historicoDeVendas;

    // Adicionar get carrinho, setItemCarrinho

    // Regex para validação de e-mail e CPF
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern CPF_PATTERN = Pattern.compile("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}");

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

    public boolean removerDoHistoricoDeVendas(Venda venda) {
        return this.historicoDeVendas.remove(venda);
    }

    public void setHistoricoDeVendas(List<Venda> historicoDeVendas) {
        this.historicoDeVendas = historicoDeVendas;
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

    public void adicionarAoCarrinho(Produto produto, int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }

        for (ItemCarrinho item : carrinho) {
            if (item.getProduto().equals(produto)) {
                item.setQuantidade(item.getQuantidade() + quantidade);
                return;
            }
        }

        carrinho.add(new ItemCarrinho(produto, quantidade));
    }

    public void removerProdutoDoCarrinho(Produto produto) {
        boolean removido = carrinho.removeIf(item -> item.getProduto().getId().equals(produto.getId()));

        if (removido) {
            System.out.println("Produto removido do carrinho com sucesso.");
            // Atualiza o JSON
            CompradorController.atualizarCarrinhoDoComprador(this.getCpf(), this.listarCarrinho());
        } else {
            System.err.println("Erro: Produto não encontrado no carrinho.");
        }
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
    public static class ItemCarrinho {
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