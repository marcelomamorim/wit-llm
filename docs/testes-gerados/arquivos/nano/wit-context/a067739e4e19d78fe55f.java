/* @test    @summary Testa o método static now(Clock clock) de java.time.MonthDay com entrada nula para verificar NullPointerException.    @run main MonthDayWitupTest */

import java.time.Clock;
import java.time.MonthDay;

public class MonthDayWitupTest {

    public static void main(String[] args) {
        testNowWithNullClock();
    }

    public static void testNowWithNullClock() {
        try {
            MonthDay.now(null);
            System.err.println("Erro: Esperado NullPointerException não foi lançada.");
            System.exit(1);
        } catch (NullPointerException e) {
            // Teste passou, exceção esperada
            System.out.println("Passou: NullPointerException lançada como esperado.");
        } catch (Throwable t) {
            System.err.println("Erro: Exceção inesperada: " + t);
            System.exit(1);
        }
    }
}
