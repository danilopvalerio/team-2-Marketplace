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
    private List<ItemCarrinho> carrinho; // Carrinho de compras
    private List<Venda> historicoDeVendas;

    // Padrões para validação de e-mail e CPF
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

    // Getters and setters
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

    // Método para adicionar um produto ao carrinho
    public void adicionarAoCarrinho(Produto produto, int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }

        for (ItemCarrinho item : carrinho) {
            if (item.getProduto().equals(produto)) {
                item.aumentarQuantidade(quantidade);  // Aumenta a quantidade se o produto já estiver no carrinho
                System.out.println("Produto atualizado no carrinho.");
                return;
            }
        }

        // Se o produto não estiver no carrinho, adiciona um novo item
        carrinho.add(new ItemCarrinho(produto, quantidade));
        System.out.println("Produto adicionado ao carrinho.");
    }

    // Método para remover um produto do carrinho
    public void removerProdutoDoCarrinho(Produto produto) {
        boolean removido = carrinho.removeIf(item -> item.getProduto().getId().equals(produto.getId()));

        if (removido) {
            System.out.println("Produto removido do carrinho com sucesso.");
            // Atualiza o carrinho no controlador após a remoção
            CompradorController.atualizarCarrinhoDoComprador(this.getCpf(), this.listarCarrinho());
        } else {
            System.err.println("Erro: Produto não encontrado no carrinho.");
        }
    }

    // Método para atualizar a quantidade de um produto no carrinho
    public void atualizarQuantidadeProduto(Produto produto, int novaQuantidade) {
        for (ItemCarrinho item : carrinho) {
            if (item.getProduto().equals(produto)) {
                item.setQuantidade(novaQuantidade);
                System.out.println("Quantidade do produto no carrinho atualizada.");
                return;
            }
        }
        System.err.println("Produto não encontrado no carrinho.");
    }

    // Método para listar os itens do carrinho
    public List<ItemCarrinho> listarCarrinho() {
        return new ArrayList<>(carrinho);
    }

    // Método para finalizar a compra
    public void finalizarCompra() {
        if (carrinho.isEmpty()) {
            System.out.println("Seu carrinho está vazio! Não é possível finalizar a compra.");
            return;
        }

        double totalCompra = 0;
        for (ItemCarrinho item : carrinho) {
            totalCompra += item.getProduto().getValor() * item.getQuantidade();
        }

        // Atualiza o estoque dos produtos
        for (ItemCarrinho item : carrinho) {
            item.getProduto().diminuirEstoque(item.getQuantidade());
        }

        System.out.println("Compra finalizada com sucesso!");
        System.out.println("Total: R$ " + totalCompra);
        carrinho.clear();  // Limpa o carrinho após a compra
    }

    public void visualizarCarrinho() {
        if (carrinho.isEmpty()) {
            System.out.println("Seu carrinho está vazio.");
            return;
        }

        System.out.println("Produtos no carrinho:");
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
