/*
 * @test
 * @summary Test LocalDateTime.withNano(int nanoOfSecond) method for various inputs.
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.LocalTime;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testWithNanoZero();
        testWithNanoMax();
        testWithNanoMid();
        testWithNanoNegative();
        testWithNanoExceeding();
    }

    static void testWithNanoZero() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 3, 15, 12, 30, 45, 123456789);
        LocalDateTime result = dateTime.withNano(0);
        assert result.getNano() == 0 : "Expected nano to be 0";
        assert result.toLocalTime().getNano() == 0 : "Expected time nano to be 0";
        assert result.toLocalDate().equals(dateTime.toLocalDate()) : "Date should remain unchanged";
    }

    static void testWithNanoMax() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 3, 15, 12, 30, 45, 0);
        LocalDateTime result = dateTime.withNano(999999999);
        assert result.getNano() == 999999999 : "Expected nano to be 999999999";
        assert result.toLocalTime().getNano() == 999999999 : "Expected time nano to be 999999999";
        assert result.toLocalDate().equals(dateTime.toLocalDate()) : "Date should remain unchanged";
    }

    static void testWithNanoMid() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 3, 15, 12, 30, 45, 500000000);
        LocalDateTime result = dateTime.withNano(250000000);
        assert result.getNano() == 250000000 : "Expected nano to be 250000000";
        assert result.toLocalTime().getNano() == 250000000 : "Expected time nano to be 250000000";
        assert result.toLocalDate().equals(dateTime.toLocalDate()) : "Date should remain unchanged";
    }

    static void testWithNanoNegative() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 3, 15, 12, 30, 45, 123456789);
        try {
            dateTime.withNano(-1);
            assert false : "Expected exception for negative nano";
        } catch (DateTimeException e) {
            // Expected
        }
    }

    static void testWithNanoExceeding() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 3, 15, 12, 30, 45, 123456789);
        try {
            dateTime.withNano(1000000000);
            assert false : "Expected exception for nano exceeding max";
        } catch (DateTimeException e) {
            // Expected
        }
    }
}
