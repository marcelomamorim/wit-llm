/*
 * @test
 * @summary Witup-generated checks for LocalTime.withHour(int) identity, field preservation and hour validation
 * @run main LocalTimeWitupTest
 */
import java.time.DateTimeException;
import java.time.LocalTime;

public class LocalTimeWitupTest {
    public static void main(String[] args) {
        testSameHourReturnsSameInstance();
        testValidHourChangesOnlyHour();
        testInvalidHourThrowsDateTimeException();
    }

    static void testSameHourReturnsSameInstance() {
        LocalTime t = LocalTime.of(10, 30, 15, 500);
        if (t.withHour(10) != t) {
            throw new AssertionError("withHour with current hour must return this");
        }
    }

    static void testValidHourChangesOnlyHour() {
        LocalTime t = LocalTime.of(10, 30, 15, 500);
        int[] hours = {0, 5, 23};
        for (int h : hours) {
            LocalTime r = t.withHour(h);
            if (r.getHour() != h) {
                throw new AssertionError("hour not updated: " + h);
            }
            if (r.getMinute() != 30 || r.getSecond() != 15 || r.getNano() != 500) {
                throw new AssertionError("other fields changed for hour: " + h);
            }
        }
    }

    static void testInvalidHourThrowsDateTimeException() {
        int[] invalid = {-1, 24, 100};
        for (int h : invalid) {
            boolean thrown = false;
            try {
                LocalTime.of(10, 30).withHour(h);
            } catch (DateTimeException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new AssertionError("expected DateTimeException for hour: " + h);
            }
        }
    }
}
