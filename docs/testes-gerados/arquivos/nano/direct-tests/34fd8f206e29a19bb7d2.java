/*
 * @test
 * @summary Test LocalDateTime.withMinute(int minute) for various inputs.
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.LocalTime;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testWithMinuteValid();
        testWithMinuteEdgeCases();
        testWithMinuteInvalid();
    }

    static void testWithMinuteValid() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 3, 15, 10, 30);
        for (int minute = 0; minute <= 59; minute += 15) {
            LocalDateTime result = dateTime.withMinute(minute);
            assert result.getMinute() == minute : "Expected minute: " + minute + ", but got: " + result.getMinute();
            assert result.getYear() == 2023 : "Year changed unexpectedly";
            assert result.getMonthValue() == 3 : "Month changed unexpectedly";
            assert result.getDayOfMonth() == 15 : "Day changed unexpectedly";
            assert result.getHour() == 10 : "Hour changed unexpectedly";
        }
    }

    static void testWithMinuteEdgeCases() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 12, 31, 23, 45);
        LocalDateTime resultZero = dateTime.withMinute(0);
        assert resultZero.getMinute() == 0 : "Expected minute: 0";
        assert resultZero.getHour() == 23 : "Hour changed unexpectedly";
        LocalDateTime resultFiftyNine = dateTime.withMinute(59);
        assert resultFiftyNine.getMinute() == 59 : "Expected minute: 59";
        assert resultFiftyNine.getHour() == 23 : "Hour changed unexpectedly";
    }

    static void testWithMinuteInvalid() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 1, 1, 0, 0);
        try {
            dateTime.withMinute(-1);
            assert false : "Expected DateTimeException for minute -1";
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
        try {
            dateTime.withMinute(60);
            assert false : "Expected DateTimeException for minute 60";
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }
}
