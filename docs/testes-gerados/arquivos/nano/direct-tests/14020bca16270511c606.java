/*
 * @test
 * @summary Test getMonth() method of LocalDate for correct Month return
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.Month;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testGetMonth();
    }

    public static void testGetMonth() {
        LocalDate date = LocalDate.of(2023, 1, 15);
        Month month = date.getMonth();
        assert month == Month.JANUARY : "Expected JANUARY, but got " + month;

        date = LocalDate.of(2023, 12, 31);
        month = date.getMonth();
        assert month == Month.DECEMBER : "Expected DECEMBER, but got " + month;

        date = LocalDate.of(2024, 2, 29);
        month = date.getMonth();
        assert month == Month.FEBRUARY : "Expected FEBRUARY, but got " + month;
    }
}
