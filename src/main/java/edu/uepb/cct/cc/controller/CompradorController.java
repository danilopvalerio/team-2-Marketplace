package edu.uepb.cct.cc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import edu.uepb.cct.cc.model.Comprador;
import edu.uepb.cct.cc.services.SecurityHash;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public class CompradorController extends Comprador {
    private static final String ARQUIVO_COMPRADORES = "src/main/resources/data/compradores.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = Logger.getLogger(CompradorController.class.getName());

    public static void create(Comprador comprador) {
        // Validações
        validarNome(comprador.getNome());
        validarEmail(comprador.getEmail());
        validarSenha(comprador.getSenha());
        validarCpf(comprador.getCpf());
        validarEndereco(comprador.getEndereco());

        comprador.setSenha(SecurityHash.hashPassword(comprador.getSenha()));
        
        List<Comprador> compradores = new ArrayList<>();
        File file = new File(ARQUIVO_COMPRADORES);

        // Verifica e cria diretório, se necessário
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        // Carrega os compradores existentes, se houver
        if (file.exists() && file.length() > 0) {
            try (Reader reader = new FileReader(file)) {
                compradores = objectMapper.readValue(reader,
                        TypeFactory.defaultInstance().constructCollectionType(List.class, Comprador.class));
            } catch (IOException e) {
                logger.severe("Erro ao ler o arquivo de compradores: " + e.getMessage());
            }
        }

        // Verifica se o comprador já existe
        for (Comprador c : compradores) {
            if (c.getCpf().equals(comprador.getCpf())) {
                throw new IllegalArgumentException("Não foi possível adicionar o comprador pois ele já está cadastrado no sistema.");
            }
        }

        // Adiciona o novo comprador à lista
        compradores.add(comprador);

        // Salva a lista atualizada de compradores no arquivo
        try (Writer writer = new FileWriter(file)) {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(writer, compradores);
            System.out.println("Comprador adicionado com sucesso.");
        } catch (IOException e) {
            logger.severe("Erro ao salvar o comprador: " + e.getMessage());
        }
    }

    public static List<Comprador> getTodosCompradores() {
        File file = new File(ARQUIVO_COMPRADORES);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }
        try (Reader reader = new FileReader(file)) {
            List<Comprador> compradores = objectMapper.readValue(reader,
                    TypeFactory.defaultInstance().constructCollectionType(List.class, Comprador.class));
            compradores.sort(Comparator.comparing(Comprador::getNome, String.CASE_INSENSITIVE_ORDER));
            return compradores;
        } catch (IOException e) {
            logger.severe("Erro ao carregar compradores: " + e.getMessage());
            throw new RuntimeException("Erro ao carregar compradores.", e);
        }
    }

    public static Comprador getCompradorPorCpf(String cpf) {
        if (cpf == null || cpf.isEmpty()) {
            throw new IllegalArgumentException("CPF inválido.");
        }
        List<Comprador> compradores = getTodosCompradores();
        for (Comprador comprador : compradores) {
            if (comprador.getCpf().equals(cpf)) {
                return comprador;
            }
        }
        return null;
    }

    public static String deleteCompradorPorCpf(String cpf) {
        if (cpf == null || cpf.isEmpty()) {
            return "CPF inválido.";
        }
        List<Comprador> compradores = getTodosCompradores();
        boolean removido = compradores.removeIf(comprador -> comprador.getCpf().equals(cpf));

        if (!removido) {
            return "Comprador não encontrado.";
        }

        try (Writer writer = new FileWriter(ARQUIVO_COMPRADORES)) {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(writer, compradores);
            return "Comprador removido com sucesso.";
        } catch (IOException e) {
            logger.severe("Erro ao salvar compradores após remoção: " + e.getMessage());
            return "Erro ao remover comprador.";
        }
    }

    public static Comprador atualizarComprador(String cpf, Comprador compradorAtualizado) {
        if (cpf == null || cpf.isEmpty() || compradorAtualizado == null) {
            throw new IllegalArgumentException("CPF ou dados inválidos.");
        }
        List<Comprador> compradores = getTodosCompradores();
        boolean atualizado = false;

        for (int i = 0; i < compradores.size(); i++) {
            if (compradores.get(i).getCpf().equals(cpf)) {
                compradorAtualizado.setCpf(cpf); // Mantém o CPF original
                compradores.set(i, compradorAtualizado);
                atualizado = true;
                break;
            }
        }

        if (!atualizado) {
            throw new IllegalArgumentException("Comprador não encontrado.");
        }

        try (Writer writer = new FileWriter(ARQUIVO_COMPRADORES)) {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(writer, compradores);
            return compradorAtualizado;
        } catch (IOException e) {
            logger.severe("Erro ao salvar compradores após atualização: " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar comprador.", e);
        }
    }

    // Validação de Nome
    private static void validarNome(String nome) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }
    }

    public static void atualizarCarrinhoDoComprador(String cpf, List<?> novoCarrinho) {
        List<Comprador> compradores = getTodosCompradores();

        for (Comprador c : compradores) {
            if (c.getCpf().equals(cpf)) {
                // Atualiza o carrinho
                try {
                    java.lang.reflect.Field field = Comprador.class.getDeclaredField("carrinho");
                    field.setAccessible(true);
                    field.set(c, novoCarrinho);
                } catch (Exception e) {
                    throw new RuntimeException("Erro ao atualizar carrinho via reflexão: " + e.getMessage());
                }
                break;
            }
        }

        try (Writer writer = new FileWriter(ARQUIVO_COMPRADORES)) {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(writer, compradores);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar carrinho atualizado: " + e.getMessage());
        }
    }


    // Validação de E-mail
    private static void validarEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("E-mail não pode ser vazio.");
        }
        // Adicionar validação de formato de e-mail se necessário, por exemplo, usando regex.
    }

    // Validação de Senha
    private static void validarSenha(String senha) {
        if (senha == null || senha.length() < 6) {
            throw new IllegalArgumentException("A senha deve ter no mínimo 6 caracteres.");
        }
    }

    // Validação de CPF
    private static void validarCpf(String cpf) {
        if (cpf == null || cpf.isEmpty() || !cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
            throw new IllegalArgumentException("CPF inválido. O formato deve ser XXX.XXX.XXX-XX.");
        }
    }

    // Validação de Endereço
    private static void validarEndereco(String endereco) {
        if (endereco == null || endereco.isEmpty()) {
            throw new IllegalArgumentException("Endereço não pode ser vazio.");
        }
    }
}
