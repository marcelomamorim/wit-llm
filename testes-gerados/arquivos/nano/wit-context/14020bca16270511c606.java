/*
 * @test
 * @summary Teste do método getMonth() de java.time.LocalDate
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.Month;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testGetMonthValid();
        testGetMonthInvalid();
    }

    private static void testGetMonthValid() {
        // Teste com mês válido
        LocalDate date = LocalDate.of(2023, 5, 15);
        Month month = date.getMonth();
        if (month != Month.MAY) {
            throw new AssertionError("Expected Month.MAY, got " + month);
        }
    }

    private static void testGetMonthInvalid() {
        // Teste com mês inválido (não possível criar LocalDate válido, então simula comportamento)
        // Como LocalDate.of lança DateTimeException para mês inválido, testar essa exceção
        try {
            LocalDate.of(2023, 13, 1);
            throw new AssertionError("Expected DateTimeException for invalid month");
        } catch (java.time.DateTimeException e) {
            // esperado
        }
    }
}
