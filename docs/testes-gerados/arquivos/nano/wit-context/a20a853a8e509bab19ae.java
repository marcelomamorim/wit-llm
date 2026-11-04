/*
 * @test
 * @summary Test for LocalDateTime.minusNanos(long nanos)
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.DateTimeException;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testMinusNanosWithValidInput();
        testMinusNanosWithLargeNegativeInput();
        testMinusNanosWithZero();
        testMinusNanosWithLargePositiveInput();
    }

    private static void testMinusNanosWithValidInput() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 3, 15, 12, 30, 45);
        long nanosToSubtract = 5000000000L; // 5 seconds
        LocalDateTime result = dateTime.minusNanos(nanosToSubtract);
        assert result != null : "Result should not be null";
        // Verifica se o resultado é diferente do original e esperado
        if (result.equals(dateTime)) {
            throw new AssertionError("minusNanos did not subtract as expected");
        }
    }

    private static void testMinusNanosWithLargeNegativeInput() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 3, 15, 12, 30, 45);
        long nanosToSubtract = Long.MAX_VALUE; // valor muito grande
        try {
            dateTime.minusNanos(nanosToSubtract);
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected DateTimeException for large negative input");
        } catch (DateTimeException e) {
            // Esperado
        }
    }

    private static void testMinusNanosWithZero() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 3, 15, 12, 30, 45);
        LocalDateTime result = dateTime.minusNanos(0);
        if (!result.equals(dateTime)) {
            throw new AssertionError("minusNanos(0) should return the same object");
        }
    }

    private static void testMinusNanosWithLargePositiveInput() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 3, 15, 12, 30, 45);
        long nanosToSubtract = -Long.MAX_VALUE; // valor negativo grande
        try {
            dateTime.minusNanos(nanosToSubtract);
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected DateTimeException for large positive input");
        } catch (DateTimeException e) {
            // Esperado
        }
    }
}
