/*
 * @test
 * @summary Test LocalDateTime.withNano(int) for valid and invalid inputs.
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.DateTimeException;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testWithNanoValid();
        testWithNanoInvalidNegative();
        testWithNanoInvalidTooLarge();
        testWithNanoNoChange();
    }

    static void testWithNanoValid() {
        LocalDateTime original = LocalDateTime.of(2023, 3, 15, 12, 30, 45, 123456789);
        int nano = 987654321;
        LocalDateTime result = original.withNano(nano);
        assert result.getNano() == nano : "Expected nano to be " + nano;
        assert result.toLocalTime().getNano() == nano : "Time nano mismatch";
        assert result.toLocalDate().equals(original.toLocalDate()) : "Date should remain unchanged";
    }

    static void testWithNanoInvalidNegative() {
        int invalidNano = -1;
        LocalDateTime dt = LocalDateTime.of(2023, 3, 15, 12, 30);
        try {
            dt.withNano(invalidNano);
            assert false : "Expected DateTimeException for negative nano";
        } catch (DateTimeException e) {
            // expected
        }
    }

    static void testWithNanoInvalidTooLarge() {
        int invalidNano = 1000000000; // 1 billion, invalid as max is 999,999,999
        LocalDateTime dt = LocalDateTime.of(2023, 3, 15, 12, 30);
        try {
            dt.withNano(invalidNano);
            assert false : "Expected DateTimeException for too large nano";
        } catch (DateTimeException e) {
            // expected
        }
    }

    static void testWithNanoNoChange() {
        LocalDateTime dt = LocalDateTime.of(2023, 3, 15, 12, 30, 45, 123456789);
        int nano = dt.getNano();
        LocalDateTime result = dt.withNano(nano);
        assert result.equals(dt) : "Result should be equal to original when nano is unchanged";
    }
}
