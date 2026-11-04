/*
 * @test
 * @summary Witup-generated checks for LocalTime.withNano(int)
 * @run main LocalTimeWitupTest
 */
import java.time.DateTimeException;
import java.time.LocalTime;

public class LocalTimeWitupTest {
    public static void main(String[] args) {
        testNegativeNanoThrows();
        testTooLargeNanoThrows();
        testBoundaryValidNano();
        testSameNanoReturnsSameInstance();
        testChangesOnlyNanoOfSecond();
        testZeroNanoEqualsTruncatedTime();
    }

    static void testNegativeNanoThrows() {
        LocalTime t = LocalTime.of(10, 20, 30, 40);
        expectDateTimeException(t, -1);
    }

    static void testTooLargeNanoThrows() {
        LocalTime t = LocalTime.of(10, 20, 30, 40);
        expectDateTimeException(t, 1000000000);
    }

    static void testBoundaryValidNano() {
        LocalTime t = LocalTime.of(0, 0);
        LocalTime r = t.withNano(999999999);
        if (r.getNano() != 999999999) {
            throw new RuntimeException("Expected nano 999999999 but got " + r.getNano());
        }
    }

    static void testSameNanoReturnsSameInstance() {
        LocalTime t = LocalTime.of(10, 20, 30, 40);
        if (t.withNano(40) != t) {
            throw new RuntimeException("withNano with same nano should return this");
        }
    }

    static void testChangesOnlyNanoOfSecond() {
        LocalTime t = LocalTime.of(23, 59, 58, 1);
        LocalTime r = t.withNano(2);
        if (r.getHour() != 23 || r.getMinute() != 59 || r.getSecond() != 58 || r.getNano() != 2) {
            throw new RuntimeException("Unexpected result: " + r);
        }
    }

    static void testZeroNanoEqualsTruncatedTime() {
        LocalTime t = LocalTime.of(0, 0, 0, 1);
        LocalTime r = t.withNano(0);
        if (!r.equals(LocalTime.of(0, 0))) {
            throw new RuntimeException("Expected 00:00 but got " + r);
        }
    }

    static void expectDateTimeException(LocalTime t, int nano) {
        try {
            t.withNano(nano);
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException("Expected DateTimeException for nano=" + nano);
    }
}
