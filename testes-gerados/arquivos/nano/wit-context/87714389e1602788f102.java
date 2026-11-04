/*
 * @test
 * @summary Teste do método getMonth() de java.time.YearMonth
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.Month;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        testGetMonthValid();
        testGetMonthInvalid();
    }

    static void testGetMonthValid() {
        YearMonth ym = YearMonth.of(2023, 5);
        Month m = ym.getMonth();
        if (m != null && m == Month.MAY) {
            System.out.println("testGetMonthValid passed");
        } else {
            System.err.println("testGetMonthValid failed");
        }
    }

    static void testGetMonthInvalid() {
        try {
            YearMonth ym = YearMonth.of(2023, 13); // inválido, deve lançar exceção
            System.err.println("testGetMonthInvalid failed: não lançou");
        } catch (Exception e) {
            if (e instanceof java.time.DateTimeException) {
                System.out.println("testGetMonthInvalid passed");
            } else {
                System.err.println("testGetMonthInvalid failed: exceção inesperada");
            }
        }
    }
}
