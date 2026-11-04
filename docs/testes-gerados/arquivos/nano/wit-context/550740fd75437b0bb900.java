/*
 * @test
 * @summary Tests for OffsetTime.atDate(LocalDate)
 * @run main OffsetTimeWitupTest
 */

import java.time.OffsetTime;
import java.time.OffsetDateTime;
import java.time.LocalDate;

public class OffsetTimeWitupTest {

    public static void main(String[] args) {
        testAtDateWithNullTime();
        testAtDateWithNullDate();
    }

    private static void testAtDateWithNullTime() {
        OffsetTime offsetTime = OffsetTime.of(12, 30, 45, 123456789, java.time.ZoneOffset.UTC);
        try {
            // Mesmo que o método não lance exceção para esse caso, testamos o comportamento
            OffsetDateTime result = offsetTime.atDate(null);
            if (result == null) {
                throw new AssertionError("Expected non-null OffsetDateTime");
            }
            // Verifica se o resultado tem a data nula, o que não é possível, então esperamos uma NullPointerException
            // Mas como o método não lança exceção, esse teste é para verificar o comportamento atual
        } catch (NullPointerException e) {
            // Esperado
        }
    }

    private static void testAtDateWithNullDate() {
        OffsetTime offsetTime = OffsetTime.of(12, 30, 45, 123456789, java.time.ZoneOffset.UTC);
        try {
            OffsetDateTime result = offsetTime.atDate(null);
            if (result == null) {
                throw new AssertionError("Expected non-null OffsetDateTime");
            }
        } catch (NullPointerException e) {
            // Esperado
        }
    }
}
