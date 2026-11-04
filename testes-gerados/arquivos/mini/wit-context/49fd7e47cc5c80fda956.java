/*
 * @test
 * @summary Test OffsetTime.now(Clock) method behavior including null clock handling
 * @run main OffsetTimeWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZoneRules;
import java.util.Objects;

public class OffsetTimeWitupTest {
    public static void main(String[] args) throws Exception {
        testNowWithValidClock();
        testNowWithNullClock();
    }

    private static void testNowWithValidClock() {
        // Create a fixed clock at a known instant and zone offset
        Instant fixedInstant = Instant.parse("2020-01-01T10:15:30.00Z");
        ZoneOffset offset = ZoneOffset.ofHours(2);
        Clock fixedClock = Clock.fixed(fixedInstant, offset);

        OffsetTime result = OffsetTime.now(fixedClock);

        // The result should have the same offset as the clock
        if (!result.getOffset().equals(offset)) {
            throw new AssertionError("OffsetTime.now(clock) returned OffsetTime with wrong offset");
        }

        // The local time should correspond to the instant adjusted by offset
        // Calculate expected local time
        int expectedHour = (fixedInstant.atOffset(offset).getHour());
        int expectedMinute = fixedInstant.atOffset(offset).getMinute();
        int expectedSecond = fixedInstant.atOffset(offset).getSecond();
        int expectedNano = fixedInstant.atOffset(offset).getNano();

        if (result.getHour() != expectedHour || result.getMinute() != expectedMinute ||
            result.getSecond() != expectedSecond || result.getNano() != expectedNano) {
            throw new AssertionError("OffsetTime.now(clock) returned OffsetTime with wrong local time");
        }
    }

    private static void testNowWithNullClock() {
        try {
            OffsetTime.now(null);
            throw new AssertionError("OffsetTime.now(null) did not throw NullPointerException");
        } catch (NullPointerException ex) {
            // Expected exception
            if (!"clock".equals(ex.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch: " + ex.getMessage());
            }
        }
    }
}
