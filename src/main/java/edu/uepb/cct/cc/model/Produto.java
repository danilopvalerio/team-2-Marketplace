package edu.uepb.cct.cc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import edu.uepb.cct.cc.controller.LojaController;

public class Produto {
    private String id; // ID do produto
    private String nome;
    private float valor;
    private String tipo;
    private int quantidade;
    private String marca;
    private String descricao;
    private String idLoja; // CNPJ ou CPF da loja
    private List<Integer> avaliacoes;
    private float mediaAvaliacoes;

    // Padrões para validação de cpf/cnpj
    private static final String PRODUCT_FIELD_PATTERN = "^[\\p{L}0-9\\s]+$";
    private static final Pattern CPF_PATTERN = Pattern.compile("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}");
    private static final Pattern CNPJ_PATTERN = Pattern.compile("\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}");
    /*
     * ^: Início da string.
     * [\\p{L}0-9\\s]: Define o conjunto de caracteres permitidos. Ele inclui:
     * \\p{L}: Qualquer letra (maiúscula ou minúscula) de qualquer idioma.
     * 0-9: Qualquer número de 0 a 9.
     * \\s: Qualquer caractere de espaço em branco (como espaço, tabulação, etc.).
     * +: Significa que deve haver pelo menos um caractere do conjunto permitido.
     * $: Fim da string.
     * \\d: caractere numérico
     */

    public Produto(String nome, float valor, String tipo, int quantidade, String marca, String descricao, String id,
            String idLoja) {
        setId(id);
        setNome(nome);
        setValor(valor);
        setTipo(tipo);
        setQuantidade(quantidade);
        setMarca(marca);
        setDescricao(descricao);
        setIdLoja(idLoja);
    }

    public Produto() {
    }

    public String getId() {
        return id;
    }

    public String setId(String id) {
        if (id == null) {
            throw new IllegalArgumentException("ID inválido.");
        }
        this.id = id;
        return this.id;
    }

    public String getIdLoja() {
        return idLoja;
    }

    public String setIdLoja(String idLoja) {
        if (idLoja == null || idLoja.trim().isEmpty()) {
            throw new IllegalArgumentException("ID da loja não pode ser vazio ou nulo.");
        }
        if (!validarCnpjOuCpf(idLoja)) {
            throw new IllegalArgumentException("ID da loja (CNPJ ou CPF) inválido.");
        }
        try {
            LojaController.getLojaPorCpfCnpj(idLoja);
            this.idLoja = idLoja;
            return this.idLoja;
        } catch (Exception e) {
            throw new IllegalArgumentException("Loja não encontrada para o CNPJ/CPF fornecido.");
        }

    }

    private boolean validarCnpjOuCpf(String idLoja) {
        return CPF_PATTERN.matcher(idLoja).matches() || CNPJ_PATTERN.matcher(idLoja).matches();
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
        if (!nome.matches(PRODUCT_FIELD_PATTERN)) {
            throw new IllegalArgumentException("Nome do produto contém caracteres inválidos.");
        }
        this.nome = nome;
    }

    public Float setValor(float valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor do produto deve ser maior que zero.");
        }
        this.valor = valor;
        return this.valor;
    }

    public String setTipo(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo não pode ser vazio ou nulo.");
        }
        if (!tipo.matches(PRODUCT_FIELD_PATTERN)) {
            throw new IllegalArgumentException("Tipo do produto contém caracteres inválidos.");
        }
        this.tipo = tipo;
        return this.tipo;
    }

    public int setQuantidade(int quantidade) {
        if (quantidade < 0) {
            throw new IllegalArgumentException("A quantidade não pode ser negativa.");
        }
        this.quantidade = quantidade;
        return this.quantidade;

    }

    public String setMarca(String marca) {
        if (marca == null || marca.trim().isEmpty()) {
            throw new IllegalArgumentException("Marca não pode ser vazia ou nula.");
        }
        if (!marca.matches(PRODUCT_FIELD_PATTERN)) {
            throw new IllegalArgumentException("Marca do produto contém caracteres inválidos.");
        }
        this.marca = marca;
        return this.marca;
    }

    public String setDescricao(String descricao) {
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição não pode ser vazia ou nula.");
        }
        this.descricao = descricao;
        return this.descricao;
    }

    @Override
    public String toString() {
        // Formatando o valor usando o locale dos EUA para garantir o ponto como
        // separador
        // decimal
        return "Produto: {" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", valor=" + String.format(Locale.US, "R$ %.2f", valor) +
                ", tipo='" + tipo + '\'' +
                ", quantidade=" + quantidade +
                ", marca='" + marca + '\'' +
                ", descricao='" + descricao + '\'' +
                ", idLoja='" + idLoja + '\'' +
                '}';
    }

    public void diminuirEstoque(int quantidade) {
        if (quantidade > this.quantidade) {
            throw new IllegalArgumentException("Estoque insuficiente para a quantidade solicitada.");
        }
        this.quantidade -= quantidade;
    }

    public List<Integer> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<Integer> avaliacoes) {
        if (avaliacoes == null) {
            this.avaliacoes = new ArrayList<>();
        } else {
            // valida todas as notas
            for (Integer nota : avaliacoes) {
                if (nota == null || nota < 1 || nota > 5) {
                    throw new IllegalArgumentException("Nota inválida na lista de avaliações: " + nota);
                }
            }
            this.avaliacoes = new ArrayList<>(avaliacoes);
        }
        atualizarMediaAvaliacoes();
    }

    public float getMediaAvaliacoes() {
        return mediaAvaliacoes;
    }

    public void setMediaAvaliacoes(float media_avaliacoes) {
        if (media_avaliacoes < 0.0f || media_avaliacoes > 5.0f) {
            throw new IllegalArgumentException("A média de avaliações deve estar entre 0.0 e 5.0.");
        }
        this.mediaAvaliacoes = media_avaliacoes;
    }

    // Método auxiliar para atualizar a média automaticamente
    private void atualizarMediaAvaliacoes() {
        if (avaliacoes.isEmpty()) {
            this.mediaAvaliacoes = 0.0f;
        } else {
            float soma = 0.0f;
            for (Integer nota : avaliacoes) {
                soma += nota;
            }
            this.mediaAvaliacoes = soma / avaliacoes.size();
        }
    }
}
