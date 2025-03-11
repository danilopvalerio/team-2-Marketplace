public class Produto {
    private String nome;
    private float valor;
    private String tipo;
    private int quantidade;
    private String marca;
    private String descricao;

    public Produto(String nome, float valor, String tipo, int quantidade, String marca, String descricao) {
        setNome(nome);
        setValor(valor);
        setTipo(tipo);
        setQuantidade(quantidade);
        setMarca(marca);
        setDescricao(descricao);
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
        validarString(nome, "Nome");
        this.nome = nome;
    }

    public void setValor(float valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor do produto deve ser maior que zero.");
        }
        this.valor = valor;
    }

    public void setTipo(String tipo) {
        validarString(tipo, "Tipo");
        this.tipo = tipo;
    }

    public void setQuantidade(int quantidade) {
        if (quantidade < 0) {
            throw new IllegalArgumentException("A quantidade não pode ser negativa.");
        }
        this.quantidade = quantidade;
    }

    public void setMarca(String marca) {
        validarString(marca, "Marca");
        this.marca = marca;
    }

    public void setDescricao(String descricao) {
        validarString(descricao, "Descrição");
        this.descricao = descricao;
    }

    private void validarString(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(campo + " não pode ser vazio ou nulo.");
        }
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
