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
    private List<Venda> historicoDeVendas;
    private int score;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern CPF_PATTERN = Pattern.compile("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}");

    public Comprador() {
        this.carrinho = new ArrayList<>();
        this.historicoDeVendas = new ArrayList<>();
    }

    public Comprador(String nome, String email, String senha, String cpf, String endereco) {
        this();
        this.nome = nome;
        setEmail(email);
        setSenha(senha);
        setCpf(cpf);
        this.endereco = endereco;
        this.score = 0;
    }

    // Getters and setters
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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

    public void setCarrinho(List<ItemCarrinho> novoCarrinho) {
        this.carrinho = novoCarrinho;
    }

    public List<ItemCarrinho> getCarrinho() {
        return this.carrinho;
    }

    public List<ItemCarrinho> listarCarrinho() {
        return new ArrayList<>(carrinho);
    }

    public void adicionarAoCarrinho(Produto produto, int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }

        for (ItemCarrinho item : carrinho) {
            if (item.getProduto().equals(produto)) {
                item.aumentarQuantidade(quantidade);
                return;
            }
        }

        carrinho.add(new ItemCarrinho(produto, quantidade));
    }

    public void removerProdutoDoCarrinho(Produto produto) {
        boolean removido = carrinho.removeIf(item -> item.getProduto().getId().equals(produto.getId()));
        if (!removido) {
            throw new IllegalArgumentException("Produto não encontrado no carrinho.");
        }
    }

    public void atualizarQuantidadeProduto(Produto produto, int novaQuantidade) {
        for (ItemCarrinho item : carrinho) {
            if (item.getProduto().equals(produto)) {
                item.setQuantidade(novaQuantidade);
                return;
            }
        }
        throw new IllegalArgumentException("Produto não encontrado no carrinho.");
    }

    public void finalizarCompra(Venda venda) {
        if (carrinho.isEmpty()) {
            throw new IllegalStateException("Carrinho vazio. Não é possível finalizar a compra.");
        }

        historicoDeVendas.add(venda);
        carrinho.clear();
    }

    public void visualizarCarrinho() {
        if (carrinho.isEmpty()) {
            System.out.println("Seu carrinho está vazio.");
            return;
        }
        System.out.println("-------------------------------");
        System.out.println("CARRINHO:");
        System.out.println("-------------------------------");
        for (ItemCarrinho item : carrinho) {
            Produto produto = item.getProduto();
            System.out.println("Produto: " + produto.getNome());
            System.out.println("Marca: " + produto.getMarca());
            System.out.println("Preço: " + String.format("R$ %.2f", produto.getValor()));
            System.out.println("Quantidade: " + item.getQuantidade());
            System.out.println("Descrição: " + produto.getDescricao());
            System.out.println("ID do produto: " + produto.getId());
            System.out.println("-------------------------------");
        }
    }

    public static class ItemCarrinho {
        private Produto produto;
        private int quantidade;

        public ItemCarrinho() {
        }

        public ItemCarrinho(Produto produto, int quantidade) {
            this.produto = produto;
            this.quantidade = quantidade;
        }

        public Produto getProduto() {
            return produto;
        }

        public void setProduto(Produto produto) {
            this.produto = produto;
        }

        public int getQuantidade() {
            return quantidade;
        }

        public void setQuantidade(int quantidade) {
            this.quantidade = quantidade;
        }

        public void aumentarQuantidade(int qtd) {
            this.quantidade += qtd;
        }

        @Override
        public String toString() {
            return "ItemCarrinho{" +
                    "Produto: " + produto.getNome() +
                    ", Marca: " + produto.getMarca() +
                    ", Preço: " + String.format("R$ %.2f", produto.getValor()) +
                    ", Quantidade: " + quantidade +
                    '}';
        }
    }
}