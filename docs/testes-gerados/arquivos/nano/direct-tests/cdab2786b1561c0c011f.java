/*
 * @test
 * @summary Test OffsetTime.of(LocalTime, ZoneOffset) method for valid inputs.
 * @run main OffsetTimeWitupTest
 */

import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.OffsetTime;

public class OffsetTimeWitupTest {
    public static void main(String[] args) {
        testOfMethodWithValidInputs();
    }

    static void testOfMethodWithValidInputs() {
        LocalTime time = LocalTime.of(12, 30, 45, 123456789);
        ZoneOffset offset = ZoneOffset.ofHoursMinutes(5, 30);
        OffsetTime result = OffsetTime.of(time, offset);
        assert result != null : "Result should not be null";
        assert result.toLocalTime().equals(time) : "LocalTime part mismatch";
        assert result.getOffset().equals(offset) : "ZoneOffset mismatch";
    }
}
