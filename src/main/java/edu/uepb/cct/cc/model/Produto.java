package edu.uepb.cct.cc.model;

public class Produto {
    private String nome;
    private float valor;
    private String tipo;
    private int quantidade;
    private String marca;
    private String descricao;

    private static final String DATA_PATTERN = "^[a-zA-Z0-9\\s]+$"; 

    public Produto(String nome, float valor, String tipo, int quantidade, String marca, String descricao) {
        setNome(nome);
        setValor(valor);
        setTipo(tipo);
        setQuantidade(quantidade);
        setMarca(marca);
        setDescricao(descricao);
    }

    public Produto() {
    }

    public String getNome() {
        return nome;
    }

    public float getValor() {
        return valor;
    }

    public String getTipo() {
        return tipo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public String getMarca() {
        return marca;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio ou nulo.");
        }
        if (!nome.matches(DATA_PATTERN)) {
            throw new IllegalArgumentException("Nome do produto contém caracteres inválidos.");
        }
        this.nome = nome;
    }

    public void setValor(float valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor do produto deve ser maior que zero.");
        }
        this.valor = valor;
    }

    public void setTipo(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo não pode ser vazio ou nulo.");
        }
        if (!tipo.matches(DATA_PATTERN)) {
            throw new IllegalArgumentException("Tipo do produto contém caracteres inválidos.");
        }
        this.tipo = tipo;
    }

    public void setQuantidade(int quantidade) {
        if (quantidade < 0) {
            throw new IllegalArgumentException("A quantidade não pode ser negativa.");
        }
        this.quantidade = quantidade;
    }

    public void setMarca(String marca) {
        if (marca == null || marca.trim().isEmpty()) {
            throw new IllegalArgumentException("Marca não pode ser vazia ou nula.");
        }
        if (!marca.matches(DATA_PATTERN)) {
            throw new IllegalArgumentException("Marca do produto contém caracteres inválidos.");
        }
        this.marca = marca;
    }

    public void setDescricao(String descricao) {
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição não pode ser vazia ou nula.");
        }
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Produto: {" +
                "nome='" + nome + '\'' +
                ", valor=" + String.format("R$ %.2f", valor) +
                ", tipo='" + tipo + '\'' +
                ", quantidade=" + quantidade +
                ", marca='" + marca + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
