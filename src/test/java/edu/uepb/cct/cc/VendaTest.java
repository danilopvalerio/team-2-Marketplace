package edu.uepb.cct.cc;

import edu.uepb.cct.cc.model.Venda;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VendaTest {

    @Test
    void testCriacaoVendaValida() {
        List<String> idsProdutos = Arrays.asList("P1", "P2");
        List<Double> valores = Arrays.asList(10.0, 20.0);
        List<Integer> quantidades = Arrays.asList(2, 1);

        Venda venda = new Venda("V001", "C001", LocalDate.of(2024, 4, 7), idsProdutos, valores, quantidades);

        assertEquals("V001", venda.getIdVenda());
        assertEquals("C001", venda.getIdComprador());
        assertEquals(LocalDate.of(2024, 4, 7), venda.getDataVenda());
        assertEquals(40.0, venda.getValorTotal()); // 10*2 + 20*1
    }

    @Test
    void testIdVendaInvalido() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Venda("", "C001", LocalDate.now(), List.of("P1"), List.of(10.0), List.of(1));
        });
        assertEquals("O ID da venda não pode ser nulo ou vazio.", exception.getMessage());
    }

    @Test
    void testIdCompradorInvalido() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Venda("V001", "   ", LocalDate.now(), List.of("P1"), List.of(10.0), List.of(1));
        });
        assertEquals("O ID do comprador não pode ser nulo ou vazio.", exception.getMessage());
    }

    @Test
    void testDataVendaNula() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Venda("V001", "C001", null, List.of("P1"), List.of(10.0), List.of(1));
        });
        assertEquals("A data da venda não pode ser nula.", exception.getMessage());
    }

    @Test
    void testListasDeProdutosNulas() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Venda("V001", "C001", LocalDate.now(), null, null, null);
        });
        assertEquals("Os detalhes dos produtos não podem ser nulos.", exception.getMessage());
    }

    @Test
    void testListasDeProdutosComTamanhosDiferentes() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Venda("V001", "C001", LocalDate.now(), List.of("P1", "P2"), List.of(10.0), List.of(1));
        });
        assertEquals("Os tamanhos das listas de produtos devem ser iguais.", exception.getMessage());
    }

    @Test
    void testValorUnitarioNegativo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Venda("V001", "C001", LocalDate.now(), List.of("P1"), List.of(-5.0), List.of(1));
        });
        assertEquals("Os valores dos produtos devem ser positivos.", exception.getMessage());
    }

    @Test
    void testQuantidadeInvalida() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Venda("V001", "C001", LocalDate.now(), List.of("P1"), List.of(10.0), List.of(0));
        });
        assertEquals("Os valores dos produtos devem ser positivos.", exception.getMessage());
    }

    @Test
    void testToStringFormatado() {
        List<String> idsProdutos = Collections.singletonList("P1");
        List<Double> valores = Collections.singletonList(50.0);
        List<Integer> quantidades = Collections.singletonList(2);

        Venda venda = new Venda("V123", "C321", LocalDate.of(2024, 4, 7), idsProdutos, valores, quantidades);

        String expected = """
                🧾 ID Venda: V123
                👤 Comprador: C321
                🗓️ Data: 2024-04-07
                📦 Produtos vendidos:
                   🔹 Produto: P1 | Quantidade: 2 | Valor unitário: R$50.0
                💰 Total: R$100.0
                """;

        assertEquals(expected.strip(), venda.toString().strip());
    }
}
