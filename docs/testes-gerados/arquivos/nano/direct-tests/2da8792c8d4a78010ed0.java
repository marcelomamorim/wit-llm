/*
 * @test
 * @summary Tests for LocalTime.withSecond(int second)
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.temporal.ChronoField;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        testWithSecondNoChange();
        testWithSecondValidChange();
        testWithSecondInvalidValue();
    }

    static void testWithSecondNoChange() {
        LocalTime time = LocalTime.of(10, 20, 30);
        LocalTime result = time.withSecond(30);
        assert result == time : "Expected same instance when second is unchanged";
    }

    static void testWithSecondValidChange() {
        LocalTime time = LocalTime.of(10, 20, 30);
        LocalTime result = time.withSecond(45);
        assert result.getHour() == 10 : "Hour should remain 10";
        assert result.getMinute() == 20 : "Minute should remain 20";
        assert result.getSecond() == 45 : "Second should be 45";
        assert result != time : "Result should be a new instance";
    }

    static void testWithSecondInvalidValue() {
        try {
            LocalTime.of(10, 20, 30).withSecond(60);
            assert false : "Expected DateTimeException for invalid second";
        } catch (IndexOutOfBoundsException e) {
            // Expected exception, test passes
        }
    }
}
