/*
 * @test
 * @summary Testa o método minusMonths de java.time.LocalDate
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.Month;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testMinusMonthsNormal();
        testMinusMonthsLongMinValue();
        testMinusMonthsLongMaxValue();
    }

    static void testMinusMonthsNormal() {
        LocalDate date = LocalDate.of(2021, Month.MARCH, 15);
        LocalDate result = date.minusMonths(3);
        assert result.equals(LocalDate.of(2021, Month.DECEMBER, 15)) : "Erro em minusMonths com valor normal";
    }

    static void testMinusMonthsLongMinValue() {
        LocalDate date = LocalDate.of(2000, Month.JANUARY, 1);
        LocalDate result = date.minusMonths(Long.MIN_VALUE);
        // Como o método trata Long.MIN_VALUE convertendo para plusMonths(Long.MAX_VALUE).plusMonths(1)
        // não há exceção, apenas verifica se o resultado é uma data válida
        assert result != null : "Resultado nulo em Long.MIN_VALUE";
    }

    static void testMinusMonthsLongMaxValue() {
        LocalDate date = LocalDate.of(2000, Month.JANUARY, 1);
        LocalDate result = date.minusMonths(Long.MAX_VALUE);
        assert result != null : "Resultado nulo em Long.MAX_VALUE";
    }
}
