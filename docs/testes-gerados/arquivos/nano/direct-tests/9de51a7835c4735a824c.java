/* @test    @summary Test LocalDateTime.withSecond(int second) method for valid and invalid inputs    @run main LocalDateTimeWitupTest */

import java.time.LocalDateTime;
import java.time.LocalTime;
import static java.time.LocalTime.MIN;
import static java.time.LocalTime.MAX;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testWithSecondValid();
        testWithSecondInvalid();
    }

    private static void testWithSecondValid() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 3, 15, 12, 30, 45);
        for (int sec = 0; sec <= 59; sec += 15) {
            LocalDateTime result = dateTime.withSecond(sec);
            assert result.getSecond() == sec : "Expected second: " + sec + ", but got: " + result.getSecond();
            assert result.toLocalTime().getHour() == 12 : "Hour changed unexpectedly";
            assert result.toLocalTime().getMinute() == 30 : "Minute changed unexpectedly";
        }
    }

    private static void testWithSecondInvalid() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 3, 15, 12, 30, 45);
        int[] invalidSeconds = {-1, 60, 100, Integer.MIN_VALUE, Integer.MAX_VALUE};
        for (int invalidSecond : invalidSeconds) {
            try {
                dateTime.withSecond(invalidSecond);
                assert false : "Expected DateTimeException for second: " + invalidSecond;
            } catch (IndexOutOfBoundsException | java.time.DateTimeException e) {
                // Expected exception
            }
        }
    }
}
