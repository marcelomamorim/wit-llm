/*
 * @test
 * @summary Teste do método LocalDate.now(Clock clock) para verificar comportamento padrão e exceções.
 * @run main LocalDateWitupTest
 */

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testNowWithNullClock();
        testNowWithValidClock();
    }

    private static void testNowWithNullClock() {
        try {
            LocalDate.now(null);
            System.err.println("Erro: Esperado NullPointerException ao passar null como clock.");
        } catch (NullPointerException e) {
            System.out.println("Passou: NullPointerException ao passar null como clock.");
        }
    }

    private static void testNowWithValidClock() {
        Clock clock = Clock.systemDefaultZone();
        try {
            LocalDate date = LocalDate.now(clock);
            if (date != null) {
                System.out.println("Passou: Data obtida com clock válido: " + date);
            } else {
                System.err.println("Erro: Data retornada é null com clock válido.");
            }
        } catch (Exception e) {
            System.err.println("Erro inesperado ao usar clock válido: " + e);
        }
    }
}
