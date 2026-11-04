/*
 * @test
 * @summary WITUP-derived checks for Clock.system(ZoneId): null zone must throw NullPointerException and UTC zone must yield a clock in UTC
 * @run main ClockWitupTest
 */

import java.time.Clock;
import java.time.ZoneOffset;

public class ClockWitupTest {

    public static void main(String[] args) {
        testNullZoneThrowsNullPointerException();
        testUtcZoneReturnsClockInUtc();
    }

    static void testNullZoneThrowsNullPointerException() {
        try {
            Clock.system(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null zone");
    }

    static void testUtcZoneReturnsClockInUtc() {
        Clock clock = Clock.system(ZoneOffset.UTC);
        if (clock == null) {
            throw new RuntimeException("Clock.system(ZoneOffset.UTC) returned null");
        }
        if (!ZoneOffset.UTC.equals(clock.getZone())) {
            throw new RuntimeException("Expected zone UTC but got " + clock.getZone());
        }
    }
}
