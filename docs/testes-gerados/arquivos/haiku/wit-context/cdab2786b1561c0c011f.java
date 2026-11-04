/*
 * @test
 * @summary WITUP-derived checks for OffsetTime.of(LocalTime, ZoneOffset) null handling and basic behavior
 * @run main OffsetTimeWitupTest
 */

import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;

public class OffsetTimeWitupTest {

    public static void main(String[] args) {
        LocalTime t = LocalTime.of(10, 15, 30, 123);
        ZoneOffset z = ZoneOffset.ofHours(2);

        OffsetTime ot = OffsetTime.of(t, z);
        check(ot.toLocalTime().equals(t), "toLocalTime should match the input time");
        check(ot.getOffset().equals(z), "getOffset should match the input offset");

        OffsetTime expected = OffsetTime.of(10, 15, 30, 123, z);
        check(ot.equals(expected), "of(LocalTime, ZoneOffset) should equal the field-based factory");

        expectNPE(() -> OffsetTime.of(null, z), "null time must throw NullPointerException");
        expectNPE(() -> OffsetTime.of(t, null), "null offset must throw NullPointerException");
        expectNPE(() -> OffsetTime.of(null, null), "null time and offset must throw NullPointerException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private static void expectNPE(Runnable action, String message) {
        try {
            action.run();
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException(message);
    }
}
